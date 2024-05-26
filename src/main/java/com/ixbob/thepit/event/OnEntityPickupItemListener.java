package com.ixbob.thepit.event;

import com.ixbob.thepit.enums.DropItemEnum;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;

public class OnEntityPickupItemListener implements Listener {
    @EventHandler
    public void onEntityPickupItem(EntityPickupItemEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            Item item = event.getItem();
            ItemStack itemStack = item.getItemStack();
            for (DropItemEnum dropItem : DropItemEnum.values()) {
                if (itemStack.getType() == dropItem.getItemStack().getType()) {
                    dropItem.apply(player);
                    item.remove();
                    event.setCancelled(true);
                    break;
                }
            }
        }
    }
}
