package com.ixbob.thepit.util;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerPlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_18_R1.CraftServer;
import org.bukkit.craftbukkit.v1_18_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer;
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

    public static ServerPlayerConnection getPlayerConnection(Player player) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        Method getHandle = player.getClass().getMethod("getHandle");
        Object nmsPlayer = getHandle.invoke(player);
        Field conField = nmsPlayer.getClass().getField("playerConnection");
        return (ServerPlayerConnection) conField.get(nmsPlayer);
    }

    public static Class<?> getNMSClass(String clazz) throws Exception {
        return Class.forName("net.minecraft.server.v1_18_R1." + clazz);
    }

    public static ServerPlayer getEntityPlayer(Player player) {
        return ((CraftPlayer)player).getHandle();
    }

    public static ServerPlayer getNewNMSPlayer(String name, String texture, String signature) throws Exception {
        MinecraftServer minecraftServer = ((CraftServer) Bukkit.getServer()).getServer();
        ServerLevel worldServer = ((CraftWorld) Bukkit.getWorlds().get(0)).getHandle();
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), "golden_chocolate");

        gameProfile.getProperties().put("textures", new Property("textures", texture, signature));

        ServerPlayer entityPlayer = new ServerPlayer(minecraftServer, worldServer, gameProfile);
        int entityID = (int)Math.ceil(Math.random() * 1000) + 2000;
        entityPlayer.setId(entityID);

        //启用双层皮肤
        SynchedEntityData dataWatcher = new SynchedEntityData(null);
//        https://wiki.vg/Entity_metadata#Player
        EntityDataAccessor<Byte> displayedPartsObject = new EntityDataAccessor<>(13, EntityDataSerializers.BYTE);
        byte displayedSkinParts = (byte) (0x01 | 0x02 | 0x04 | 0x08 | 0x10 | 0x20 | 0x40);
        dataWatcher.define(displayedPartsObject, displayedSkinParts);
        ClientboundSetEntityDataPacket packet = new ClientboundSetEntityDataPacket(entityID, dataWatcher, true);
        NMSUtils.sendNMSPacketToAllPlayers(packet);

        return entityPlayer;
    }

//R.I.P.
//    /**
//     * {@link com.ixbob.thepit.task.network.RegisterPlayerNamePacketAdapterRunnable}
//     */
//    public static void sendPacketForChangeDisplayName(Player player) {
//
//        ServerPlayer handle = ((CraftPlayer) player).getHandle();
//
//        //玩家加入时，其他玩家似乎不会接收到ADD_PLAYER包，这里模拟玩家发送ADD_PLAYER包
//        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
//            ServerPlayer onlinePlHandle = ((CraftPlayer) onlinePlayer).getHandle();
//            ServerPlayerConnection onlinePlConnection = onlinePlHandle.connection;
//
//            ClientboundPlayerInfoPacket handlerRemovePacket = new ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.REMOVE_PLAYER, handle);
//
//            ClientboundPlayerInfoPacket handlerAddPacket = new ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.ADD_PLAYER, handle);
//
//            onlinePlConnection.send(handlerRemovePacket);
//            onlinePlConnection.send(handlerAddPacket);
//
//            if (!onlinePlHandle.equals(handle)) {
//
//                ClientboundRemoveEntitiesPacket handlerDesPacket = new ClientboundRemoveEntitiesPacket(handle.getId());
//
//                ClientboundAddPlayerPacket packetPlayOutNamedEntitySpawn = new ClientboundAddPlayerPacket(handle);
//
//                onlinePlConnection.send(handlerDesPacket);
//                onlinePlConnection.send(packetPlayOutNamedEntitySpawn);
//            }
//        }
//    }


}
