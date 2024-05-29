package com.ixbob.thepit.event;

import com.ixbob.thepit.LangLoader;
import com.ixbob.thepit.Main;
import com.ixbob.thepit.enums.DropItemEnum;
import com.ixbob.thepit.task.handler.RandomGoldIngotSpawnSingleTaskHandler;
import com.ixbob.thepit.util.Utils;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class OnPlayerPickupItemListener implements Listener {
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
                else if (itemStack.getType() == Material.GOLD_INGOT) {
                    RandomGoldIngotSpawnSingleTaskHandler taskHandler = Main.getTaskManager().getRandomGoldIngotSpawnTaskHandler();
                    int amount = itemStack.getAmount();
                    taskHandler.setGoldIngotExistAmount(taskHandler.getGoldIngotExistAmount() - amount);
                    if (taskHandler.isPaused()) {
                        taskHandler.resume();
                    }
                    int addPoint = new Random().ints(amount, 1, 5).reduce(1, Integer::sum);
                    pickupGoldIngot(player, addPoint);
                    item.remove();
                    event.setCancelled(true);
                    break;
                }
            }
        }
    }

    private void pickupGoldIngot(Player player, int addPoint) {
        Utils.addCoin(player, addPoint);
        player.sendMessage(String.format(LangLoader.get("player_pickup_coin_message"), addPoint));
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 1, 2);
    }
}
