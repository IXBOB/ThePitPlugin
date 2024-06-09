package com.ixbob.thepit.util;

public class TalentCalcuUtils {
    //推荐直接从TalentItemsEnum内调用，实在没办法时直接调用
    public static float getAddPointValue(int id, int level) {
        return switch (id) {
            case 0 -> (1 + level);
            case 1 -> (4 + level);
            case 2 -> 0;
            case 3 -> (1 + level);
            case 4 -> (1 + level);
            case 5 -> 0;
            case 6 -> (6 + level * 6);
            case 7 -> (float) (1 + level);
            case 8 -> 20 + level * 10;
            case 9 -> 40 + level * 3 * 20;
            default -> throw new NullPointerException();
        };
    }

    public static double getNextLevelAddCoinValue(int id, int currentLevel) {
        return switch (id) {
            case 0 -> (currentLevel + 1) * 500;
            case 1 -> (currentLevel + 1) * 400;
            case 2 -> 0;
            case 3 -> (currentLevel + 1) * 800;
            case 4 -> (currentLevel + 1) * 500;
            case 5 -> 0;
            case 6 -> (currentLevel + 1) * 350;
            case 7 -> (currentLevel + 1) * 500;
            case 8 -> (currentLevel + 1) * 700;
            case 9 -> (currentLevel + 1) * 600;
            default -> throw new NullPointerException();
        };
    }
}
