package com.ixbob.thepit.util;

import com.ixbob.thepit.enums.ItemExtraData;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class ItemExtraDataReader {
    public static ArrayList<String> readFromItem(ItemStack itemStack) {
        ArrayList<String> list = new ArrayList<>();
        NBTItem nbtItem = new NBTItem(itemStack);
        if (itemStack.getItemMeta().isUnbreakable()) {
            list.add(ItemExtraData.UNBREAKABLE.toString());
        }
        return (list.isEmpty() ? null : list);
    }
}
