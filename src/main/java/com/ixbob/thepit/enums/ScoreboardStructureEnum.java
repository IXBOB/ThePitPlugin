package com.ixbob.thepit.enums;

public enum ScoreboardStructureEnum {
    EMPTY(),
    LEVEL(),
    NEXT_LEVEL_NEED_XP(),
    COIN(),
    BATTLE_STATE(),
    BOTTOM_INFO(),
    TALENT_STRENGTH();

    private ScoreboardStructureEnum positionAfter = null;

    public static void init() {
        TALENT_STRENGTH.positionAfter = BATTLE_STATE;
    }

    public ScoreboardStructureEnum getPositionAfter() {
        return positionAfter;
    }
}
