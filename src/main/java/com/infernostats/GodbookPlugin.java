package com.infernostats;

import com.google.inject.Provides;
import javax.inject.Inject;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.AnimationChanged;
import net.runelite.api.events.GameTick;
import net.runelite.api.gameval.VarbitID;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import java.util.LinkedHashMap;

@Slf4j
@PluginDescriptor(
  name = "Godbook",
  tags = {"timer", "timing", "maiden", "xarpus", "verzik"}
)
public class GodbookPlugin extends Plugin
{
	@Getter(AccessLevel.MODULE)
	private LinkedHashMap<String, Integer> players;

	@Inject
	private Client client;

	@Inject
	private GodbookOverlay overlay;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private GodbookConfig config;

	@Provides
	GodbookConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(GodbookConfig.class);
	}

	@Override
	protected void startUp() throws Exception
	{
		players = new LinkedHashMap<>();
		overlayManager.add(overlay);
	}

	@Override
	protected void shutDown() throws Exception
	{
		overlayManager.remove(overlay);
		players.clear();
	}

  @Subscribe
  public void onAnimationChanged(final AnimationChanged event)
  {
    if (!(event.getActor() instanceof Player))
    {
      return;
    }

		if (config.theatreOnly() && !isInTheatreOfBlood())
			return;

    if (config.instanceOnly() && !isInInstance())
      return;

    switch (config.animations()) {
      case BOTH: {
        if (TimingAnimationID.isTimingAnimation(event.getActor().getAnimation()))
          players.put(event.getActor().getName(), 0);
      }
      case GODBOOKS_ONLY: {
        if (TimingAnimationID.isGodbookAnimation(event.getActor().getAnimation()))
          players.put(event.getActor().getName(), 0);
      }
      case EMOTES_ONLY: {
        if (TimingAnimationID.isEmoteAnimation(event.getActor().getAnimation()))
          players.put(event.getActor().getName(), 0);
      }
    }
  }

	@Subscribe
	public void onGameTick(GameTick event)
	{
		if (players.isEmpty())
			return;

		players.entrySet()
			.forEach(i -> i.setValue(i.getValue() + 1));

		players.entrySet()
			.removeIf(i -> i.getValue() >= config.maxTicks());
	}

  private boolean isInTheatreOfBlood()
  {
    return client.getVarbitValue(VarbitID.TOB_CLIENT_PARTYSTATUS) != 0;
  }

  private boolean isInInstance()
  {
    WorldView wv = client.getTopLevelWorldView();
    if (wv == null) {
      return false;
    }
    return wv.isInstance();
  }
}