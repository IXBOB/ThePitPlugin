package com.ixbob.thepit.util;

import com.ixbob.thepit.enums.ItemExtraDataEnum;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class ItemExtraDataApplier {
    public static ItemStack applyFromList(ItemStack itemStack, ArrayList<String> extraDataList) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        for (String extraDataStr : extraDataList) {
            ItemExtraDataEnum extraData = ItemExtraDataEnum.valueOf(extraDataStr);
            if (extraData == ItemExtraDataEnum.UNBREAKABLE) {
                itemMeta.setUnbreakable(true);
            }
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
