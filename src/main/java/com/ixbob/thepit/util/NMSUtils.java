package com.ixbob.thepit.util;

import net.minecraft.server.v1_12_R1.EntityPlayer;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class NMSUtils {
    public static void sendNMSPacketToAllPlayers(Object packet) throws Exception {
        Method sendPacket = getNMSClass("PlayerConnection").getMethod("sendPacket", getNMSClass("Packet"));
        for (Player player : Bukkit.getOnlinePlayers()) {
            sendPacket.invoke(getPlayerConnection(player), packet);
        }
    }

    public static Object getPlayerConnection(Player player) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        Method getHandle = player.getClass().getMethod("getHandle");
        Object nmsPlayer = getHandle.invoke(player);
        Field conField = nmsPlayer.getClass().getField("playerConnection");
        return conField.get(nmsPlayer);
    }

    public static Class<?> getNMSClass(String clazz) throws Exception {
        return Class.forName("net.minecraft.server.v1_12_R1." + clazz);
    }

    public static EntityPlayer getEntityPlayer(Player player) {
        return ((CraftPlayer)player).getHandle();
    }
}
