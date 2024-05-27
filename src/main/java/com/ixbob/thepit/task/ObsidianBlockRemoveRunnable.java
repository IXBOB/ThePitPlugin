package com.ixbob.thepit.task;

import com.ixbob.thepit.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

public class ObsidianBlockRemoveRunnable implements Runnable{

    private int taskID;
    private final Location obsidianLoc;

    public ObsidianBlockRemoveRunnable(Location obsidianLoc){
        this.obsidianLoc = obsidianLoc;
    }

    @Override
    public void run() {
        Bukkit.getWorlds().get(0).getBlockAt(obsidianLoc).setType(Material.AIR);
        Main.getTaskManager().getObsidianTaskHandler().remove(taskID);
    }

    public void instantRun() {
        Bukkit.getWorlds().get(0).getBlockAt(obsidianLoc).setType(Material.AIR);
    }

    public void setTaskID(int taskID){
        this.taskID = taskID;
    }
}
