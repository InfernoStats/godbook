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
      keyName = "instanceOnly",
      name = "Instance Only",
      description = "Only display the overlay when in an instance"
  )
  default boolean instanceOnly()
  {
    return false;
  }

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

  enum AllowedAnimations {
    EMOTES_ONLY,
    GODBOOKS_ONLY,
    BOTH
  };

  @ConfigItem(
      position = 3,
      keyName = "allowedAnimations",
      name = "Allowed Animations",
      description = "Trigger the overlay on Emotes, Godbooks, or Both"
  )
  default AllowedAnimations animations()
  {
    return AllowedAnimations.BOTH;
  }

  @ConfigItem(
      position = 4,
      keyName = "reverse",
      name = "Reverse Player List",
      description = "Display the current list of players in reverse order"
  )
  default boolean reverse()
  {
    return false;
  }
}
