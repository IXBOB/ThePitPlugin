package com.ixbob.thepit.task;

import com.ixbob.thepit.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

public class PlacedBlockRemoveRunnable implements Runnable{

    private int taskID;
    private final Location placedBlockLoc;

    public PlacedBlockRemoveRunnable(Location placedBlockLoc){
        this.placedBlockLoc = placedBlockLoc;
    }

    @Override
    public void run() {
        Bukkit.getWorlds().get(0).getBlockAt(placedBlockLoc).setType(Material.AIR);
        Main.getTaskManager().getPlacedBlockTaskHandler().remove(taskID);
    }

    public void setTaskID(int taskID){
        this.taskID = taskID;
    }
}
