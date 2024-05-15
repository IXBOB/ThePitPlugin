package com.ixbob.thepit;

import com.ixbob.thepit.handler.config.LangLoader;
import com.ixbob.thepit.task.BattleStateCoolCountDowner;
import com.mongodb.DBObject;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;

public class PlayerDataBlock {
    private final Player player;
    private int level;
    private int rank;
    private int thisLevelOwnXp;
    private int nextLevelNeedXp;
    private int coinAmount;
    private int prestigeLevel;
    private int prestigePointAmount;
    private String prefix;
    private int consecutiveKillAmount;
    private int killAmount;
    private int deathAmount;
    private boolean battleState;
    private ArrayList<Integer> TalentsLevelList;
    private ArrayList<?> equippedTalentList;
    private BattleStateCoolCountDowner battleStateCoolCountDowner;
    private boolean typedSpawn;

    private final Scoreboard scoreboard;
    private final Objective scoreboardObj;

    public PlayerDataBlock(Player player) {
        DBObject dataDBObj = Main.getDB().findByUUID(player.getUniqueId());
        this.player = player;
        this.level = (int) dataDBObj.get("level");
        this.rank = (int) dataDBObj.get("rank");
        this.thisLevelOwnXp = (int) dataDBObj.get("this_level_own_xp");
        this.nextLevelNeedXp = (int) dataDBObj.get("next_level_need_xp");
        this.coinAmount = (int) dataDBObj.get("coin_amount");
        this.prestigeLevel = (int) dataDBObj.get("prestige_level");
        this.prestigePointAmount = (int) dataDBObj.get("prestige_point_amount");
        this.prefix = (String) dataDBObj.get("prefix");
        this.consecutiveKillAmount = (int) dataDBObj.get("consecutive_kill_amount");
        this.killAmount = (int) dataDBObj.get("kill_amount");
        this.deathAmount = (int) dataDBObj.get("death_amount");
        this.TalentsLevelList = (ArrayList<Integer>) dataDBObj.get("TalentsLevelList");
        this.equippedTalentList = (ArrayList<?>) dataDBObj.get("EquippedTalentList");
        this.battleState = false;
        this.typedSpawn = false;

        this.scoreboard = player.getScoreboard();
        this.scoreboardObj = scoreboard.getObjective("main");
    }

    public void updatePlayerDBData() {
        MongoDB mongoDB = Main.getDB();
        DBObject dataObj = mongoDB.findByUUID(player.getUniqueId());
        dataObj.put("level", level);
        dataObj.put("rank", rank);
        dataObj.put("this_level_own_xp", thisLevelOwnXp);
        dataObj.put("next_level_need_xp", nextLevelNeedXp);
        dataObj.put("coin_amount", coinAmount);
        dataObj.put("prestige_level", prestigeLevel);
        dataObj.put("prestige_point_amount", prestigePointAmount);
        dataObj.put("prefix", prefix);
        dataObj.put("consecutive_kill_amount", consecutiveKillAmount);
        dataObj.put("kill_amount", killAmount);
        dataObj.put("death_amount", deathAmount);
        dataObj.put("TalentsLevelList", TalentsLevelList);
        dataObj.put("EquippedTalentList", equippedTalentList);
        mongoDB.updateDataByUUID(dataObj, player.getUniqueId());
    }

    public void updateScoreboardLevel() {
        for (String entry : scoreboard.getEntries()) {
            Score score = scoreboardObj.getScore(entry);
            int scoreInt = score.getScore();
            if (scoreInt == -1) {
                scoreboardObj.getScoreboard().resetScores(score.getEntry());
                scoreboardObj.getScore(String.format(LangLoader.get("main_scoreboard_line2"), level)).setScore(scoreInt);
            }
        }
    }

    public void updateScoreboardNextLevelNeedXp() {
        for (String entry : scoreboard.getEntries()) {
            Score score = scoreboardObj.getScore(entry);
            int scoreInt = score.getScore();
            if (scoreInt == -2) {
                scoreboardObj.getScoreboard().resetScores(score.getEntry());
                scoreboardObj.getScore(String.format(LangLoader.get("main_scoreboard_line3"), nextLevelNeedXp-thisLevelOwnXp)).setScore(scoreInt);
            }
        }
    }

