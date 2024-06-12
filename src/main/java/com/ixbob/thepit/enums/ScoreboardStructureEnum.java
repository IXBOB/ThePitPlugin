package com.ixbob.thepit.enums;

public enum ScoreboardStructureEnum {
    EMPTY,
    LEVEL,
    NEXT_LEVEL_NEED_XP,
    COIN,
    BATTLE_STATE,
    BOTTOM_INFO,
    CONSECUTIVE_KILL_AMOUNT,
    BE_REWARDED_AMOUNT,
    TALENT_STRENGTH;

    private ScoreboardStructureEnum positionAfter = null;

    // 初始化枚举的位置属性
    // 新增Enum时须要考虑此处
    public static void init() {
        TALENT_STRENGTH.positionAfter = BATTLE_STATE;
        CONSECUTIVE_KILL_AMOUNT.positionAfter = COIN;
        BE_REWARDED_AMOUNT.positionAfter = CONSECUTIVE_KILL_AMOUNT;
    }

    public ScoreboardStructureEnum getPositionAfter() {
        return positionAfter;
    }
}
