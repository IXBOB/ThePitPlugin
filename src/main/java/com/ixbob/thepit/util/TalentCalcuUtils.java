package com.ixbob.thepit.util;

public class TalentCalcuUtils {
    public static float getValue(int id, int level) {
        switch (id) {
            case 0: return (1 + level * 2);
            case 1: return (4 + level * 4);
        }
        throw new NullPointerException();
    }
}
