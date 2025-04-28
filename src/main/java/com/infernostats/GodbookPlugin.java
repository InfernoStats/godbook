package com.infernostats;

import com.google.inject.Provides;
import javax.inject.Inject;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.AnimationChanged;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.VarbitChanged;
import net.runelite.api.gameval.VarbitID;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.party.PartyService;
import net.runelite.client.party.WSClient;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@PluginDescriptor(
	name = "Godbook"
)
public class GodbookPlugin extends Plugin
{
	@Getter(AccessLevel.MODULE)
	private LinkedHashMap<String, Preach> players;

	@Inject
	private Client client;

	@Inject
	private GodbookOverlay overlay;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private PartyService partyService;

	@Inject
	private WSClient wsClient;

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
		wsClient.registerMessage(RingChangeMessage.class);
		overlayManager.add(overlay);
	}

	@Override
	protected void shutDown() throws Exception
	{
		overlayManager.remove(overlay);
		wsClient.unregisterMessage(RingChangeMessage.class);
		players.clear();
	}

	@Subscribe
	public void onAnimationChanged(final AnimationChanged event)
	{
		int animId = event.getActor().getAnimation();
		if (config.theatreOnly() && !isInTheatreOfBlood())
			return;

		if (GodbookAnimationID.isGodbookAnimation(animId) || isHornAnimation(animId)) {
			boolean isLightbearerPreach = false;
			String name = event.getActor().getName();

			if (event.getActor().getName().equalsIgnoreCase(client.getLocalPlayer().getName())) {
				if (config.lightbearerSupport()) {
					// This is preaching, check our equipment.
					try {
						Item item = client.getItemContainer(InventoryID.EQUIPMENT)
								.getItem(EquipmentInventorySlot.RING.getSlotIdx());
						isLightbearerPreach = item.getId() == ItemID.LIGHTBEARER;
					} catch (NullPointerException _e) {
						isLightbearerPreach = false;
					}
				}
			}

			doNewPreach(name, isLightbearerPreach, 0);
		}
	}

	@Subscribe
	public void onGameTick(GameTick event)
	{
		if (players.isEmpty())
			return;

		// Check if the localPlayer equipped a Lightbearer
		if (config.lightbearerSupport() && players.containsKey(client.getLocalPlayer().getName())) {
			Preach localPreach = players.get(client.getLocalPlayer().getName());

			Item item;
			try {
				item = client.getItemContainer(InventoryID.EQUIPMENT).getItem(EquipmentInventorySlot.RING.getSlotIdx());
			} catch (NullPointerException _e) {
				item = null;
			}

			if (item != null && item.getId() == ItemID.LIGHTBEARER) {

				if (localPreach.ringTick == 0) {
					localPreach.ringTick = findLowestPreach();

					if (partyService.isInParty()) {
						partyService.send(new RingChangeMessage(client.getLocalPlayer().getName(), 1, localPreach.ringTick));
					}
				}
			} else {
				if (partyService.isInParty() && localPreach.ringTick != 0) {
					partyService.send(new RingChangeMessage(client.getLocalPlayer().getName(), 0, 0));
				}

				localPreach.ringTick = 0;
			}
		}

		players.entrySet()
			.forEach(i -> i.setValue(i.getValue().advance(config.backwardsCount())));

		players.entrySet()
			.removeIf(this::shouldRemove);
	}

	public void doNewPreach(String name, boolean isLightbearerPreach, int offset) {
		players.put(name,
				new Preach(0, isLightbearerPreach, name, startTick() + offset));

		// Re-order in descending order so that the lowest preach tick always appears
		// at the bottom of the list.
		players = players.entrySet()
				.stream()
				.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
				.collect(Collectors.toMap(
						Map.Entry::getKey,
						Map.Entry::getValue,
						(oldValue, newValue) -> newValue, LinkedHashMap::new));
	}

	@Subscribe
	public void onRingChangeMessage(RingChangeMessage e) {
		if (!e.n.equalsIgnoreCase(client.getLocalPlayer().getName())) {
			players.entrySet().forEach(p -> {
				if (p.getKey().equalsIgnoreCase(e.n)) {
					Preach remotePreach = p.getValue();
					remotePreach.ringTick = e.r == 1 ? e.t : 0;
					p.setValue(remotePreach);
				}
			});
		}
	}

	private int findLowestPreach() {
		// Find the lowest preach
		final int[] lowestPreach = {Integer.MAX_VALUE};
		players.entrySet().forEach(i -> {
			if (lowestPreach[0] > i.getValue().tick) {
				lowestPreach[0] = i.getValue().tick;
			}
		});

		return lowestPreach[0];
	}

	private boolean shouldRemove(Map.Entry<String, Preach> p) {
		if (config.backwardsCount()) {
			return p.getValue().tick < 0;
		} else {
			return p.getValue().tick >= config.maxTicks();
		}
	}

	private boolean isHornAnimation(int animId) {
		return config.hornAnimId() != 0 && animId == config.hornAnimId();
	}

	private int startTick() {
		return config.backwardsCount() ? 150 : 0;
	}

	private boolean isInTheatreOfBlood()
	{
		return client.getVar(Varbits.THEATRE_OF_BLOOD) != 0;
	}
}