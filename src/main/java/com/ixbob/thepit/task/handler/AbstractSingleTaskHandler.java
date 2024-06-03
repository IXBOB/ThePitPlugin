package com.ixbob.thepit.task.handler;

import com.ixbob.thepit.Main;
import org.bukkit.Bukkit;

public abstract class AbstractSingleTaskHandler {
    private int taskID;
    private final Runnable runnable;
    private final int firstDelay;
    private final int periodDelay;
    private boolean isPaused = false;

    public AbstractSingleTaskHandler(Runnable runnable, int firstDelay, int periodDelay) {
        this.taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), runnable, firstDelay, periodDelay);
        this.runnable = runnable;
        this.firstDelay = firstDelay;
        this.periodDelay = periodDelay;
    }

    //Runnable里检测触发pause
    public void pause() {
        Bukkit.getScheduler().cancelTask(taskID);
        isPaused = true;
    }

    //其他地方的事件触发resume
    public void resume() {
        this.taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), runnable, firstDelay, periodDelay);
        isPaused = false;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public int getTaskID() {
        return taskID;
    }
}
