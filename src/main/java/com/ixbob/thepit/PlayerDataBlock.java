package com.ixbob.thepit;

import com.ixbob.thepit.handler.config.LangLoader;
import com.mongodb.DBObject;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class PlayerDataBlock {
    private final Player player;
    private int level;
    private int rank;
    private int thisLevelOwnXp;
    private int nextLevelNeedXp;
    private int coinAmount;
    private int prestige;
    private String prefix;
    private int consecutiveKillAmount;
    private int killAmount;
    private int deathAmount;
    private boolean battleState;

    public PlayerDataBlock(Player player, int level, int rank, int thisLevelOwnXp, int nextLevelNeedXp, int coinAmount, int prestige, String prefix, int consecutiveKillAmount, int killAmount, int deathAmount, boolean battleState) {
        this.player = player;
        this.level = level;
        this.rank = rank;
        this.thisLevelOwnXp = thisLevelOwnXp;
        this.nextLevelNeedXp = nextLevelNeedXp;
        this.coinAmount = coinAmount;
        this.prestige = prestige;
        this.prefix = prefix;
        this.consecutiveKillAmount = consecutiveKillAmount;
        this.killAmount = killAmount;
        this.deathAmount = deathAmount;
        this.battleState = battleState;
    }

    public void updatePlayerDBData() {
        MongoDB mongoDB = Main.getDB();
        DBObject dataObj = mongoDB.findByUUID(player.getUniqueId());
        dataObj.put("level", level);
        dataObj.put("rank", rank);
        dataObj.put("this_level_own_xp", thisLevelOwnXp);
        dataObj.put("next_level_need_xp", nextLevelNeedXp);
        dataObj.put("coin_amount", coinAmount);
        dataObj.put("prestige", prestige);
        dataObj.put("prefix", prefix);
        dataObj.put("consecutive_kill_amount", consecutiveKillAmount);
        dataObj.put("kill_amount", killAmount);
        dataObj.put("death_amount", deathAmount);
        mongoDB.updateDataByUUID(dataObj, player.getUniqueId());
    }

    public void updatePlayerScoreboard() {
        Scoreboard scoreboard = player.getScoreboard();
        Objective scoreboardObj = scoreboard.getObjective("main");
        for (String entry : scoreboard.getEntries()) {
            Score score = scoreboardObj.getScore(entry);
            switch (score.getScore()) {
                case -1: {
                    scoreboardObj.getScoreboard().resetScores(score.getEntry());
                    scoreboardObj.getScore(String.format(LangLoader.get("main_scoreboard_line2"), level)).setScore(-1);
                    break;
                }
                case -2: {
                    scoreboardObj.getScoreboard().resetScores(score.getEntry());
                    scoreboardObj.getScore(String.format(LangLoader.get("main_scoreboard_line3"), nextLevelNeedXp-thisLevelOwnXp)).setScore(-2);
                    break;
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

    public int getPrestige() {
        return prestige;
    }

    public void setPrestige(int prestige) {
        this.prestige = prestige;
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
}
