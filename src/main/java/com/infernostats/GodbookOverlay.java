package com.infernostats;

import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.PanelComponent;
import net.runelite.client.ui.overlay.components.TitleComponent;

import javax.inject.Inject;
import java.awt.*;
import java.util.List;
import java.util.*;

class GodbookOverlay extends Overlay {
  private final GodbookPlugin plugin;
  private final GodbookConfig config;
  private final PanelComponent panelComponent = new PanelComponent();

  @Inject
  private GodbookOverlay(GodbookPlugin plugin, GodbookConfig config) {
    this.plugin = plugin;
    this.config = config;

    setPosition(OverlayPosition.ABOVE_CHATBOX_RIGHT);
  }

  @Override
  public Dimension render(Graphics2D graphics) {
    String title = "Tick:";
    LinkedHashMap<String, Integer> players = plugin.getPlayers();

    panelComponent.getChildren().clear();

    if (!players.isEmpty()) {
      panelComponent.setPreferredSize(new Dimension(getMaxWidth(graphics, players, title) + 14, 0));
      panelComponent.getChildren().add(TitleComponent.builder().text(title).color(Color.green).build());

      if (config.reverse()) {
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(players.entrySet());
        for (int i = entries.size() - 1; i >= 0; i--) {
          Map.Entry<String, Integer> entry = entries.get(i);
          addPlayerToOverlay(entry.getKey(), entry.getValue());
        }
      } else {
        players.forEach(this::addPlayerToOverlay);
      }
    }

    return panelComponent.render(graphics);
  }

  private int getMaxWidth(Graphics2D graphics, LinkedHashMap<String, Integer> players, String title) {
    String longestKey = Collections.max(players.keySet(), Comparator.comparingInt(String::length));
    return graphics.getFontMetrics().stringWidth(longestKey) + graphics.getFontMetrics().stringWidth(title);
  }

  private void addPlayerToOverlay(String playerName, Integer ticks) {
    panelComponent.getChildren().add(LineComponent.builder().left(playerName).right(Integer.toString(ticks)).build());
  }
}

