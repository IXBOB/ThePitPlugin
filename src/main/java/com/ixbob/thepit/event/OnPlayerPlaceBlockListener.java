package com.ixbob.thepit.event;

import com.ixbob.thepit.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class OnPlayerPlaceBlockListener implements Listener {
    @EventHandler
    public void onPlayerPlaceBlock(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Location location = event.getBlockPlaced().getLocation();
        if (event.getBlockPlaced().getType() == Material.OBSIDIAN) {
            Main.getTaskManager().getObsidianTaskHandler().newDefaultTask(200, location);
        }
    }
}
