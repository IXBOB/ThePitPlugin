package com.ixbob.thepit.util;

import com.ixbob.thepit.Main;
import com.ixbob.thepit.PlayerDataBlock;
import com.ixbob.thepit.TalentItemsEnum;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TalentUtils {
    public static void setTalentItem(Inventory inventory, int index, TalentItemsEnum talentItem, int talentLevel) {
        Player player = (Player) inventory.getHolder();
        PlayerDataBlock playerDataBlock = Main.getPlayerDataBlock(player);

        int playerLevel = playerDataBlock.getLevel();
        int playerPrestigeLevel = playerDataBlock.getPrestigeLevel();
        int needLevel = talentItem.getNeedLevel();
        int needPrestigeLevel = talentItem.getNeedPrestigeLevel();
        if (playerLevel < needLevel && playerPrestigeLevel <= needPrestigeLevel) {
            return;
        }

        ItemStack item = talentItem.getItemStack(1, talentLevel);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(talentItem.getDisplayName());
        itemMeta.setLore(talentItem.getLoreList());
        item.setItemMeta(itemMeta);

        inventory.setItem(index, item);
    }
}
