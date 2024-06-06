package com.ixbob.thepit.util;

import com.ixbob.thepit.LangLoader;
import com.ixbob.thepit.Main;
import com.ixbob.thepit.Mth;
import com.ixbob.thepit.PlayerDataBlock;
import com.ixbob.thepit.enums.CustomBasicToolEnum;
import com.ixbob.thepit.enums.PitItemEnum;
import com.ixbob.thepit.enums.gui.talent.GUITalentItemEnum;
import com.ixbob.thepit.enums.gui.talent.TalentGivingItemEnum;
import com.ixbob.thepit.event.custom.*;
import com.ixbob.thepit.service.MongoDBService;
import com.mongodb.DBObject;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;

import java.util.*;

public class PlayerUtils {
    public static void storePlayerInventoryData(Player player) {
        MongoDBService mongoDB = ServiceUtils.getMongoDBService();
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

    public static void onPlayerKillAnother(@NonNull Player damagedPlayer, @NonNull Player damager) {
        PlayerDataBlock damagedPlayerDataBlock = Main.getPlayerDataBlock(damagedPlayer);
        PlayerDataBlock damagerDataBlock = Main.getPlayerDataBlock(damager);
        double killAddXp = (10 + (Math.random() * 15)) * (1 + Main.getPlayerDataBlock(damagedPlayer).getConsecutiveKillAmount());
        PlayerUtils.addXp(damager, killAddXp);
        double killAddCoin = (5 + (Math.random() * 10)) * (1 + Main.getPlayerDataBlock(damagedPlayer).getConsecutiveKillAmount());
        PlayerUtils.addCoin(damager, killAddCoin);

        if (damagerDataBlock.getEquippedNormalTalentList().contains(GUITalentItemEnum.FLEXIBLE_TACTICS.getId())) {
            int id = GUITalentItemEnum.FLEXIBLE_TACTICS.getId();
            int level = damagedPlayerDataBlock.getNormalTalentLevelList().get(id);
            float addPoint = TalentCalcuUtils.getAddPointValue(id, level);
            PlayerUtils.addCoin(damager, killAddCoin * addPoint * 0.01);
        }

        damager.playSound(damager.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
        damagedPlayer.playSound(damagedPlayer.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);

        ArrayList<?> damagerEquippedTalentList = damagerDataBlock.getEquippedNormalTalentList();
        //天赋 金色巧克力
        if (damagerEquippedTalentList.contains(GUITalentItemEnum.GOLDEN_CHOCOLATE.getId())) {
            damager.getInventory().addItem(PitItemEnum.GOLDEN_CHOCOLATE.getItemStack());
        } else damager.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 1));
        //天赋 力量
        if (damagerEquippedTalentList.contains(GUITalentItemEnum.STRENGTH.getId())) {
            damagerDataBlock.updateTalentStrengthState(true);
        }
        //天赋 矿工
        if (damagerEquippedTalentList.contains(GUITalentItemEnum.MINER.getId())) {
            damager.getInventory().addItem(TalentGivingItemEnum.DEFAULT_COBBLESTONE.getItemStack(4));
        }

        ArrayList<PitItemEnum> dropItemList = new ArrayList<>(Arrays.asList(
                PitItemEnum.DEFAULT_IRON_HELMET,
                PitItemEnum.DEFAULT_IRON_CHESTPLATE,
                PitItemEnum.DEFAULT_IRON_LEGGINGS,
                PitItemEnum.DEFAULT_IRON_BOOTS));
        ItemStack randomDropItem = dropItemList.stream().skip((int) (dropItemList.size() * Math.random())).findFirst().get().getItemStack();
        Bukkit.getWorlds().get(0).dropItemNaturally(damagedPlayer.getLocation(), randomDropItem);
        if (damagedPlayer.getInventory().contains(Material.OBSIDIAN)) {
            int obsidianAmount = 0;
            for (ItemStack itemStack : damagedPlayer.getInventory().getContents()) {
                if (itemStack != null && itemStack.getType() == Material.OBSIDIAN) {
                    obsidianAmount += itemStack.getAmount();
                }
            }
            Bukkit.getWorlds().get(0).dropItemNaturally(damagedPlayer.getLocation(), new ItemStack(Material.OBSIDIAN, obsidianAmount));
        }

        for (PotionEffect effect : damagedPlayer.getActivePotionEffects()) {
            damagedPlayer.removePotionEffect(effect.getType());
        }

        String broadcastMes = String.format(LangLoader.getString("player_kill_other_message_broadcast"), PlayerUtils.getPitDisplayName(damager), PlayerUtils.getPitDisplayName(damagedPlayer));
        String toDamagerMes = String.format(String.format(LangLoader.getString("player_kill_other_message_to_damager"), PlayerUtils.getPitDisplayName(damagedPlayer))) + " " + String.format(LangLoader.getString("player_get_xp_message"), Mth.formatDecimalWithFloor(killAddXp, 2)) + " " + String.format(LangLoader.getString("player_get_coin_message"), Mth.formatDecimalWithFloor(killAddCoin, 1));
        String toDamagedPlayerMes = String.format(LangLoader.getString("player_kill_other_message_to_damaged_player"), PlayerUtils.getPitDisplayName(damager));

        for (Player onlinePl : Bukkit.getOnlinePlayers()) {
            if ( ! (Objects.equals(onlinePl.getName(), damager.getName()) || Objects.equals(onlinePl.getName(), damagedPlayer.getName()))  ) {
                onlinePl.sendMessage(broadcastMes);
            }
        }
        damager.sendMessage(toDamagerMes);
        damagedPlayer.sendMessage(toDamagedPlayerMes);

        ArrayList<LinkedHashMap<Player, ArrayList<Object>>> damagedHistory = damagedPlayerDataBlock.getPlayerGetDamagedHistory();
        HashMap<Player, Double> damagePercentMap = Utils.getDamagePercentMapSum(damagedHistory);
        ArrayList<Player> helperList = new ArrayList<>();
        for (Player historyDamager : Utils.getDamageHistoryPlayers(damagedHistory)) {
            if (!historyDamager.equals(damager) && historyDamager.isOnline()) {
                helperList.add(historyDamager);
            }
        }

        //助攻
        for (Player helper : helperList) {
            double damagePercent = damagePercentMap.get(helper);
            String displayDamagePercent = Mth.formatDecimalWithFloor(damagePercent * 100, 2); // 显示成百分数
            double helperAddXp = killAddXp * damagePercent;
            double helperAddCoin = killAddCoin * damagePercent;
            PlayerUtils.addXp(helper, helperAddXp);
            PlayerUtils.addCoin(helper, helperAddCoin);

            PlayerDataBlock helperDataBlock = Main.getPlayerDataBlock(helper);
            if (helperDataBlock.getEquippedNormalTalentList().contains(GUITalentItemEnum.FLEXIBLE_TACTICS.getId())) {
                PlayerUtils.addCoin(helper, 2);
                helperAddCoin += 2;
                for (Player helper_ : damagedPlayerDataBlock.getDamagedByArrowPlayers()) {
                    if (helper_.isOnline()) {
                        if (Main.getPlayerDataBlock(helper_).getEquippedNormalTalentList().contains(GUITalentItemEnum.FLEXIBLE_TACTICS.getId())) {
                            PlayerDataBlock helper_DataBlock = Main.getPlayerDataBlock(helper_);
                            //天赋 灵活战术
                            int id = GUITalentItemEnum.FLEXIBLE_TACTICS.getId();
                            int level = helper_DataBlock.getNormalTalentLevelList().get(id);
                            float addPoint = TalentCalcuUtils.getAddPointValue(id, level);
                            double addCoin = helperAddCoin * addPoint * 0.01;
                            PlayerUtils.addCoin(helper, addCoin);
                            helperAddCoin += addCoin;
                        }
                    }
                }
            }
            String displayHelperAddXp = Mth.formatDecimalWithFloor(helperAddXp, 2);
            String displayHelperAddCoin = Mth.formatDecimalWithFloor(helperAddCoin, 2);
            helper.sendMessage(
                    String.format(String.format(String.format(LangLoader.getString("player_help_kill_other_message_to_helper"), PlayerUtils.getPitDisplayName(damagedPlayer), displayDamagePercent)
                            + LangLoader.getString("player_get_xp_message"), displayHelperAddXp) + " "
                            + LangLoader.getString("player_get_coin_message"), displayHelperAddCoin));
        }

        damagedPlayerDataBlock.getPlayerGetDamagedHistory().clear();
        PlayerUtils.setMostBasicKit(damagedPlayer, true);
        PlayerUtils.setBattleState(damagedPlayer, false);
        PlayerUtils.setTypedSpawn(damagedPlayer, false);
        damagedPlayerDataBlock.setDeathAmount(damagedPlayerDataBlock.getDeathAmount() + 1);
        damagerDataBlock.setKillAmount(damagerDataBlock.getKillAmount() + 1);
        Utils.backToLobby(damagedPlayer);
    }

