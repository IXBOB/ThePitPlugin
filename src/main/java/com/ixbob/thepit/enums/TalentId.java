package com.ixbob.thepit.enums;

import com.ixbob.thepit.util.Utils;

public enum TalentId {
    ID_0(Utils.getInventoryIndex(2,2)),
    ID_1(Utils.getInventoryIndex(2,3));

    private int inventoryIndex;

    TalentId(int inventoryIndex) {
        this.inventoryIndex = inventoryIndex;
    }

    public int getInventoryIndex() {
        return inventoryIndex;
    }
}
