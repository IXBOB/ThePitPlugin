package com.ixbob.thepit.util;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GUIUtils {
    public static void fillAll(Inventory inventory, ItemStack itemStack) {
        int size = inventory.getSize();
        for (int index = 0; index < size; index++) {
            inventory.setItem(index, itemStack);
        }
    }
}
