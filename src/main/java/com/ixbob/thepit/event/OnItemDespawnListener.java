package com.ixbob.thepit.event;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemDespawnEvent;

public class OnItemDespawnListener implements Listener {
    @EventHandler
    public void onItemDespawn(ItemDespawnEvent event) {
        Item item = event.getEntity();
        if (item.getItemStack().getType() == Material.GOLD_INGOT) {
            event.setCancelled(true);
        }
    }
}
