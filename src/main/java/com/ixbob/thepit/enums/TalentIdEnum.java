package com.ixbob.thepit.enums;

import com.ixbob.thepit.util.Utils;

public enum TalentIdEnum {
    ID_0(0, Utils.getInventoryIndex(2,2)),
    ID_1(1, Utils.getInventoryIndex(2,3));

    private int id;
    private int inventoryIndex;

    TalentIdEnum(int id, int inventoryIndex) {
        this.id = id;
        this.inventoryIndex = inventoryIndex;
    }

    public int getId() {
        return id;
    }

    public int getInventoryIndex() {
        return inventoryIndex;
    }
}
