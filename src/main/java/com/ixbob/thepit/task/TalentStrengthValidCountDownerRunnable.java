package com.ixbob.thepit.task;

import com.ixbob.thepit.Main;
import com.ixbob.thepit.PlayerDataBlock;
import com.ixbob.thepit.PlayerScoreboard;
import com.ixbob.thepit.enums.gui.talent.GUITalentItemEnum;
import com.ixbob.thepit.util.TalentCalcuUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TalentStrengthValidCountDownerRunnable implements Runnable{

    private float timeLeft;
    private final Player player;
    private final PlayerDataBlock dataBlock;
    private final PlayerScoreboard playerScoreboard;
    private int taskID;
    private final int talentId = GUITalentItemEnum.STRENGTH.getId();
    private float addDamagePercentagePoint;

    public TalentStrengthValidCountDownerRunnable(float timeLeft, Player player) {
        this.timeLeft = timeLeft;
        this.player = player;
        this.dataBlock = Main.getPlayerDataBlock(player);
        this.playerScoreboard = dataBlock.getPlayerScoreboard();
        addDamagePercentagePoint = TalentCalcuUtils.getAddPointValue(talentId, dataBlock.getNormalTalentLevelList().get(talentId));
    }

    @Override
    public void run() {
        timeLeft -= 0.05f;
        if (timeLeft < 0) {
            dataBlock.updateTalentStrengthState(false);
            cancel();
        }
        else if (!player.isOnline()) {
            cancel();
            return;
        }
        playerScoreboard.updateBoardTalentStrength();
    }

    public void addStrengthLevel() {
        this.timeLeft = 7.0f;
        if (addDamagePercentagePoint < TalentCalcuUtils.getAddPointValue(talentId, dataBlock.getNormalTalentLevelList().get(talentId)) * 10 - 0.1) {
            this.addDamagePercentagePoint += TalentCalcuUtils.getAddPointValue(talentId, dataBlock.getNormalTalentLevelList().get(talentId));
        }
    }

    public float getAddDamagePercentagePoint() {
        return addDamagePercentagePoint;
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
}
