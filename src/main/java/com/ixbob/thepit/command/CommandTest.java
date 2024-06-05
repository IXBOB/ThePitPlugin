package com.ixbob.thepit.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandTest implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
//        Bukkit.getScheduler().runTask(Main.getInstance(), new ReloadPitLobbyRankingsListRunnable());
//        if (commandSender instanceof Player) {
//            Player player = (Player) commandSender;
//            CraftPlayer craftPlayer = (CraftPlayer) player;
//            EntityPlayer entityPlayer = craftPlayer.getHandle();
//            GameProfile gameProfile = entityPlayer.getProfile();
//
//            // 创建一个新的GameProfile，使用新的名字
//            GameProfile newProfile = new GameProfile(gameProfile.getId(), "123456");
//
//            // 更新玩家的GameProfile
//            try {
//                // 使用反射来设置新的GameProfile
//                java.lang.reflect.Field profileField = EntityPlayer.class.getDeclaredField("displayName");
//                profileField.setAccessible(true);
//                profileField.set(entityPlayer, newProfile);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            // 发送包来刷新玩家信息
//            for (Player onlinePl : Bukkit.getOnlinePlayers()) {
//                PlayerConnection connection = ((CraftPlayer)onlinePl).getHandle().playerConnection;
//                connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, entityPlayer));
//                connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, entityPlayer));
//            }
//
//        }
//

        return true;
    }
}
