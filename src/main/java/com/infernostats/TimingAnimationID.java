package com.infernostats;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TimingAnimationID {
  public static final int HOLY_BOOK = 1335;
  public static final int HOLY_BOOK_OR = 9403;
  public static final int UNHOLY_BOOK = 1336;
  public static final int UNHOLY_BOOK_OR = 9404;
  public static final int BOOK_OF_BALANCE = 1337;
  public static final int BOOK_OF_BALANCE_OR = 9405;
  public static final int BOOK_OF_WAR = 7153;
  public static final int BOOK_OF_WAR_OR = 9414;
  public static final int BOOK_OF_LAW = 7154;
  public static final int BOOK_OF_LAW_OR = 9440;
  public static final int BOOK_OF_DARKNESS = 7155;
  public static final int BOOK_OF_DARKNESS_OR = 9441;
  public static final int SOULFLAME_HORN = 12158;

  public static final int EMOTE_YES = 855;
  public static final int EMOTE_NO = 856;
  public static final int EMOTE_SPIN = 2107;
  public static final int EMOTE_THINK = 857;
  public static final int EMOTE_DANCE = 866;

  private static final List<Integer> godbookAnimationIDs = new ArrayList<>(
      Arrays.asList(
          HOLY_BOOK,
          HOLY_BOOK_OR,
          UNHOLY_BOOK,
          UNHOLY_BOOK_OR,
          BOOK_OF_BALANCE,
          BOOK_OF_BALANCE_OR,
          BOOK_OF_WAR,
          BOOK_OF_WAR_OR,
          BOOK_OF_LAW,
          BOOK_OF_LAW_OR,
          BOOK_OF_DARKNESS,
          BOOK_OF_DARKNESS_OR,
              SOULFLAME_HORN
      )
  );

  private static final List<Integer> emoteAnimationIDs = new ArrayList<>(
      Arrays.asList(
        EMOTE_YES,
        EMOTE_NO,
        EMOTE_SPIN,
        EMOTE_THINK,
        EMOTE_DANCE
      )
  );

  private static final List<Integer> timingAnimationIDs = Stream.of(
      godbookAnimationIDs.stream(),
      emoteAnimationIDs.stream()
  ).flatMap(s -> s).collect(Collectors.toList());

  public static boolean isGodbookAnimation(int animationID)
  {
    return godbookAnimationIDs.contains(animationID);
  }

  public static boolean isEmoteAnimation(int animationID)
  {
    return emoteAnimationIDs.contains(animationID);
  }

  public static boolean isTimingAnimation(int animationID)
  {
    return timingAnimationIDs.contains(animationID);
  }
}
