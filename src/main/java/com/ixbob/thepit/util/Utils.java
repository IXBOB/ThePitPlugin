package com.ixbob.thepit.util;

import com.ixbob.thepit.Main;
import com.ixbob.thepit.MongoDB;
import com.ixbob.thepit.PlayerDataBlock;
import com.ixbob.thepit.event.custom.PlayerOwnCoinModifiedEvent;
import com.ixbob.thepit.event.custom.PlayerOwnXpModifiedEvent;
import com.mongodb.DBObject;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

public class Utils {
    private static MongoDB mongoDB = Main.getDB();
    public static void storePlayerInventoryData(Player player) {
        UUID playerUUID = player.getUniqueId();
        DBObject dataDBObj = mongoDB.findByUUID(playerUUID);
        ArrayList<ArrayList<?>> hotBarItemList = new ArrayList<>(Collections.nCopies(9 ,null));
        ArrayList<ArrayList<?>> inventoryItemList = new ArrayList<>(Collections.nCopies(27 ,null));
        ArrayList<ArrayList<?>> armorItemList = new ArrayList<>(Collections.nCopies(4 ,null));
        ArrayList<ArrayList<?>> offHandItemList = new ArrayList<>(Collections.nCopies(1 ,null)); //虽然只有一个，但为了统一一点，都用List
        for (int i = 0; i <= 8; i++) {
            ItemStack indexItem = player.getInventory().getItem(i);
            if (indexItem != null) {
                hotBarItemList.set(i, new ArrayList<>(Arrays.asList(indexItem.getType().name(), indexItem.getAmount(), new ArrayList<>())));
            }
        }
        for (int i = 0; i <= 26; i++) {
            ItemStack indexItem = player.getInventory().getItem(i+9);
            if (indexItem != null) {
                inventoryItemList.set(i, new ArrayList<>(Arrays.asList(indexItem.getType().name(), indexItem.getAmount(), new ArrayList<>())));
            }
        }
        for (int i = 0; i <= 3; i++) {
            ItemStack indexItem = player.getInventory().getArmorContents()[i];
            if (indexItem != null) {
                armorItemList.set(i, new ArrayList<>(Arrays.asList(indexItem.getType().name(), indexItem.getAmount(), new ArrayList<>())));
            }
        }
        ItemStack itemInOffHand = player.getInventory().getItemInOffHand();
        if (itemInOffHand != null) {
            offHandItemList.set(0, new ArrayList<>(Arrays.asList(itemInOffHand.getType().name(), itemInOffHand.getAmount(), new ArrayList<>())));
        }

        dataDBObj.put("HotBarItemList", hotBarItemList);
        dataDBObj.put("InventoryItemList", inventoryItemList);
        dataDBObj.put("ArmorItemList", armorItemList);
        dataDBObj.put("OffHandItemList", offHandItemList);
        mongoDB.updateDataByUUID(dataDBObj, playerUUID);
    }

    public static void addXp(Player player, int addXp) {
        PlayerDataBlock playerData = Main.playerDataMap.get(player);
        int originXp = playerData.getThisLevelOwnXp();
        int newXp = originXp + addXp;
        playerData.setThisLevelOwnXp(newXp);
        //创建并广播 拥有经验改变事件
        PlayerOwnXpModifiedEvent modifyXpEvent = new PlayerOwnXpModifiedEvent(player, originXp, addXp);
        Bukkit.getPluginManager().callEvent(modifyXpEvent);
    }

    public static void addCoin(Player player, int addCoin) {
        PlayerDataBlock playerData = Main.playerDataMap.get(player);
        int originCoin = playerData.getCoinAmount();
        int newCoin = originCoin + addCoin;
        playerData.setCoinAmount(newCoin);
        //创建并广播 拥有硬币改变事件
        PlayerOwnCoinModifiedEvent modifyCoinEvent = new PlayerOwnCoinModifiedEvent(player, originCoin, addCoin);
        Bukkit.getPluginManager().callEvent(modifyCoinEvent);
    }
}
