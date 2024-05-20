package com.ixbob.thepit.util;

import com.ixbob.thepit.LangLoader;
import com.ixbob.thepit.Main;
import com.ixbob.thepit.PlayerDataBlock;
import com.ixbob.thepit.enums.TalentIdEnum;
import com.ixbob.thepit.enums.TalentItemsEnum;
import lombok.NonNull;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class TalentUtils {
    public static boolean setTalentItem(Inventory inventory, int index, TalentItemsEnum talentItem, int talentLevel, boolean equipped) {
        Player player = (Player) inventory.getHolder();
        PlayerDataBlock playerDataBlock = Main.getPlayerDataBlock(player);

        int playerLevel = playerDataBlock.getLevel();
        int playerPrestigeLevel = playerDataBlock.getPrestigeLevel();
        int needLevel = talentItem.getNeedLevel();
        int needPrestigeLevel = talentItem.getNeedPrestigeLevel();
        if (playerPrestigeLevel < needPrestigeLevel || (playerPrestigeLevel == needPrestigeLevel && playerLevel < needLevel)) { //不满足条件，不放置
            return false;
        }
        ItemStack item = getTalentItemStack(talentItem, talentLevel, equipped);
        inventory.setItem(index, item);
        return true;
    }

    public static ItemStack getTalentItemStack(@NonNull TalentItemsEnum talentItemType, int level, boolean equipped) {
        ItemStack itemStack = talentItemType.getItemStack(1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> loreList = talentItemType.getLoreList();
        int id = talentItemType.getId();

        if (talentItemType == TalentItemsEnum.HEALTH_BOOST) {
            loreList.replaceAll(s -> s.replace("%Level%", String.valueOf(level))
                    .replace("%NextLevelNeedCoin%", String.valueOf(getNextLevelNeedCoinAmount(talentItemType, level)))
                    .replace("%AddPoint%", String.valueOf(TalentCalcuUtils.getValue(id, level)))
                    .replace("%ActionLeftClick%", String.valueOf(equipped ? LangLoader.get("talent_item_click_action_off") : LangLoader.get("talent_item_click_action_equip"))));
            itemMeta.setLore(loreList);
            itemMeta.setDisplayName(talentItemType.getDisplayName());
            itemStack.setItemMeta(itemMeta);
            return itemStack;
        }
        if (talentItemType == TalentItemsEnum.GOLDEN_CHOCOLATE) {
            loreList.replaceAll(s -> s.replace("%Level%", String.valueOf(level))
                    .replace("%NextLevelNeedCoin%", String.valueOf(getNextLevelNeedCoinAmount(talentItemType, level)))
                    .replace("%AddPoint%", String.valueOf(TalentCalcuUtils.getValue(id, level)))
                    .replace("%ActionLeftClick%", String.valueOf(equipped ? LangLoader.get("talent_item_click_action_off") : LangLoader.get("talent_item_click_action_equip"))));
            itemMeta.setLore(loreList);
            itemMeta.setDisplayName(talentItemType.getDisplayName());
            itemStack.setItemMeta(itemMeta);
            return itemStack;
        }
        throw new RuntimeException();
    }

    public static int getNextLevelNeedCoinAmount(TalentItemsEnum talentItemType, int currentLevel) {
        if (talentItemType == TalentItemsEnum.HEALTH_BOOST) {
            return (currentLevel + 1) * 500;
        }
        if (talentItemType == TalentItemsEnum.GOLDEN_CHOCOLATE) {
            return (currentLevel + 1) * 400;
        }
        throw new RuntimeException();
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
}
