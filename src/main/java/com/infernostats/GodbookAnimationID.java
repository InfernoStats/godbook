package com.infernostats;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class GodbookAnimationID
{
	public static final int HOLY_BOOK = 1335;
	public static final int UNHOLY_BOOK = 1336;
	public static final int BOOK_OF_BALANCE = 1337;
	public static final int BOOK_OF_WAR = 7153;
	public static final int BOOK_OF_LAW = 7154;
	public static final int BOOK_OF_DARKNESS = 7155;

	private static final List<Integer> godbookAnimationIDs = new ArrayList<>(
		Arrays.asList(
			HOLY_BOOK,
			UNHOLY_BOOK,
			BOOK_OF_BALANCE,
			BOOK_OF_WAR,
			BOOK_OF_LAW,
			BOOK_OF_DARKNESS
		)
	);

	public static boolean isGodbookAnimation(int animationID)
	{
		return godbookAnimationIDs.contains(animationID);
	}
}
