package com.ixbob.thepit.util;

import com.ixbob.thepit.Main;
import com.ixbob.thepit.MongoDB;
import com.ixbob.thepit.PlayerDataBlock;
import com.ixbob.thepit.enums.CustomBasicTool;
import com.ixbob.thepit.enums.TalentItemsEnum;
import com.ixbob.thepit.event.custom.*;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mongodb.DBObject;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.v1_12_R1.CraftServer;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

public class Utils {
    private static final MongoDB mongoDB = Main.getDB();
    public static void storePlayerInventoryData(Player player) {
        UUID playerUUID = player.getUniqueId();
        DBObject dataDBObj = mongoDB.findByUUID(playerUUID);
        ArrayList<ArrayList<?>> hotBarItemList = new ArrayList<>(Collections.nCopies(9 ,null));
        ArrayList<ArrayList<?>> inventoryItemList = new ArrayList<>(Collections.nCopies(27 ,null));
        ArrayList<ArrayList<?>> armorItemList = new ArrayList<>(Collections.nCopies(4 ,null));
        for (int i = 0; i <= 8; i++) {
            ItemStack indexItem = player.getInventory().getItem(i);
            if (indexItem != null) {
                hotBarItemList.set(i, new ArrayList<>(Arrays.asList(indexItem.getType().name(), indexItem.getAmount(), readItemExtraData(indexItem))));
            }
        }
        for (int i = 0; i <= 26; i++) {
            ItemStack indexItem = player.getInventory().getItem(i+9);
            if (indexItem != null) {
                inventoryItemList.set(i, new ArrayList<>(Arrays.asList(indexItem.getType().name(), indexItem.getAmount(), readItemExtraData(indexItem))));
            }
        }
        for (int i = 0; i <= 3; i++) {
            ItemStack indexItem = player.getInventory().getArmorContents()[3 - i];  //默认i=0:脚，3-i:从头上往脚下读
            if (indexItem != null) {
                armorItemList.set(i, new ArrayList<>(Arrays.asList(indexItem.getType().name(), indexItem.getAmount(), readItemExtraData(indexItem))));
            }
        }

        dataDBObj.put("HotBarItemList", hotBarItemList);
        dataDBObj.put("InventoryItemList", inventoryItemList);
        dataDBObj.put("ArmorItemList", armorItemList);
        mongoDB.updateDataByUUID(dataDBObj, playerUUID);
    }

    public static ArrayList<String> readItemExtraData(ItemStack itemStack) {
        return ItemExtraDataReader.readFromItem(itemStack);
    }

    public static String getPitDisplayName(Player player) {
        PlayerDataBlock playerData = Main.getPlayerDataBlock(player);
        int rank = playerData.getRank();
        int level = playerData.getLevel();
        String prefix;
        ChatColor color = getChatColorByLevel(level);
        prefix = getLevelStrWithStyle(rank, level);
        return prefix + color + player.getName() + ChatColor.RESET;
    }

    public static String getLevelStrWithStyle(int rank, int level) {
        ChatColor color = getChatColorByLevel(level);
        if (rank == 0) {
            return ChatColor.RESET + "[" + color + level + ChatColor.RESET + "]";
        }
        else {
            return ChatColor.RESET + "[" + ChatColor.YELLOW + convertToRoman(rank) + ChatColor.RESET+ "-" + color + level + ChatColor.RESET +"]";
        }
    }

    private static ChatColor getChatColorByLevel(int level) {
        ChatColor color;
        if (level <= 10) {
            color = ChatColor.GRAY;
        } else if (level <= 25) {
            color = ChatColor.WHITE;
        } else if (level <= 40) {
            color = ChatColor.LIGHT_PURPLE;
        } else if (level <= 60) {
            color = ChatColor.BLUE;
        } else if (level <= 80) {
            color = ChatColor.GREEN;
        } else if (level <= 100) {
            color = ChatColor.YELLOW;
        } else if (level <= 110) {
            color = ChatColor.GOLD;
        } else {
            color = ChatColor.RED;
        }
        return color;
    }

    public static String convertToRoman(int number) {
        if (number <= 0 || number > 3999) {
            return "ERROR";
        }

        String[] romanSymbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};

        StringBuilder result = new StringBuilder();
        int i = 0;

