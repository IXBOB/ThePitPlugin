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

import java.util.ArrayList;
import java.util.Arrays;
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
    private final ArrayList<ArrayList<?>> initHotBar = new ArrayList<>();
    private final ArrayList<ArrayList<?>> initInventory = new ArrayList<>();
    private final ArrayList<ArrayList<?>> initArmor = new ArrayList<>();
    private final ArrayList<ArrayList<?>> initOffHand = new ArrayList<>(); //虽然只有一个，但为了统一一点，都用List

    public OnPlayerJoinListener() {
        initHotBar.add(0, new ArrayList<>(Arrays.asList("STONE_SWORD", 1)));
        initHotBar.add(1, new ArrayList<>(Arrays.asList("BOW", 1)));
        initHotBar.add(2, new ArrayList<>(Arrays.asList("ARROW", 8)));
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        this.player = event.getPlayer();
        this.playerUUID = player.getUniqueId();

        player.sendTitle(LangLoader.get("join_loading_title"), LangLoader.get("join_loading_subtitle"), 10, 60, 10);
        System.out.println("test1");
        Bukkit.getServer().getScheduler().runTaskAsynchronously(Main.getPlugin(), () -> {
           if (!mongoDB.isFindByUUIDExist(playerUUID)) {
                createDBData();
           }
           genPlayerDataBlock();

            System.out.println(mongoDB.findByUUID(playerUUID).get("HotBarItemList"));
        });
    }
    private void createDBData() {
        playerUUIDString = playerUUID.toString();
        DBObject dataDBObj = new BasicDBObject("UUID", playerUUIDString);
        dataDBObj.put("player_name", player.getName());
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

    private void genPlayerDataBlock() {
        PlayerDataBlock dataBlock = new PlayerDataBlock(player, initLevel, initRank, initCoinAmount, initPrestige, initPrefix, initKillAmount, initDeathAmount);
        Main.playerDataMap.put(player, dataBlock);
    }
}
