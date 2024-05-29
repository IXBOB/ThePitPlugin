package com.ixbob.thepit.util;

import com.ixbob.thepit.enums.ItemExtraDataEnum;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class ItemExtraDataApplier {
    public static ItemStack applyFromList(ItemStack itemStack, ArrayList<String> extraDataList) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        for (String extraDataStr : extraDataList) {
            ItemExtraDataEnum extraData = ItemExtraDataEnum.valueOf(extraDataStr);
            if (extraData == ItemExtraDataEnum.ENCHANT_UNBREAKABLE) {
                itemMeta.setUnbreakable(true);
            }
            if (extraData == ItemExtraDataEnum.ENCHANT_EFFICIENCY_4) {
                itemMeta.addEnchant(Enchantment.DIG_SPEED, 4, true);
            }
            if (extraData == ItemExtraDataEnum.ENCHANT_UNBREAKABLE) {
                itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            }
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