        while (number > 0) {
            while (number >= values[i]) {
                number -= values[i];
                result.append(romanSymbols[i]);
            }
            i++;
        }

        return result.toString();
    }

    public static void addXp(Player player, double addXp) {
        PlayerDataBlock playerData = Main.getPlayerDataBlock(player);
        double originXp = playerData.getThisLevelOwnXp();

        PlayerOwnXpModifiedEvent modifyXpEvent = new PlayerOwnXpModifiedEvent(player, originXp, addXp);
        Bukkit.getPluginManager().callEvent(modifyXpEvent);
    }

    public static void addCoin(Player player, double addCoin) {
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

    public static void changeEquippedTalent(Player player, int index, TalentItemsEnum talentItem, boolean equipped) {
        PlayerEquippedTalentChangeEvent playerEquippedTalentChangeEvent = new PlayerEquippedTalentChangeEvent(player, index, talentItem, equipped);
        Bukkit.getPluginManager().callEvent(playerEquippedTalentChangeEvent);
    }

    public static void updateDisplayName(Player player) {
        String displayName = getPitDisplayName(player);
        player.setDisplayName(displayName);
        player.setPlayerListName(displayName);
    }

    public static boolean isInLobbyArea(Location location) {
        return (Main.lobbyAreaFromPosList.get(0) <= location.getX() && location.getX() <= Main.lobbyAreaToPosList.get(0))
                && (Main.lobbyAreaFromPosList.get(1) <= location.getY() && location.getY() <= Main.lobbyAreaToPosList.get(1))
                && (Main.lobbyAreaFromPosList.get(2) <= location.getZ() && location.getZ() <= Main.lobbyAreaToPosList.get(2));
    }

    public static void setMostBasicKit(Player player, boolean clear) {
        PlayerInventory inventory = player.getInventory();
        if (clear) {
            inventory.clear();
        }
        inventory.addItem(CustomBasicTool.BASIC_STONE_SWORD.getItemStack());
        inventory.addItem(CustomBasicTool.BASIC_BOW.getItemStack());
        inventory.addItem(new ItemStack(Material.ARROW, 8));
        inventory.addItem(new ItemStack(Material.COOKED_BEEF, 64));
        inventory.setChestplate(CustomBasicTool.BASIC_CHAINMAIL_CHESTPLATE.getItemStack());
        inventory.setLeggings(CustomBasicTool.BASIC_CHAINMAIL_LEGGINGS.getItemStack());
    }

    public static int getInventoryIndex(int row, int column) {
        return row * 9 - 1 - (9 - column);
    }

    public static EntityPlayer getNewNMSPlayer(String name, String texture, String signature) throws Exception {
        MinecraftServer minecraftServer = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer worldServer = ((CraftWorld) Bukkit.getWorlds().get(0)).getHandle();
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), "golden_chocolate");

        gameProfile.getProperties().put("textures", new Property("textures", texture, signature));

        EntityPlayer entityPlayer = new EntityPlayer(minecraftServer, worldServer, gameProfile, new PlayerInteractManager(minecraftServer.getWorld()));
        int entityID = (int)Math.ceil(Math.random() * 1000) + 2000;
        entityPlayer.h(entityID); //h: setID

        //启用双层皮肤
        DataWatcher dataWatcher = new DataWatcher(null);
//        https://wiki.vg/Entity_metadata#Player
        DataWatcherObject<Byte> displayedPartsObject = new DataWatcherObject<>(13, DataWatcherRegistry.a);
        byte displayedSkinParts = (byte) (0x01 | 0x02 | 0x04 | 0x08 | 0x10 | 0x20 | 0x40);
        dataWatcher.register(displayedPartsObject, displayedSkinParts);
        PacketPlayOutEntityMetadata packet = new PacketPlayOutEntityMetadata(entityID, dataWatcher, true);
        NMSUtils.sendNMSPacketToAllPlayers(packet);

        return entityPlayer;
    }

    public static void backToLobby(Player player) {
        NMSUtils.getEntityPlayer(player).setAbsorptionHearts(0);
        player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
        player.setFoodLevel(20);
        player.teleport(Main.spawnLocation);
        //恢复弓箭数
        Inventory inventory = player.getInventory();
        inventory.remove(Material.ARROW);
        inventory.addItem(new ItemStack(Material.ARROW, 8));
    }
}
