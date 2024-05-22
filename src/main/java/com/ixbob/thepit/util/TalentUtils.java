package com.ixbob.thepit.util;

import com.ixbob.thepit.Main;
import com.ixbob.thepit.PlayerDataBlock;
import com.ixbob.thepit.enums.TalentIdEnum;
import com.ixbob.thepit.enums.TalentItemsEnum;
import lombok.NonNull;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class TalentUtils {
    public static boolean setTalentItem(Inventory inventory, int index, TalentItemsEnum talentItem, int talentLevel, boolean equipped, boolean hasReachedMaxLevel) {
        Player player = (Player) inventory.getHolder();
        PlayerDataBlock playerDataBlock = Main.getPlayerDataBlock(player);

        int playerLevel = playerDataBlock.getLevel();
        int playerPrestigeLevel = playerDataBlock.getPrestigeLevel();
        int needLevel = talentItem.getNeedLevel();
        int needPrestigeLevel = talentItem.getNeedPrestigeLevel();
        if (playerPrestigeLevel < needPrestigeLevel || (playerPrestigeLevel == needPrestigeLevel && playerLevel < needLevel)) { //不满足条件，不放置
            return false;
        }
        ItemStack item = getTalentItemStack(talentItem, talentLevel, equipped, hasReachedMaxLevel);
        inventory.setItem(index, item);
        return true;
    }

    public static ItemStack getTalentItemStack(@NonNull TalentItemsEnum talentItemType, int level, boolean equipped, boolean hasReachedMaxLevel) {
        return talentItemType.getNamedItem(level, equipped, hasReachedMaxLevel);
    }

    public static double getNextLevelNeedCoinAmount(TalentItemsEnum talentItemType, int currentLevel) {
        return talentItemType.getNextLevelNeedCoinValue(currentLevel + 1);
    }

    public static int getNotEquippedTalentIdByInventoryIndex(int inventoryIndex) {
        for (TalentIdEnum talentId : TalentIdEnum.values()) {
            if (talentId.getInventoryIndex() == inventoryIndex) {
                return talentId.getId();
            }
        }
        throw new NullPointerException();
    }

    public static int getEquipGridIdByInventoryIndex(int inventoryIndex) {
        return inventoryIndex - 37;
    }

    public static TalentItemsEnum getTalentItemEnumById(int id) {
        for (TalentItemsEnum talentItemsEnum : TalentItemsEnum.values()) {
            if (talentItemsEnum.getId() == id) {
                return talentItemsEnum;
            }
        }
        return null;
    }

    public static int getEquippedTalentIdByInventoryIndex(Player player, int inventoryIndex) {
        PlayerDataBlock dataBlock = Main.getPlayerDataBlock(player);
        ArrayList<?> equippedTalentList = dataBlock.getEquippedTalentList();
        int gridId = getEquipGridIdByInventoryIndex(inventoryIndex);
        return (int) equippedTalentList.get(gridId);
    }
}
