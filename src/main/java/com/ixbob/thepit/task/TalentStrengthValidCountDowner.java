package com.ixbob.thepit.task;

import com.ixbob.thepit.*;
import com.ixbob.thepit.enums.TalentItemsEnum;
import com.ixbob.thepit.util.TalentCalcuUtils;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

public class TalentStrengthValidCountDowner implements Runnable{

    private float timeLeft;
    private final Player player;
    private final PlayerDataBlock dataBlock;
    private final PlayerScoreboard playerScoreboard;
    private int taskID;
    private final int talentId = TalentItemsEnum.STRENGTH.getId();
    private float addDamagePercentagePoint;

    public TalentStrengthValidCountDowner(float timeLeft, Player player) {
        this.timeLeft = timeLeft;
        this.player = player;
        this.dataBlock = Main.getPlayerDataBlock(player);
        this.playerScoreboard = dataBlock.getPlayerScoreboard();
        addDamagePercentagePoint = TalentCalcuUtils.getAddPointValue(talentId, dataBlock.getTalentLevelList().get(talentId));
    }

    @Override
    public void run() {
        timeLeft -= 0.05f;
        if (timeLeft < 0) {
            playerScoreboard.removeKey(7);
            dataBlock.updateTalentStrengthState(false);
            cancel();
            return;
        }
        if (!player.isOnline()) {
            cancel();
            return;
        }
        dataBlock.updateScoreboardTalentStrength(false);
        //TODO: 没做完，下面的莫名其妙
        double originDamageValue = player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getBaseValue();
        player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(originDamageValue + originDamageValue * (addDamagePercentagePoint / 100));
        System.out.println(player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getBaseValue());
    }

    public void addStrengthLevel() {
        this.timeLeft = 7.0f;
        if (addDamagePercentagePoint < 39.9f) {
            this.addDamagePercentagePoint += TalentCalcuUtils.getAddPointValue(talentId, dataBlock.getTalentLevelList().get(talentId));
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
