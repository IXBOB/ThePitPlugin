package com.ixbob.thepit.command;

import com.ixbob.thepit.enums.PitItemEnumHolder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandTest implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s,@NotNull String[] strings) {
        if (commandSender instanceof Player player) {
            player.getInventory().addItem(PitItemEnumHolder.MYTHICAL_RED_LEATHER_LEGGINGS.getItemStack());
        }

//        Bukkit.getScheduler().runTask(Main.getInstance(), new ReloadPitLobbyRankingsListRunnable());

//        Bukkit.getScheduler().runTask(Main.getInstance(), new ReloadPitLobbyRankingsListRunnable());
//        if (commandSender instanceof Player) {
//            Player player = (Player) commandSender;
//            CraftPlayer craftPlayer = (CraftPlayer) player;
//            ServerPlayer entityPlayer = craftPlayer.getHandle();
//            GameProfile gameProfile = entityPlayer.getGameProfile();
//
//            // 创建一个新的GameProfile，使用新的名字
//            GameProfile newProfile = new GameProfile(gameProfile.getId(), "123456");
//
//            // 更新玩家的GameProfile
//            try {
//                // 使用反射来设置新的GameProfile
//                java.lang.reflect.Field profileField = ServerPlayer.class.getDeclaredField("displayName");
//                profileField.setAccessible(true);
//                profileField.set(entityPlayer, newProfile);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            // 发送包来刷新玩家信息
//            for (Player onlinePl : Bukkit.getOnlinePlayers()) {
//                ServerPlayerConnection connection = ((CraftPlayer)onlinePl).getHandle().playerConnection;
//                connection.send(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, entityPlayer));
//                connection.send(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, entityPlayer));
//            }
//
//        }
//

        return true;
    }
}
