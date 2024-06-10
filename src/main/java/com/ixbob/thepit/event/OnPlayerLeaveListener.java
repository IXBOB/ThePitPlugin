package com.ixbob.thepit.event;

import com.ixbob.thepit.Main;
import com.ixbob.thepit.PlayerDataBlock;
import com.ixbob.thepit.TeamManager;
import com.ixbob.thepit.util.PlayerUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

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

        Main.playerDataMap.remove(player);
    }
}