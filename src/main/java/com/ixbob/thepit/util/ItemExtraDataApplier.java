package com.ixbob.thepit.util;

import com.ixbob.thepit.enums.ItemExtraData;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class ItemExtraDataApplier {
    public static ItemStack applyFromList(ItemStack itemStack, ArrayList<String> extraDataList) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        for (String extraDataStr : extraDataList) {
            ItemExtraData extraData = ItemExtraData.valueOf(extraDataStr);
            if (extraData == ItemExtraData.UNBREAKABLE) {
                itemMeta.setUnbreakable(true);
            }
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
