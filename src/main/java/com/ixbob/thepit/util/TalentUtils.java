package com.ixbob.thepit.util;

import com.ixbob.thepit.Main;
import com.ixbob.thepit.PlayerDataBlock;
import com.ixbob.thepit.enums.gui.talent.GUITalentItemEnum;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class TalentUtils {
    public static boolean setTalentItem(Inventory inventory, int index, GUITalentItemEnum talentItem, int talentLevel, boolean equipped, boolean hasReachedMaxLevel) {
        Player player = (Player) inventory.getHolder();
        PlayerDataBlock playerDataBlock = Main.getPlayerDataBlock(player);

        int playerLevel = playerDataBlock.getLevel();
        int playerPrestigeLevel = playerDataBlock.getPrestigeLevel();
        int needLevel = talentItem.getNeedLevel();
        int needPrestigeLevel = talentItem.getNeedPrestigeLevel();
        if (playerPrestigeLevel < needPrestigeLevel || (playerPrestigeLevel == needPrestigeLevel && playerLevel < needLevel)) { //不满足条件，不放置
            return false;
        }
        ItemStack item = talentItem.getNamedItem(talentLevel, equipped, hasReachedMaxLevel);
        inventory.setItem(index, item);
        return true;
    }

    public static double getNextLevelNeedCoinAmount(GUITalentItemEnum talentItemType, int currentLevel) {
        return talentItemType.getNextLevelNeedCoinValue(currentLevel + 1);
    }

    public static int getNotEquippedTalentIdByInventoryIndex(int inventoryIndex) {
        for (GUITalentItemEnum talentItemEnum : GUITalentItemEnum.values()) {
            if (talentItemEnum.getIndex() == inventoryIndex) {
                return talentItemEnum.getId();
            }
        }
        return -1;
    }

    public static int getEquipGridIdByInventoryIndex(int inventoryIndex) {
        return inventoryIndex - 39;
    }

    public static GUITalentItemEnum getGUITalentItemEnumById(int id) {
        for (GUITalentItemEnum GUITalentItemEnum : GUITalentItemEnum.values()) {
            if (GUITalentItemEnum.getId() == id) {
                return GUITalentItemEnum;
            }
        }
        return null;
    }

    public static int getEquippedTalentIdByInventoryIndex(Player player, int inventoryIndex) {
        PlayerDataBlock dataBlock = Main.getPlayerDataBlock(player);
        ArrayList<?> equippedNormalTalentList = dataBlock.getEquippedNormalTalentList();
        int gridId = getEquipGridIdByInventoryIndex(inventoryIndex);
        return (int) equippedNormalTalentList.get(gridId);
    }
}
