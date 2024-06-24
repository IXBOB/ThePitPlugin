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

    /**
     * ↖(from)---+
     * -----------
     * +---------↘(to)
     * @param fromIndex begin from 0
     * @param toIndex begin from 0
     */
    public static void fillArea(Inventory inventory, ItemStack itemStack, int fromIndex, int toIndex) {
        int fillColumn = 0;
        while (toIndex >= fromIndex) {
            fillColumn++;
            toIndex -= 9;
            if (fillColumn > 6) {
                throw new IllegalStateException();
            }
        }
        toIndex += 9;
        int fillRow = toIndex - fromIndex + 1;

        //settingRow: 123456789
        //settingColumn: 1------
        //               2------
        //               3------
        int settingRow = (fromIndex + 1) % 9; //begin from 1
        int settingColumn = 1 + fromIndex / 9; //begin from 1
        for (int columnAmount = 1; columnAmount <= fillColumn; columnAmount++) {
            for (int rowAmount = 1; rowAmount <= fillRow; rowAmount++) {
                inventory.setItem(GUIUtils.getInvIndex(settingColumn, settingRow - 1 + rowAmount), itemStack);
            }
            settingColumn++;
        }
    }

    //(getInventoryIndex) ↓
    public static int getInvIndex(int row, int column) {
        return row * 9 - 1 - (9 - column);
    }
}
