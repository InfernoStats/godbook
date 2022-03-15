package com.infernostats;

import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.PanelComponent;
import net.runelite.client.ui.overlay.components.TitleComponent;

import javax.inject.Inject;
import java.awt.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Collections;

class GodbookOverlay extends Overlay
{
	private final GodbookPlugin plugin;
	private final PanelComponent panelComponent = new PanelComponent();

	@Inject
	private GodbookOverlay(GodbookPlugin plugin)
	{
		this.plugin = plugin;

		setPosition(OverlayPosition.ABOVE_CHATBOX_RIGHT);
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		String title = "Tick:";
		HashMap<String, Integer> players = plugin.getPlayers();

		panelComponent.getChildren().clear();

		if (!players.isEmpty())
		{
			panelComponent.setPreferredSize(new Dimension(getMaxWidth(graphics, players, title) + 10, 0));
			panelComponent.getChildren().add(TitleComponent.builder().text(title).color(Color.green).build());

			players.forEach(this::addPlayerToOverlay);
		}

		return panelComponent.render(graphics);
	}

	private int getMaxWidth(Graphics2D graphics, HashMap<String, Integer> players, String title)
	{
		String longestKey = Collections.max(players.keySet(), Comparator.comparingInt(String::length));
		return graphics.getFontMetrics().stringWidth(longestKey) + graphics.getFontMetrics().stringWidth(title);
	}

	private void addPlayerToOverlay(String playerName, Integer ticks)
	{
		panelComponent.getChildren().add(LineComponent.builder().left(playerName).right(Integer.toString(ticks)).build());
	}
}

