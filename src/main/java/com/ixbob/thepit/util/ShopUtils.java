package com.ixbob.thepit.util;

import com.ixbob.thepit.enums.GUIShopItemEnum;

public class ShopUtils {

    public static GUIShopItemEnum getGUIShopItemEnumById(int id) {
        for (GUIShopItemEnum GUIShopItemEnum : GUIShopItemEnum.values()) {
            if (GUIShopItemEnum.getId() == id) {
                return GUIShopItemEnum;
            }
        }
        return null;
    }

    public static int getShopItemIdByInventoryIndex(int inventoryIndex) {
        for (GUIShopItemEnum shopItemEnum : GUIShopItemEnum.values()) {
            if (shopItemEnum.getIndex() == inventoryIndex) {
                return shopItemEnum.getId();
            }
        }
        return -1;
    }
}
