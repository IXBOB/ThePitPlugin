package com.ixbob.thepit.event;

import com.ixbob.thepit.Main;
import com.ixbob.thepit.util.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class OnPlayerLeaveListener implements Listener {
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Main.playerDataMap.remove(player);
        Utils.storePlayerInventoryData(player);
    }
}