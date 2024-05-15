package com.ixbob.thepit.util;

import com.ixbob.thepit.TalentItemsEnum;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class TalentUtils {
    public static void setTalentItem(Inventory inventory, int index, TalentItemsEnum talentItem, int talentLevel) {
        //TODO: finish talentLevel
        ItemStack item = talentItem.getItemStack(1, talentLevel);

        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(talentItem.getDisplayName());
        itemMeta.setLore(talentItem.getLoreList());
        item.setItemMeta(itemMeta);

        inventory.setItem(index, item);
    }
}
