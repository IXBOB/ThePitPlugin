package com.ixbob.thepit.event;

import com.ixbob.thepit.*;
import com.ixbob.thepit.enums.ItemExtraData;
import com.ixbob.thepit.util.ItemExtraDataApplier;
import com.ixbob.thepit.util.Utils;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

public class OnPlayerJoinListener implements Listener {
    private Player player;
    private final MongoDB mongoDB = Main.getDB();
    private UUID playerUUID;
    private String playerUUIDString;
    private final ArrayList<ArrayList<?>> initHotBar = new ArrayList<>(Collections.nCopies(9 ,null));
    private final ArrayList<ArrayList<?>> initInventory = new ArrayList<>(Collections.nCopies(27 ,null));
    private final ArrayList<ArrayList<?>> initArmor = new ArrayList<>(Collections.nCopies(4 ,null));
    private final ArrayList<Integer> initTalentLevel = new ArrayList<>(Collections.nCopies(100 ,0));
    private final ArrayList<?> initEquippedTalent = new ArrayList<>(Collections.nCopies(7 ,null));

    public OnPlayerJoinListener() {
        initHotBar.set(0, new ArrayList<>(Arrays.asList(Material.STONE_SWORD.name(), 1, new ArrayList<>(Arrays.asList(ItemExtraData.UNBREAKABLE.toString())))));
        initHotBar.set(1, new ArrayList<>(Arrays.asList(Material.BOW.name(), 1, new ArrayList<>(Arrays.asList(ItemExtraData.UNBREAKABLE.toString())))));
        initHotBar.set(2, new ArrayList<>(Arrays.asList(Material.ARROW.name(), 8, new ArrayList<>())));
        initHotBar.set(3, new ArrayList<>(Arrays.asList(Material.COOKED_BEEF.name(), 64, new ArrayList<>())));

        initArmor.set(1, new ArrayList<>(Arrays.asList(Material.CHAINMAIL_CHESTPLATE.name(), 1, new ArrayList<>(Arrays.asList(ItemExtraData.UNBREAKABLE.toString())))));
        initArmor.set(2, new ArrayList<>(Arrays.asList(Material.CHAINMAIL_LEGGINGS.name(), 1, new ArrayList<>(Arrays.asList(ItemExtraData.UNBREAKABLE.toString())))));
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        this.player = event.getPlayer();
        this.playerUUID = player.getUniqueId();

        for (PotionEffect potionEffect : player.getActivePotionEffects()) {
            player.removePotionEffect(potionEffect.getType());
        }

        player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(200);
        player.getInventory().clear();
        player.sendTitle(LangLoader.get("join_loading_title"), LangLoader.get("join_loading_subtitle"), 10, 60, 10);
        Bukkit.getServer().getScheduler().runTaskAsynchronously(Main.getPlugin(), () -> {
            Player taskPlayer = player;
            if (!mongoDB.isFindByUUIDExist(playerUUID)) {
                 createDBData(taskPlayer);
            }
            Bukkit.getServer().getScheduler().runTask(Main.getPlugin(), () -> {
                Scoreboard scoreboard = initScoreboardFrame(taskPlayer);
                genPlayerDataBlock(taskPlayer);
                initScoreboardContent(scoreboard, taskPlayer);
                Utils.updateDisplayName(taskPlayer);
                //读取并设置玩家物品栏
                @SuppressWarnings("unchecked")
                ArrayList<ArrayList<?>> storedHotBarItemList = (ArrayList<ArrayList<?>>) mongoDB.findByUUID(playerUUID).get("HotBarItemList");
                @SuppressWarnings("unchecked")
                ArrayList<ArrayList<?>> storedInventoryItemList = (ArrayList<ArrayList<?>>) mongoDB.findByUUID(playerUUID).get("InventoryItemList");
                @SuppressWarnings("unchecked")
                ArrayList<ArrayList<?>> storedArmorItemList = (ArrayList<ArrayList<?>>) mongoDB.findByUUID(playerUUID).get("ArmorItemList");

                PlayerInventory inventory = taskPlayer.getInventory();
                //slot ID: https://i.stack.imgur.com/kiUcV.jpg
                for (int i = 0; i <= 8; i++) {
                    ArrayList<?> handlingInsideList = storedHotBarItemList.get(i);
                    if (handlingInsideList != null) {
                        ItemStack itemStack = new ItemStack(Material.valueOf((String) handlingInsideList.get(0)), (int) handlingInsideList.get(1));
                        ArrayList<String> dataList = (ArrayList<String>) handlingInsideList.get(2);
                        inventory.setItem(i, getItemIfWithExtraData(itemStack, dataList));
                    }
                }
                for (int i = 0; i <= 26; i++) {
                    ArrayList<?> handlingInsideList = storedInventoryItemList.get(i);
                    if (handlingInsideList != null) {
                        ItemStack itemStack = new ItemStack(Material.valueOf((String) handlingInsideList.get(0)), (int) handlingInsideList.get(1));
                        ArrayList<String> dataList = (ArrayList<String>) handlingInsideList.get(2);
                        inventory.setItem(i+9, getItemIfWithExtraData(itemStack, dataList));
                    }
                }
                for (int i = 0; i <= 3; i++) {
                    ArrayList<?> handlingInsideList = storedArmorItemList.get(i);
                    if (handlingInsideList != null) {
                        ItemStack itemStack = new ItemStack(Material.valueOf((String) handlingInsideList.get(0)), (int) handlingInsideList.get(1));
                        ArrayList<String> dataList = (ArrayList<String>) handlingInsideList.get(2);
                        switch (i) {
                            case 0: inventory.setHelmet(getItemIfWithExtraData(itemStack, dataList)); break;
                            case 1: inventory.setChestplate(getItemIfWithExtraData(itemStack, dataList)); break;
                            case 2: inventory.setLeggings(getItemIfWithExtraData(itemStack, dataList)); break;
                            case 3: inventory.setBoots(getItemIfWithExtraData(itemStack, dataList)); break;
                        }
                    }
                }

                Utils.backToLobby(player);
            });
        });
    }

