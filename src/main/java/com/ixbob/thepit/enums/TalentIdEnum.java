package com.ixbob.thepit.enums;

import com.ixbob.thepit.util.Utils;

public enum TalentIdEnum {
    ID_0(0, Utils.getInventoryIndex(2,2)),
    ID_1(1, Utils.getInventoryIndex(2,3)),
    ID_2(2, Utils.getInventoryIndex(2,4)),
    ID_3(3, Utils.getInventoryIndex(2,5)),
    ID_4(4, Utils.getInventoryIndex(2,6)),
    ID_5(5, Utils.getInventoryIndex(2,7)),
    ID_6(6, Utils.getInventoryIndex(2,8)),
    ID_7(7, Utils.getInventoryIndex(3,1)),
    ID_8(8, Utils.getInventoryIndex(3,2)),
    ID_9(9, Utils.getInventoryIndex(3,3)),
    ID_10(10, Utils.getInventoryIndex(3,4)),
    ID_11(11, Utils.getInventoryIndex(3,5)),
    ID_12(12, Utils.getInventoryIndex(3,6)),
    ID_13(13, Utils.getInventoryIndex(3,7)),
    ID_14(14, Utils.getInventoryIndex(3,8));

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
