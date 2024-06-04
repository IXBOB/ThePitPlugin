package com.ixbob.thepit.event;

import com.ixbob.thepit.Main;
import com.ixbob.thepit.util.PlayerUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class OnPlayerLeaveListener implements Listener {
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (Main.getPlayerDataBlock(player).getBattleState()) {
            PlayerUtils.onPlayerEscapePunish(player);
        }
        PlayerUtils.storePlayerInventoryData(player);
        Main.playerDataMap.remove(player);
    }
}