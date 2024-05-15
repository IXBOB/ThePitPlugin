package com.ixbob.thepit.event;

import com.ixbob.thepit.Main;
import com.ixbob.thepit.PlayerDataBlock;
import com.ixbob.thepit.event.custom.PlayerTypedSpawnChangeEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OnPlayerTypedSpawnChangeListener implements Listener {
    @EventHandler
    public void onPlayerTypedSpawnChange(PlayerTypedSpawnChangeEvent event) {
        Player player = event.getPlayer();
        boolean typedSpawn = event.getChangeToState();
        PlayerDataBlock dataBlock = Main.playerDataMap.get(player);
        dataBlock.setTypedSpawn(typedSpawn);
    }
}