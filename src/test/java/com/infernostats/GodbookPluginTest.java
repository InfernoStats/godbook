package com.infernostats;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

import java.util.Arrays;

public class GodbookPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(GodbookPlugin.class);
		String[] debugArgs = Arrays.copyOf(args, args.length + 1);
		debugArgs[args.length] = "--developer-mode";
		RuneLite.main(debugArgs);
	}
}