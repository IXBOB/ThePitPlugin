package com.ixbob.thepit.task;

import com.ixbob.thepit.Main;
import com.ixbob.thepit.task.handler.RandomGoldIngotSpawnSingleTaskHandler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

public class RandomGoldIngotSpawnRunnable implements Runnable {
    @Override
    public void run() {
        RandomGoldIngotSpawnSingleTaskHandler handler = Main.getTaskManager().getRandomGoldIngotSpawnTaskHandler();
        int goldExistAmount = handler.getGoldIngotExistAmount();
        if (goldExistAmount >= 10) {
            handler.pause();
            return;
        }
        World world = Bukkit.getWorlds().get(0);
        double randomX = -20 + Math.random() * 20;
        double randomZ = -20 + Math.random() * 20;
        double randomY = world.getHighestBlockYAt((int) randomX, (int) randomZ);
        Location location = new Location(world, randomX, randomY, randomZ);
        world.dropItemNaturally(location, new ItemStack(Material.GOLD_INGOT));
        Main.getTaskManager().getRandomGoldIngotSpawnTaskHandler().setGoldIngotExistAmount(goldExistAmount + 1);
    }
}
