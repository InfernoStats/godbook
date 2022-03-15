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
			keyName = "ticks",
			name = "Ticks",
			description = "How many ticks the counter remains active for"
	)
	default int maxTicks()
	{
		return 157;
	}
}