    private ItemStack getItemIfWithExtraData(ItemStack itemStack, ArrayList<String> dataList) {
        return dataList == null ? itemStack : ItemExtraDataApplier.applyFromList(itemStack, dataList);
    }

    private void createDBData(Player taskPlayer) {
        playerUUIDString = taskPlayer.getUniqueId().toString();
        DBObject dataDBObj = new BasicDBObject("UUID", playerUUIDString);
        dataDBObj.put("player_name", taskPlayer.getName());
        dataDBObj.put("level", 0);
        dataDBObj.put("rank", 0);
        dataDBObj.put("this_level_own_xp", (double) 0);
        dataDBObj.put("next_level_need_xp", (double)( 2*(0+1) + (Math.random()*2*(0+1)) ));
        dataDBObj.put("coin_amount", (double) 5000);
        dataDBObj.put("prestige_level", 0);
        dataDBObj.put("prestige_point_amount", 0);
        dataDBObj.put("prefix", "0"); // 称号，0相当于什么都没有吧
        dataDBObj.put("consecutive_kill_amount", 0);
        dataDBObj.put("kill_amount", 0);
        dataDBObj.put("death_amount", 0);
        dataDBObj.put("HotBarItemList", initHotBar);
        dataDBObj.put("InventoryItemList", initInventory);
        dataDBObj.put("ArmorItemList", initArmor);
        dataDBObj.put("TalentLevelList", initTalentLevel);
        dataDBObj.put("EquippedTalentList", initEquippedTalent);
        mongoDB.insert(dataDBObj);
    }

    private void genPlayerDataBlock(Player taskPlayer) {
        PlayerDataBlock dataBlock = new PlayerDataBlock(taskPlayer);
        Main.addPlayerDataBlock(taskPlayer, dataBlock);
    }

    private Scoreboard initScoreboardFrame(Player taskPlayer) {
        Scoreboard scoreboard = taskPlayer.getServer().getScoreboardManager().getNewScoreboard();
        Objective boardObj = scoreboard.registerNewObjective("main", "dummy");
        boardObj.setDisplayName(ChatColor.YELLOW + "" + ChatColor.BOLD + LangLoader.get("game_name"));
        boardObj.setDisplaySlot(DisplaySlot.SIDEBAR);
        taskPlayer.setScoreboard(scoreboard); //setScoreboard(): 设置玩家可见的计分板

        return scoreboard;
    }

    private void initScoreboardContent(Scoreboard scoreboard, Player taskPlayer) {
        Objective boardObj = scoreboard.getObjective("main");

        PlayerDataBlock playerData = Main.getPlayerDataBlock(taskPlayer);
        boardObj.getScore(LangLoader.get("main_scoreboard_line1")).setScore(0);
        boardObj.getScore(String.format(LangLoader.get("main_scoreboard_line2"), playerData.getLevel())).setScore(-1);
        boardObj.getScore(String.format(LangLoader.get("main_scoreboard_line3"), Mth.formatDecimalWithFloor(playerData.getNextLevelNeedXp()-playerData.getThisLevelOwnXp(), 2))).setScore(-2);
        boardObj.getScore(LangLoader.get("main_scoreboard_line4")).setScore(-3);
        boardObj.getScore(String.format(LangLoader.get("main_scoreboard_line5"), Mth.formatDecimalWithFloor(playerData.getCoinAmount(), 1))).setScore(-4);
        boardObj.getScore(LangLoader.get("main_scoreboard_line6")).setScore(-5);
        boardObj.getScore(LangLoader.get("battle_state_false_scoreboard_line7")).setScore(-6);
        boardObj.getScore(LangLoader.get("main_scoreboard_line8")).setScore(-7);
        boardObj.getScore(LangLoader.get("main_scoreboard_line9")).setScore(-8);
    }
}
