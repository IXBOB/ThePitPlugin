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
        Player player = event.getPlayer();
        PlayerDataBlock playerDataBlock = Main.getPlayerDataBlock(player);
        if (playerDataBlock.getBattleState()) {
            PlayerUtils.onPlayerEscapePunish(player);
        }
        TeamManager.getInstance().leaveTeam(player);
        PlayerUtils.storePlayerInventoryData(player);
        Objects.requireNonNull(Bukkit.getServer().getScoreboardManager().getMainScoreboard().getObjective(playerDataBlock.getScoreboardObjName())).unregister();

        Main.playerDataMap.remove(player);
    }
}