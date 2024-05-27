package com.ixbob.thepit.task.handler;

import com.ixbob.thepit.Main;
import com.ixbob.thepit.task.ObsidianBlockRemoveRunnable;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class ObsidianTaskHandler extends AbstractTaskHandler {

    /**
     *
     * @param obsidianAliveTime 以tick为单位
     */
    public void newDefaultTask(int obsidianAliveTime, Location obsidianLocation) {
        ObsidianBlockRemoveRunnable runnable = new ObsidianBlockRemoveRunnable(obsidianLocation);
        int taskID = Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), runnable, obsidianAliveTime);
        runnable.setTaskID(taskID);
        add(taskID, runnable);
    }

}
