package com.ixbob.thepit.event;

import com.ixbob.thepit.Main;
import com.ixbob.thepit.MongoDB;
import com.ixbob.thepit.PlayerDataBlock;
import com.ixbob.thepit.handler.config.LangLoader;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

public class OnPlayerJoinListener implements Listener {
    private Player player;
    private final MongoDB mongoDB = Main.getDB();
    private UUID playerUUID;
    private String playerUUIDString;
    private final int initLevel = 0;
    private final int initRank = 0;
    private final int initCoinAmount = 0;
    private final int initPrestige = 0;
    private final String initPrefix = "0";
    private final int initKillAmount = 0;
    private final int initDeathAmount = 0;
    private final ArrayList<ArrayList<?>> initHotBar = new ArrayList<>(Collections.nCopies(9 ,null));
    private final ArrayList<ArrayList<?>> initInventory = new ArrayList<>(Collections.nCopies(27 ,null));
    private final ArrayList<ArrayList<?>> initArmor = new ArrayList<>(Collections.nCopies(4 ,null));
    private final ArrayList<ArrayList<?>> initOffHand = new ArrayList<>(Collections.nCopies(1 ,null)); //虽然只有一个，但为了统一一点，都用List

    public OnPlayerJoinListener() {
        initHotBar.set(0, new ArrayList<>(Arrays.asList(Material.STONE_SWORD.name(), 1, new ArrayList<>())));
        initHotBar.set(1, new ArrayList<>(Arrays.asList(Material.BOW.name(), 1, new ArrayList<>())));
        initHotBar.set(2, new ArrayList<>(Arrays.asList(Material.ARROW.name(), 8, new ArrayList<>())));

        initArmor.set(1, new ArrayList<>(Arrays.asList(Material.CHAINMAIL_CHESTPLATE.name(), 1, new ArrayList<>())));
        initArmor.set(2, new ArrayList<>(Arrays.asList(Material.CHAINMAIL_LEGGINGS.name(), 1, new ArrayList<>())));
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        this.player = event.getPlayer();
        this.playerUUID = player.getUniqueId();

        player.getInventory().clear();
        player.sendTitle(LangLoader.get("join_loading_title"), LangLoader.get("join_loading_subtitle"), 10, 60, 10);
        Bukkit.getServer().getScheduler().runTaskAsynchronously(Main.getPlugin(), () -> {
            Player taskPlayer = player;
            if (!mongoDB.isFindByUUIDExist(playerUUID)) {
                 createDBData(taskPlayer);
            }
            readDBAndGenPlayerDataBlock(taskPlayer);

            @SuppressWarnings("unchecked")
            ArrayList<ArrayList<?>> storedHotBarItemList = (ArrayList<ArrayList<?>>) mongoDB.findByUUID(playerUUID).get("HotBarItemList");
            @SuppressWarnings("unchecked")
            ArrayList<ArrayList<?>> storedInventoryItemList = (ArrayList<ArrayList<?>>) mongoDB.findByUUID(playerUUID).get("InventoryItemList");
            @SuppressWarnings("unchecked")
            ArrayList<ArrayList<?>> storedArmorItemList = (ArrayList<ArrayList<?>>) mongoDB.findByUUID(playerUUID).get("ArmorItemList");
            @SuppressWarnings("unchecked")
            ArrayList<ArrayList<?>> storedOffHandItemList = (ArrayList<ArrayList<?>>) mongoDB.findByUUID(playerUUID).get("OffHandItemList");

            PlayerInventory inventory = taskPlayer.getInventory();
            //slot ID: https://i.stack.imgur.com/kiUcV.jpg
            for (int i = 0; i <= 8; i++) {
                ArrayList<?> handlingInsideList = storedHotBarItemList.get(i);
                if (handlingInsideList != null) {
                    ItemStack itemStack = new ItemStack(Material.valueOf((String) handlingInsideList.get(0)), (int) handlingInsideList.get(1));
                    inventory.setItem(i, itemStack);
                }
            }
            for (int i = 0; i <= 26; i++) {
                ArrayList<?> handlingInsideList = storedInventoryItemList.get(i);
                if (handlingInsideList != null) {
                    ItemStack itemStack = new ItemStack(Material.valueOf((String) handlingInsideList.get(0)), (int) handlingInsideList.get(1));
                    inventory.setItem(i+9, itemStack);
                }
            }
            for (int i = 0; i <= 3; i++) {
                ArrayList<?> handlingInsideList = storedArmorItemList.get(i);
                if (handlingInsideList != null) {
                    ItemStack itemStack = new ItemStack(Material.valueOf((String) handlingInsideList.get(0)), (int) handlingInsideList.get(1));
                    switch (i) {
                        case 0: inventory.setHelmet(itemStack); break;
                        case 1: inventory.setChestplate(itemStack); break;
                        case 2: inventory.setLeggings(itemStack); break;
                        case 3: inventory.setBoots(itemStack); break;
                    }
                }
            }
            if (!storedOffHandItemList.isEmpty()) {
                ArrayList<?> handlingInsideList = storedOffHandItemList.get(0);
                if (handlingInsideList != null) {
                    ItemStack itemStack = new ItemStack(Material.valueOf((String) handlingInsideList.get(0)), (int) handlingInsideList.get(1));
                    inventory.setItemInOffHand(itemStack);
                }
            }
        });
    }
    private void createDBData(Player taskPlayer) {
        playerUUIDString = taskPlayer.getUniqueId().toString();
        DBObject dataDBObj = new BasicDBObject("UUID", playerUUIDString);
        dataDBObj.put("player_name", taskPlayer.getName());
        dataDBObj.put("level", initLevel);
        dataDBObj.put("rank", initRank);
        dataDBObj.put("coin_amount", initCoinAmount);
        dataDBObj.put("prestige", initPrestige);
        dataDBObj.put("prefix", initPrefix); // 称号，0相当于什么都没有吧
        dataDBObj.put("kill_amount", initKillAmount);
        dataDBObj.put("death_amount", initDeathAmount);
        dataDBObj.put("HotBarItemList", initHotBar);
        dataDBObj.put("InventoryItemList", initInventory);
        dataDBObj.put("ArmorItemList", initArmor);
        dataDBObj.put("OffHandItemList", initOffHand);
        mongoDB.insert(dataDBObj);
    }

    private void readDBAndGenPlayerDataBlock(Player taskPlayer) {
        DBObject dataDBObj = mongoDB.findByUUID(taskPlayer.getUniqueId());
        PlayerDataBlock dataBlock = new PlayerDataBlock(
                taskPlayer,
                (int) dataDBObj.get("level"),
                (int) dataDBObj.get("rank"),
                (int) dataDBObj.get("coin_amount"),
                (int) dataDBObj.get("prestige"),
                (String) dataDBObj.get("prefix"),
                (int) dataDBObj.get("kill_amount"),
                (int) dataDBObj.get("death_amount"));
        Main.playerDataMap.put(taskPlayer, dataBlock);
    }
}
