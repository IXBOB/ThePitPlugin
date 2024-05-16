package com.ixbob.thepit.util;

import com.ixbob.thepit.Main;
import com.ixbob.thepit.PlayerDataBlock;
import com.ixbob.thepit.enums.TalentItemsEnum;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class TalentUtils {
    public static boolean setTalentItem(Inventory inventory, int index, TalentItemsEnum talentItem, int talentLevel) {
        Player player = (Player) inventory.getHolder();
        PlayerDataBlock playerDataBlock = Main.getPlayerDataBlock(player);

        int playerLevel = playerDataBlock.getLevel();
        int playerPrestigeLevel = playerDataBlock.getPrestigeLevel();
        int needLevel = talentItem.getNeedLevel();
        int needPrestigeLevel = talentItem.getNeedPrestigeLevel();
        if (playerPrestigeLevel < needPrestigeLevel || (playerPrestigeLevel == needPrestigeLevel && playerLevel < needLevel)) { //不满足条件，不放置
            return false;
        }
        ItemStack item = getTalentItem(TalentItemsEnum.HEALTH_BOOST, talentLevel);
        inventory.setItem(index, item);
        return true;
    }

    private static ItemStack getTalentItem(TalentItemsEnum talentItemType, int level) {
        ItemStack itemStack;
        ItemMeta itemMeta;
        if (talentItemType == TalentItemsEnum.HEALTH_BOOST) {
            itemStack = talentItemType.getItemStack(1);
            List<String> loreList = talentItemType.getLoreList();
            itemMeta = itemStack.getItemMeta();
            loreList.replaceAll(s -> s.replace("%Level%", String.valueOf(level))
                    .replace("%NextLevelNeedCoin%", String.valueOf(getNextLevelNeedCoinAmount(talentItemType, level)))
                    .replace("%AddMaxHealth%", String.valueOf(level * 2)));
            itemMeta.setLore(loreList);
            itemStack.setItemMeta(itemMeta);
            return itemStack;
        }
        throw new RuntimeException();
    }

    public static int getNextLevelNeedCoinAmount(TalentItemsEnum talentItemType, int currentLevel) {
        if (talentItemType == TalentItemsEnum.HEALTH_BOOST) {
            return (currentLevel + 1) * 500;
        }
        throw new RuntimeException();
    }
}