    public void updateScoreboardOwnCoinAmount() {
        for (String entry : scoreboard.getEntries()) {
            Score score = scoreboardObj.getScore(entry);
            int scoreInt = score.getScore();
            if (scoreInt == -4) {
                scoreboardObj.getScoreboard().resetScores(score.getEntry());
                scoreboardObj.getScore(String.format(LangLoader.get("main_scoreboard_line5"), coinAmount)).setScore(scoreInt);
            }
        }
    }

    public void updateScoreboardBattleState() {
        for (String entry : scoreboard.getEntries()) {
            Score score = scoreboardObj.getScore(entry);
            int scoreInt = score.getScore();
            if (scoreInt == -6) {
                scoreboardObj.getScoreboard().resetScores(score.getEntry());
                if (battleState) {
                    scoreboardObj.getScore(String.format(LangLoader.get("battle_state_true_scoreboard_line7"), String.format("%.1f", battleStateCoolCountDowner.getTimeLeft()))).setScore(scoreInt);
                } else {
                    scoreboardObj.getScore(LangLoader.get("battle_state_false_scoreboard_line7")).setScore(scoreInt);
                }
            }
        }
    }

    public Player getPlayer() {
        return player;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getCoinAmount() {
        return coinAmount;
    }

    public void setCoinAmount(int coinAmount) {
        this.coinAmount = coinAmount;
    }

    public int getPrestigeLevel() {
        return prestigeLevel;
    }

    public void setPrestigeLevel(int prestigeLevel) {
        this.prestigeLevel = prestigeLevel;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public int getKillAmount() {
        return killAmount;
    }

    public void setKillAmount(int killAmount) {
        this.killAmount = killAmount;
    }

    public int getDeathAmount() {
        return deathAmount;
    }

    public void setDeathAmount(int deathAmount) {
        this.deathAmount = deathAmount;
    }

    public boolean getBattleState() {
        return battleState;
    }

    public void setBattleState(boolean battleState) {
        this.battleState = battleState;
        if (battleState) {
            if (battleStateCoolCountDowner == null) {
                BattleStateCoolCountDowner countDowner = new BattleStateCoolCountDowner(20.0f, player);
                int taskID = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), countDowner, 0, 1);
                countDowner.setTaskID(taskID);
                battleStateCoolCountDowner = countDowner;
            }
            else {
                battleStateCoolCountDowner.setTimeLeft(20.0f);
            }
        }
        else {
            battleStateCoolCountDowner = null;
        }
    }

    public int getNextLevelNeedXp() {
        return nextLevelNeedXp;
    }

    public void setNextLevelNeedXp(int nextLevelNeedXp) {
        this.nextLevelNeedXp = nextLevelNeedXp;
    }

    public int getThisLevelOwnXp() {
        return thisLevelOwnXp;
    }

    public void setThisLevelOwnXp(int thisLevelOwnXp) {
        this.thisLevelOwnXp = thisLevelOwnXp;
    }

    public int getConsecutiveKillAmount() {
        return consecutiveKillAmount;
    }

    public void setConsecutiveKillAmount(int consecutiveKillAmount) {
        this.consecutiveKillAmount = consecutiveKillAmount;
    }

    public boolean isBattleState() {
        return battleState;
    }

    public boolean isTypedSpawn() {
        return typedSpawn;
    }

    public void setTypedSpawn(boolean typedSpawn) {
        this.typedSpawn = typedSpawn;
    }

    public ArrayList<?> getEquippedTalentList() {
        return equippedTalentList;
    }

    public void setEquippedTalentList(ArrayList<?> equippedTalentList) {
        this.equippedTalentList = equippedTalentList;
    }

    public int getPrestigePointAmount() {
        return prestigePointAmount;
    }

    public void setPrestigePointAmount(int prestigePointAmount) {
        this.prestigePointAmount = prestigePointAmount;
    }

    public ArrayList<Integer> getTalentsLevelList() {
        return TalentsLevelList;
    }

    public void setTalentsLevelList(ArrayList<Integer> talentsLevelList) {
        TalentsLevelList = talentsLevelList;
    }
}
