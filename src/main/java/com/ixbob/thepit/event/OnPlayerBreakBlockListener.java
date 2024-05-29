package com.ixbob.thepit.event;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class OnPlayerBreakBlockListener implements Listener {
    @EventHandler
    public void onPlayerBreakBlock(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block breakBlock = event.getBlock();
        if (!player.isOp()) {
            if (breakBlock.getType() == Material.OBSIDIAN
                    || breakBlock.getType() == Material.COBBLESTONE) {
                return;
            }
            event.setCancelled(true);
        }
    }
}
