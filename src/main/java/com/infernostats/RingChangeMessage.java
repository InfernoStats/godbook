package com.infernostats;

import net.runelite.client.party.messages.PartyMessage;

public class RingChangeMessage extends PartyMessage {
    String n;
    int r;
    int t;

    public RingChangeMessage(String n, int r, int t) {
        this.n = n;
        this.r = r;
        this.t = t;
    }
}
