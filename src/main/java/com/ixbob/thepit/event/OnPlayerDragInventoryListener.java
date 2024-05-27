package com.ixbob.thepit.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;

public class OnPlayerDragInventoryListener implements Listener {
    @EventHandler
    public void onPlayerDragInventory(InventoryDragEvent event) {
        System.out.println("drag");
        Player player = (Player) event.getWhoClicked();
        if (player.getInventory().getType() == InventoryType.PLAYER) {
            if (event.getRawSlots().contains(45)) {
                event.setCancelled(true);
            }
        }
    }
}
