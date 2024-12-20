package com.ixbob.thepit.event;

import com.ixbob.thepit.*;
import com.ixbob.thepit.enums.ItemExtraDataEnum;
import com.ixbob.thepit.service.MongoDBService;
import com.ixbob.thepit.task.RefreshPlayerHologramVisibleStateRunnable;
import com.ixbob.thepit.util.ItemExtraDataApplier;
import com.ixbob.thepit.util.ServiceUtils;
import com.ixbob.thepit.util.Utils;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.util.Ticks;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

public class OnPlayerJoinListener implements Listener {
    private Player player;
    private final MongoDBService mongoDB = ServiceUtils.getMongoDBService();
    private UUID playerUUID;
    private String playerUUIDString;
    private final ArrayList<ArrayList<?>> initHotBar = new ArrayList<>(Collections.nCopies(9 ,null));
    private final ArrayList<ArrayList<?>> initInventory = new ArrayList<>(Collections.nCopies(27 ,null));
    private final ArrayList<ArrayList<?>> initArmor = new ArrayList<>(Collections.nCopies(4 ,null));
    private final ArrayList<Integer> initNormalTalentLevel = new ArrayList<>(Collections.nCopies(100 ,0));
    private final ArrayList<?> initEquippedNormalTalent = new ArrayList<>(Collections.nCopies(3 ,null));

    public OnPlayerJoinListener() {
        initHotBar.set(0, new ArrayList<>(Arrays.asList(Material.STONE_SWORD.name(), 1, new ArrayList<>(Arrays.asList(ItemExtraDataEnum.ENCHANT_UNBREAKABLE.toString())))));
        initHotBar.set(1, new ArrayList<>(Arrays.asList(Material.BOW.name(), 1, new ArrayList<>(Arrays.asList(ItemExtraDataEnum.ENCHANT_UNBREAKABLE.toString())))));
        initHotBar.set(2, new ArrayList<>(Arrays.asList(Material.ARROW.name(), 8, new ArrayList<>())));
        initHotBar.set(3, new ArrayList<>(Arrays.asList(Material.COOKED_BEEF.name(), 64, new ArrayList<>())));

        initArmor.set(1, new ArrayList<>(Arrays.asList(Material.CHAINMAIL_CHESTPLATE.name(), 1, new ArrayList<>(Arrays.asList(ItemExtraDataEnum.ENCHANT_UNBREAKABLE.toString())))));
        initArmor.set(2, new ArrayList<>(Arrays.asList(Material.CHAINMAIL_LEGGINGS.name(), 1, new ArrayList<>(Arrays.asList(ItemExtraDataEnum.ENCHANT_UNBREAKABLE.toString())))));
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        this.player = event.getPlayer();
        this.playerUUID = player.getUniqueId();

        if (!HologramManager.getInstance().isInitialized()) {
            player.kick(Component.text(LangLoader.getString("server_prepare_not_finished_kick_message")));
            return;
        }

        for (PotionEffect potionEffect : player.getActivePotionEffects()) {
            player.removePotionEffect(potionEffect.getType());
        }

        Main.signInPlayerAmountFromLaunch++;
        Utils.backToLobby(player);
        player.setGameMode(GameMode.SURVIVAL);
        player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(200);
        player.getInventory().clear();
        Title title = Title.title(Component.text(LangLoader.getString("join_loading_title")),
                Component.text(LangLoader.getString("join_loading_subtitle")),
                Title.Times.of(Ticks.duration(10L), Ticks.duration(60L), Ticks.duration(10L)));
        player.showTitle(title);

        Bukkit.getServer().getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
            Player taskPlayer = player;
            if (!mongoDB.isFindByUUIDExist(playerUUID)) {
                 createDBData(taskPlayer);
            }
            Bukkit.getServer().getScheduler().runTask(Main.getInstance(), () -> {
                genPlayerDataBlock(taskPlayer);
                Bukkit.getScheduler().runTask(Main.getInstance(), new RefreshPlayerHologramVisibleStateRunnable(player));
//                NMSUtils.sendPacketForChangeDisplayName(player);
                TeamManager.getInstance().joinTeamToChangeDisplayName(player);


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
                        @SuppressWarnings("unchecked")
                        ArrayList<String> dataList = (ArrayList<String>) handlingInsideList.get(2);
                        inventory.setItem(i, getItem(itemStack, dataList));
                    }
                }
                for (int i = 0; i <= 26; i++) {
                    ArrayList<?> handlingInsideList = storedInventoryItemList.get(i);
                    if (handlingInsideList != null) {
                        ItemStack itemStack = new ItemStack(Material.valueOf((String) handlingInsideList.get(0)), (int) handlingInsideList.get(1));
                        @SuppressWarnings("unchecked")
                        ArrayList<String> dataList = (ArrayList<String>) handlingInsideList.get(2);
                        inventory.setItem(i+9, getItem(itemStack, dataList));
                    }
                }
                for (int i = 0; i <= 3; i++) {
                    ArrayList<?> handlingInsideList = storedArmorItemList.get(i);
                    if (handlingInsideList != null) {
                        ItemStack itemStack = new ItemStack(Material.valueOf((String) handlingInsideList.get(0)), (int) handlingInsideList.get(1));
                        @SuppressWarnings("unchecked")
                        ArrayList<String> dataList = (ArrayList<String>) handlingInsideList.get(2);
                        switch (i) {
                            case 0: inventory.setHelmet(getItem(itemStack, dataList)); break;
                            case 1: inventory.setChestplate(getItem(itemStack, dataList)); break;
                            case 2: inventory.setLeggings(getItem(itemStack, dataList)); break;
                            case 3: inventory.setBoots(getItem(itemStack, dataList)); break;
                        }
                    }
                }
            });
        });
    }

    private ItemStack getItem(ItemStack itemStack, ArrayList<String> dataList) {
        return dataList == null ? itemStack : ItemExtraDataApplier.applyFromList(itemStack, dataList);
    }

    private void createDBData(Player taskPlayer) {
        playerUUIDString = taskPlayer.getUniqueId().toString();
        DBObject dataDBObj = new BasicDBObject("UUID", playerUUIDString);
        dataDBObj.put("player_name", taskPlayer.getName());
        dataDBObj.put("level", 0);
        dataDBObj.put("prestige_level", 0);
        dataDBObj.put("prestige_point_amount", 0);
        dataDBObj.put("this_level_own_xp", (double) 0);
        dataDBObj.put("next_level_need_xp", (2 + (Math.random()*2)));
        dataDBObj.put("xp_amount", (double) 0);
        dataDBObj.put("coin_amount", (double) 5000);
        dataDBObj.put("prefix", "0"); // 称号，0相当于什么都没有吧
        dataDBObj.put("kill_amount", 0);
        dataDBObj.put("death_amount", 0);
        dataDBObj.put("HotBarItemList", initHotBar);
        dataDBObj.put("InventoryItemList", initInventory);
        dataDBObj.put("ArmorItemList", initArmor);
        dataDBObj.put("NormalTalentLevelList", initNormalTalentLevel);
        dataDBObj.put("EquippedNormalTalentList", initEquippedNormalTalent);
        mongoDB.insert(dataDBObj);
    }

    private void genPlayerDataBlock(Player taskPlayer) {
        PlayerDataBlock dataBlock = new PlayerDataBlock(taskPlayer);
        Main.addPlayerDataBlock(taskPlayer, dataBlock);
        dataBlock.init();
    }
}
