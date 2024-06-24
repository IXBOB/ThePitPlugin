package com.ixbob.thepit.util;

import com.ixbob.thepit.Main;
import com.ixbob.thepit.config.PitConfig;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.text.SimpleDateFormat;
import java.util.*;

public class Utils {

    public static ArrayList<String> readItemExtraData(ItemStack itemStack) {
        return ItemExtraDataReader.readFromItem(itemStack);
    }

    public static String getLevelStrWithStyle(int prestigeLevel, int level) {
        ChatColor color = getChatColorByLevel(level);
        if (prestigeLevel == 0) {
            return ChatColor.RESET + "" + ChatColor.GRAY + "[" + color + level + ChatColor.RESET + "" + ChatColor.GRAY + "]";
        }
        else {
            return ChatColor.RESET + "" + ChatColor.GRAY + "[" + ChatColor.YELLOW + convertToRoman(prestigeLevel) + ChatColor.RESET+ "-" + color + level + ChatColor.GRAY + "]" + ChatColor.RESET + " ";
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
        Location lobbyAreaFromPos = PitConfig.getInstance().getLobbyAreaFromLoc();
        Location lobbyAreaToPos = PitConfig.getInstance().getLobbyAreaToLoc();
        return (lobbyAreaFromPos.getX() <= location.getX() && location.getX() <= lobbyAreaToPos.getX())
                && (lobbyAreaFromPos.getY() <= location.getY() && location.getY() <= lobbyAreaToPos.getY())
                && (lobbyAreaFromPos.getZ() <= location.getZ() && location.getZ() <= lobbyAreaToPos.getZ());
    }

    public static void backToLobby(Player player) {
        NMSUtils.getEntityPlayer(player).setAbsorptionAmount(0);
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
            Iterator<Map.Entry<Player, ArrayList<Object>>> iterator = history.entrySet().iterator();
            if (iterator.hasNext()) {
                Map.Entry<Player, ArrayList<Object>> firstEntry = iterator.next();
                Player player = firstEntry.getKey();
                if (!playerHistoryList.contains(player)) {
                    playerHistoryList.add(player);
                }
            }
        }
        return playerHistoryList;
    }

    public static String getFormattedNowTime() {
        Date now = new Date();
        TimeZone tz = TimeZone.getTimeZone("GMT+8");
        Calendar calendar = Calendar.getInstance(tz);
        calendar.setTime(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(tz);
        return sdf.format(calendar.getTime());
    }
}
