package com.ixbob.thepit.task;

import com.ixbob.thepit.Main;
import com.ixbob.thepit.PlayerDataBlock;
import com.ixbob.thepit.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BattleStateCoolCountDowner implements Runnable{
    private int taskID;
    private float timeLeft;
    private Player player;
    private PlayerDataBlock dataBlock;

    public BattleStateCoolCountDowner(float timeLeft, Player player) {
        this.timeLeft = timeLeft;
        this.player = player;
        this.dataBlock = Main.getPlayerDataBlock(player);
    }

    @Override
    public void run() {
        timeLeft -= 0.05f;
        dataBlock.updateScoreboardBattleState();
        if (timeLeft < 0) {
            Utils.setBattleState(player, false);
            Bukkit.getScheduler().cancelTask(taskID);
        }
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public int getTaskID() {
        return taskID;
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

    public void setPlayer(Player player) {
        this.player = player;
    }

    public PlayerDataBlock getDataBlock() {
        return dataBlock;
    }

    public void setDataBlock(PlayerDataBlock dataBlock) {
        this.dataBlock = dataBlock;
    }
}
