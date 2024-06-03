package com.ixbob.thepit.task.handler;

import com.ixbob.thepit.Main;
import com.ixbob.thepit.task.PlacedBlockRemoveRunnable;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class PlacedBlockMultipleTaskHandler extends AbstractMultipleTaskHandler {

    /**
     *
     * @param placedBlockAliveTime 以tick为单位
     */
    public void newDefaultTask(int placedBlockAliveTime, Location placedBlockLocation) {
        PlacedBlockRemoveRunnable runnable = new PlacedBlockRemoveRunnable(placedBlockLocation);
        int taskID = Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), runnable, placedBlockAliveTime);
        runnable.setTaskID(taskID);
        add(taskID, runnable);
    }

}
