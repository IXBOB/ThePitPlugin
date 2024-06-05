package com.ixbob.thepit.util;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_12_R1.CraftServer;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

public class NMSUtils {
    public static void sendNMSPacketToAllPlayers(Object packet) throws Exception {
        Method sendPacket = getNMSClass("PlayerConnection").getMethod("sendPacket", getNMSClass("Packet"));
        for (Player player : Bukkit.getOnlinePlayers()) {
            sendPacket.invoke(getPlayerConnection(player), packet);
        }
    }

    public static PlayerConnection getPlayerConnection(Player player) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        Method getHandle = player.getClass().getMethod("getHandle");
        Object nmsPlayer = getHandle.invoke(player);
        Field conField = nmsPlayer.getClass().getField("playerConnection");
        return (PlayerConnection) conField.get(nmsPlayer);
    }

    public static Class<?> getNMSClass(String clazz) throws Exception {
        return Class.forName("net.minecraft.server.v1_12_R1." + clazz);
    }

    public static EntityPlayer getEntityPlayer(Player player) {
        return ((CraftPlayer)player).getHandle();
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

//=================================================================================================
//曾经用于修改玩家显示名称的方法，配合PacketAdapter一起用。但是由于玩家名限制16个字符，无解决办法。 R.I.P.  立碑
//=================================================================================================

//    /**
//     * {@link com.ixbob.thepit.task.network.RegisterPlayerNamePacketAdapterRunnable}
//     */
//    public static void sendPacketForChangeDisplayName(Player player) {
//
//        EntityPlayer handle = ((CraftPlayer) player).getHandle();
//
//        //玩家加入时，其他玩家似乎不会接收到ADD_PLAYER包，这里模拟玩家发送ADD_PLAYER包
//        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
//            EntityPlayer onlinePlHandle = ((CraftPlayer) onlinePlayer).getHandle();
//            PlayerConnection onlinePlConnection = onlinePlHandle.playerConnection;
//
//            Packet<PacketListenerPlayOut> handlerRemovePacket = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, handle);
//
//            Packet<PacketListenerPlayOut> handlerAddPacket = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, handle);
//
//            onlinePlConnection.sendPacket(handlerRemovePacket);
//            onlinePlConnection.sendPacket(handlerAddPacket);
//
//            if (!onlinePlHandle.equals(handle)) {
//
//                PacketPlayOutEntityDestroy handlerDesPacket = new PacketPlayOutEntityDestroy(handle.getId());
//
//                PacketPlayOutNamedEntitySpawn packetPlayOutNamedEntitySpawn = new PacketPlayOutNamedEntitySpawn(handle);
//
//                onlinePlConnection.sendPacket(handlerDesPacket);
//                onlinePlConnection.sendPacket(packetPlayOutNamedEntitySpawn);
//            }
//        }
//    }
//

}