    public static void onPlayerSuicide(@NonNull Player player) {
        player.sendMessage(LangLoader.getString("player_suicide_message_to_player"));
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (!onlinePlayer.equals(player)) {
                onlinePlayer.sendMessage(String.format(LangLoader.getString("player_suicide_message_broadcast"), PlayerUtils.getPitDisplayName(player)));
            }
        }

        PlayerDataBlock playerDataBlock = Main.getPlayerDataBlock(player);
        playerDataBlock.getPlayerGetDamagedHistory().clear();
        PlayerUtils.setMostBasicKit(player, true);
        PlayerUtils.setBattleState(player, false);
        PlayerUtils.setTypedSpawn(player, false);
        playerDataBlock.setDeathAmount(playerDataBlock.getDeathAmount() + 1);
        Utils.backToLobby(player);
    }

    public static void onPlayerEscapePunish(Player player) {

        EntityDamageEvent event = player.getLastDamageCause();

        //玩家输入/spawn confirm前受伤了
        if (event != null && !event.isCancelled() && (event instanceof EntityDamageByEntityEvent)) {
            EntityDamageByEntityEvent entityDamageByEntityEvent = (EntityDamageByEntityEvent) event;
            Entity lastDamageEntity = entityDamageByEntityEvent.getDamager();

            Player damager = null;
            if (lastDamageEntity instanceof Player) {
                damager = (Player) lastDamageEntity;
            } else if (lastDamageEntity instanceof Arrow) {
                damager = (Player) ((Arrow) lastDamageEntity).getShooter();
            } else {
                return;
            }
            PlayerUtils.onPlayerKillAnother(player, damager);
        }

        //玩家输入/spawn confirm前没有受伤
        if (event == null) {
            PlayerUtils.onPlayerSuicide(player);
        }

    }
}
