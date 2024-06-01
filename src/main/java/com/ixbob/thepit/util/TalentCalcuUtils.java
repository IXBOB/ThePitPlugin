package com.ixbob.thepit.util;

public class TalentCalcuUtils {
    //推荐直接从TalentItemsEnum内调用，实在没办法时直接调用
    public static float getAddPointValue(int id, int level) {
        switch (id) {
            case 0: return (1 + level);
            case 1: return (4 + level);
            case 2: return 0;
            case 3: return (1 + level);
            case 4: return (1 + level);
            case 5: return 0;
            case 6: return (6 + level * 6);
            case 7: return (float) (1 + level);
            case 8: return 20 + level * 10;
        }
        throw new NullPointerException();
    }

    public static double getNextLevelAddCoinValue(int id, int currentLevel) {
        switch (id) {
            case 0: return (currentLevel + 1) * 500;
            case 1: return (currentLevel + 1) * 400;
            case 2: return 0;
            case 3: return (currentLevel + 1) * 800;
            case 4: return (currentLevel + 1) * 500;
            case 5: return 0;
            case 6: return (currentLevel + 1) * 350;
            case 7: return (currentLevel + 1) * 1000;
            case 8: return (currentLevel + 1) * 700;
        }
        throw new NullPointerException();
    }
}
