package com.ixbob.thepit.util;

import com.ixbob.thepit.enums.ItemExtraDataEnum;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class ItemExtraDataReader {
    public static ArrayList<String> readFromItem(ItemStack itemStack) {
        ArrayList<String> list = new ArrayList<>();
        NBTItem nbtItem = new NBTItem(itemStack);
        if (itemStack.getItemMeta().isUnbreakable()) {
            list.add(ItemExtraDataEnum.ENCHANT_UNBREAKABLE.toString());
        }
        if (itemStack.getItemMeta().hasEnchant(Enchantment.DIG_SPEED)) {
            if (itemStack.getItemMeta().getEnchantLevel(Enchantment.DIG_SPEED) == 4) {
                list.add(ItemExtraDataEnum.ENCHANT_EFFICIENCY_4.toString());
            }
        }
        if (itemStack.getItemMeta().getItemFlags().contains(ItemFlag.HIDE_ATTRIBUTES)) {
            list.add(ItemExtraDataEnum.FLAG_HIDE_ATTRIBUTES.toString());
        }
        return (list.isEmpty() ? null : list);
    }
}
