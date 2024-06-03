package com.ixbob.thepit.util;

import com.ixbob.thepit.Main;
import com.ixbob.thepit.MongoDB;
import com.ixbob.thepit.PlayerDataBlock;
import com.ixbob.thepit.enums.CustomBasicToolEnum;
import com.ixbob.thepit.enums.gui.talent.GUITalentItemEnum;
import com.ixbob.thepit.enums.gui.talent.TalentGivingItemEnum;
import com.ixbob.thepit.event.custom.*;
import com.mongodb.DBObject;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

public class PlayerUtils {
    public static void storePlayerInventoryData(Player player) {
        MongoDB mongoDB = Main.getDB();
        UUID playerUUID = player.getUniqueId();
        DBObject dataDBObj = mongoDB.findByUUID(playerUUID);
        ArrayList<ArrayList<?>> hotBarItemList = new ArrayList<>(Collections.nCopies(9 ,null));
        ArrayList<ArrayList<?>> inventoryItemList = new ArrayList<>(Collections.nCopies(27 ,null));
        ArrayList<ArrayList<?>> armorItemList = new ArrayList<>(Collections.nCopies(4 ,null));
        for (int i = 0; i <= 8; i++) {
            ItemStack indexItem = player.getInventory().getItem(i);
            if (indexItem != null) {
                hotBarItemList.set(i, new ArrayList<>(Arrays.asList(indexItem.getType().name(), indexItem.getAmount(), Utils.readItemExtraData(indexItem))));
            }
        }
        for (int i = 0; i <= 26; i++) {
            ItemStack indexItem = player.getInventory().getItem(i+9);
            if (indexItem != null) {
                inventoryItemList.set(i, new ArrayList<>(Arrays.asList(indexItem.getType().name(), indexItem.getAmount(), Utils.readItemExtraData(indexItem))));
            }
        }
        for (int i = 0; i <= 3; i++) {
            ItemStack indexItem = player.getInventory().getArmorContents()[3 - i];  //默认i=0:脚，3-i:从头上往脚下读
            if (indexItem != null) {
                armorItemList.set(i, new ArrayList<>(Arrays.asList(indexItem.getType().name(), indexItem.getAmount(), Utils.readItemExtraData(indexItem))));
            }
        }

        dataDBObj.put("HotBarItemList", hotBarItemList);
        dataDBObj.put("InventoryItemList", inventoryItemList);
        dataDBObj.put("ArmorItemList", armorItemList);
        mongoDB.updateDataByUUID(dataDBObj, playerUUID);
    }

    public static void setMostBasicKit(Player player, boolean clear) {
        PlayerInventory inventory = player.getInventory();
        PlayerDataBlock dataBlock = Main.getPlayerDataBlock(player);
        ArrayList<?> equippedTalentList = dataBlock.getEquippedNormalTalentList();
        if (clear) {
            inventory.clear();
            inventory.addItem(CustomBasicToolEnum.BASIC_STONE_SWORD.getItemStack());
            inventory.addItem(CustomBasicToolEnum.BASIC_BOW.getItemStack());
            if (equippedTalentList.contains(GUITalentItemEnum.FISHERMAN.getId())) {
                inventory.addItem(TalentGivingItemEnum.DEFAULT_FISHING_ROD.getItemStack());
            }
            if (equippedTalentList.contains(GUITalentItemEnum.SAFETY_FIRST.getId())) {
                inventory.setHelmet(TalentGivingItemEnum.DEFAULT_CHAINMAIL_HELMET.getItemStack());
            }
            if (equippedTalentList.contains(GUITalentItemEnum.MINER.getId())) {
                player.getInventory().addItem(TalentGivingItemEnum.DIAMOND_PICKAXE_EFFICIENCY_4.getItemStack());
                player.getInventory().addItem(TalentGivingItemEnum.DEFAULT_COBBLESTONE.getItemStack((int) TalentCalcuUtils.getAddPointValue(GUITalentItemEnum.MINER.getId(), dataBlock.getNormalTalentLevelList().get(GUITalentItemEnum.MINER.getId()))));
            }
            inventory.setChestplate(CustomBasicToolEnum.BASIC_CHAINMAIL_CHESTPLATE.getItemStack());
            inventory.setLeggings(CustomBasicToolEnum.BASIC_CHAINMAIL_LEGGINGS.getItemStack());
        }
        inventory.remove(Material.COOKED_BEEF);
        inventory.addItem(new ItemStack(Material.COOKED_BEEF, 64));
        inventory.remove(Material.ARROW);
        inventory.addItem(new ItemStack(Material.ARROW, 8));

    }

    public static void addXp(Player player, double addXp) {
        if (!player.isOnline()) {
            return;
        }
        PlayerDataBlock playerData = Main.getPlayerDataBlock(player);
        double originXp = playerData.getThisLevelOwnXp();

        PlayerOwnXpModifiedEvent modifyXpEvent = new PlayerOwnXpModifiedEvent(player, originXp, addXp);
        Bukkit.getPluginManager().callEvent(modifyXpEvent);
    }

    public static void addCoin(Player player, double addCoin) {
        if (!player.isOnline()) {
            return;
        }
        PlayerDataBlock playerData = Main.getPlayerDataBlock(player);
        double originCoin = playerData.getCoinAmount();

        PlayerOwnCoinModifiedEvent modifyCoinEvent = new PlayerOwnCoinModifiedEvent(player, originCoin, addCoin);
        Bukkit.getPluginManager().callEvent(modifyCoinEvent);
    }

    public static void setBattleState(Player player, boolean battleState) {
        PlayerBattleStateChangeEvent playerBattleStateChangeEvent = new PlayerBattleStateChangeEvent(player, battleState);
        Bukkit.getPluginManager().callEvent(playerBattleStateChangeEvent);
    }

    public static void setTypedSpawn(Player player, boolean typedSpawn) {
        PlayerTypedSpawnChangeEvent playerTypedSpawnChangeEvent = new PlayerTypedSpawnChangeEvent(player, typedSpawn);
        Bukkit.getPluginManager().callEvent(playerTypedSpawnChangeEvent);
    }

    public static void setTalentLevel(Player player, int id, int level) {
        PlayerTalentLevelChangeEvent playerTalentLevelChangeEvent = new PlayerTalentLevelChangeEvent(player, id, level);
        Bukkit.getPluginManager().callEvent(playerTalentLevelChangeEvent);
    }

    public static void changeEquippedTalent(Player player, int index, GUITalentItemEnum talentItem, boolean equipped) {
        PlayerEquippedTalentChangeEvent playerEquippedTalentChangeEvent = new PlayerEquippedTalentChangeEvent(player, index, talentItem, equipped);
        Bukkit.getPluginManager().callEvent(playerEquippedTalentChangeEvent);
    }

    public static void updateDisplayName(Player player) {
        String displayName = getPitDisplayName(player);
        player.setDisplayName(displayName);
        player.setPlayerListName(displayName);
    }

    public static String getPitDisplayName(Player player) {
        PlayerDataBlock playerData = Main.getPlayerDataBlock(player);
        int prestigeLevel = playerData.getPrestigeLevel();
        int level = playerData.getLevel();
        String prefix;
        ChatColor color = Utils.getChatColorByLevel(level);
        prefix = Utils.getLevelStrWithStyle(prestigeLevel, level);
        return prefix + color + player.getName() + ChatColor.RESET;
    }
}
