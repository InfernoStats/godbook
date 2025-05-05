package com.infernostats;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Emote
{
    YES(855),
    NO(856),
    SPIN(2107),
    THINK(857),
    DANCE(866);

    private final int animationId;

    public int getInt()
    {
        return animationId;
    }
}
