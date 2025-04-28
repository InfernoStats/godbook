package com.infernostats;

public class Preach implements Comparable<Preach> {
    public int tick;
    public String name;
    public boolean preachedWithRing;
    public int ringTick;

    public Preach(int ringTick, boolean preachedWithRing, String name, int tick) {
        this.ringTick = ringTick;
        this.preachedWithRing = preachedWithRing;
        this.name = name;
        this.tick = tick;
    }

    @Override
    public int compareTo(Preach p) {
        return Integer.compare(p.tick, this.tick);
    }

    public Preach advance(boolean isBackwards) {
        this.tick += isBackwards ? -1 : 1;
        return this;
    }
}
