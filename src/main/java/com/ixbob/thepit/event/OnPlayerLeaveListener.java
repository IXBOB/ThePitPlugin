package com.ixbob.thepit.event;

import com.ixbob.thepit.Main;
import com.ixbob.thepit.PlayerDataBlock;
import com.ixbob.thepit.TeamManager;
import com.ixbob.thepit.util.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Objects;

public class OnPlayerLeaveListener implements Listener {
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player leavePlayer = event.getPlayer();
        PlayerDataBlock playerDataBlock = Main.getPlayerDataBlock(leavePlayer);
        if (playerDataBlock.getBattleState()) {
            PlayerUtils.onPlayerEscapePunish(leavePlayer);
        }
        TeamManager.getInstance().leaveTeam(leavePlayer);
        PlayerUtils.storePlayerInventoryData(leavePlayer);

        Main.playerDataMap.remove(leavePlayer);

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (!Objects.equals(onlinePlayer, leavePlayer)) {
                Main.getPlayerDataBlock(onlinePlayer).removePlayerFromGetDamagedHistory(leavePlayer);
            }
        }
    }
}