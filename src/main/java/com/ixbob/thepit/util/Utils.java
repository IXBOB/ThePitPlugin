package com.ixbob.thepit.util;

import com.ixbob.thepit.Main;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.v1_12_R1.CraftServer;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class Utils {

    public static ArrayList<String> readItemExtraData(ItemStack itemStack) {
        return ItemExtraDataReader.readFromItem(itemStack);
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

    public static ChatColor getChatColorByLevel(int level) {
        ChatColor color;
        if (level <= 10) {
            color = ChatColor.GRAY;
        } else if (level <= 25) {
            color = ChatColor.DARK_GREEN;
        } else if (level <= 40) {
            color = ChatColor.GREEN;
        } else if (level <= 60) {
            color = ChatColor.BLUE;
        } else if (level <= 80) {
            color = ChatColor.YELLOW;
        } else if (level <= 100) {
            color = ChatColor.GOLD;
        } else if (level <= 110) {
            color = ChatColor.RED;
        } else {
            color = ChatColor.DARK_RED;
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

    public static boolean isInLobbyArea(Location location) {
        return (Main.lobbyAreaFromPosList.get(0) <= location.getX() && location.getX() <= Main.lobbyAreaToPosList.get(0))
                && (Main.lobbyAreaFromPosList.get(1) <= location.getY() && location.getY() <= Main.lobbyAreaToPosList.get(1))
                && (Main.lobbyAreaFromPosList.get(2) <= location.getZ() && location.getZ() <= Main.lobbyAreaToPosList.get(2));
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
    }

    /**
     * 返回每个玩家总的伤害占比
     */
    public static HashMap<Player, Double> getDamagePercentMapSum(ArrayList<LinkedHashMap<Player, ArrayList<Object>>> damagedHistory) {
        final double[] allDamage = {0};
        HashMap<Player, Double> hashMap = new HashMap<>();
        //先将每个 player,总伤害 键值对存入hashMap
        for (HashMap<Player, ArrayList<Object>> history : damagedHistory) {
            history.forEach((damager, object) -> {
                allDamage[0] += (double) object.get(0);
                hashMap.merge(damager, (double) object.get(0), Double::sum);
            });
        }
        //计算hashMap每个值伤害所占比例
        for (Map.Entry<Player, Double> entry : hashMap.entrySet()) {
            entry.setValue(entry.getValue() / allDamage[0]);
        }
        return hashMap;
    }

    public static ArrayList<Player> getDamageHistoryPlayers(ArrayList<LinkedHashMap<Player, ArrayList<Object>>> damagedHistory) {
        ArrayList<Player> playerHistoryList = new ArrayList<>();
        for (LinkedHashMap<Player, ArrayList<Object>> history : damagedHistory) {
            Map.Entry<Player, ArrayList<Object>> firstEntry = history.entrySet().iterator().next();
            Player player = firstEntry.getKey();
            if (!playerHistoryList.contains(player)) {
                playerHistoryList.add(player);
            }
        }
        return playerHistoryList;
    }
}
