package com.infernostats;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("Godbook")
public interface GodbookConfig extends Config
{
	@ConfigItem(
			position = 0,
			keyName = "theatreOnly",
			name = "Theatre of Blood Only",
			description = "Only display the overlay when in the Theatre of Blood"
	)
	default boolean theatreOnly()
	{
		return false;
	}

	@ConfigItem(
			position = 1,
			keyName = "lightbearerSupport",
			name = "Lightbearer Support",
			description = "Additional info displayed based on ring usage"
			)
	default boolean lightbearerSupport() { return false; }

	@ConfigItem(
			position = 2,
			keyName = "ticks",
			name = "Ticks",
			description = "How many ticks the counter remains active for"
	)
	default int maxTicks()
	{
		return 157;
	}

	@ConfigItem(
			position = 4,
			hidden = true,
			keyName = "hornAnimId",
			name = "[WIP] Extra Animation Id",
			description = "Extra animation ID to treat as a godbook"
	)
	default int hornAnimId() { return 0; }

	@ConfigItem(
			position = 3,
			keyName = "backwardsCount",
			name = "Count Backwards",
			description = "Display ticks until full regen, instead of ticks since preach."
	)
	default boolean backwardsCount() { return false; }
}
