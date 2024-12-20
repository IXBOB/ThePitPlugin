package com.ixbob.thepit.task;

import com.ixbob.thepit.Main;
import com.ixbob.thepit.PlayerDataBlock;
import com.ixbob.thepit.util.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BattleStateCoolCountDownerRunnable implements Runnable{
    private int taskID;
    private float timeLeft;
    private final Player player;
    private final PlayerDataBlock dataBlock;

    public BattleStateCoolCountDownerRunnable(float timeLeft, Player player) {
        this.timeLeft = timeLeft;
        this.player = player;
        this.dataBlock = Main.getPlayerDataBlock(player);
    }

    @Override
    public void run() {
        timeLeft -= 0.05f;
        if (timeLeft < 0) {
            PlayerUtils.setBattleState(player, false);
            cancel();
            return;
        }
        if (!player.isOnline()) {
            cancel();
            return;
        }
        dataBlock.getPlayerScoreboard().updateBoardBattleState();
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public int getTaskID() {
        return taskID;
    }

    public void cancel() {
        Bukkit.getScheduler().cancelTask(taskID);
    }

    public float getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(float timeLeft) {
        this.timeLeft = timeLeft;
    }

    public Player getPlayer() {
        return player;
    }

    public PlayerDataBlock getDataBlock() {
        return dataBlock;
    }
}
