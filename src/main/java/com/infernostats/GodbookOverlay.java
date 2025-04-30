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
		HashMap<String, Preach> players = plugin.getPlayers();

		panelComponent.getChildren().clear();

		if (!players.isEmpty())
		{
			panelComponent.setPreferredSize(new Dimension(getMaxWidth(graphics, players, title) + 10, 0));
			panelComponent.getChildren().add(TitleComponent.builder().text(title).color(Color.green).build());

			players.forEach(this::addPlayerToOverlay);
		}

		return panelComponent.render(graphics);
	}

	private int getMaxWidth(Graphics2D graphics, HashMap<String, Preach> players, String title)
	{
		String longestKey = Collections.max(players.keySet(), Comparator.comparingInt(String::length));
		return graphics.getFontMetrics().stringWidth(longestKey) + graphics.getFontMetrics().stringWidth(title) + graphics.getFontMetrics().stringWidth("(157)");
	}

	private void addPlayerToOverlay(String playerName, Preach preach)
	{
		panelComponent.getChildren().add(LineComponent.builder()
				.leftColor(preach.preachedWithRing ? Color.YELLOW : Color.WHITE)
				.left(playerName)
				.right(Integer.toString(preach.tick) + (!preach.preachedWithRing && preach.ringTick != 0 ? " (" + Integer.toString(preach.ringTick) + ")" : ""))
				.build());
	}
}

