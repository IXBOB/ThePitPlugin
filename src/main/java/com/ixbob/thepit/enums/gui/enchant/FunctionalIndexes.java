package com.ixbob.thepit.enums.gui.enchant;

import com.ixbob.thepit.util.GUIUtils;

public enum FunctionalIndexes {

    /**  index:
     *   ###  123
     *   # #  8 4
     *   ###  765
     */

    INDEX_1(GUIUtils.getInvIndex(2,2)),
    INDEX_2(GUIUtils.getInvIndex(2,3)),
    INDEX_3(GUIUtils.getInvIndex(2,4)),
    INDEX_4(GUIUtils.getInvIndex(3,4)),
    INDEX_5(GUIUtils.getInvIndex(4,4)),
    INDEX_6(GUIUtils.getInvIndex(4,3)),
    INDEX_7(GUIUtils.getInvIndex(4,2)),
    INDEX_8(GUIUtils.getInvIndex(3,2));

    private final int invIndex;

    FunctionalIndexes(int invIndex) {
        this.invIndex = invIndex;
    }

    public int getInvIndex() {
        return invIndex;
    }
}
