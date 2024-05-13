package com.ixbob.thepit;

import org.bukkit.entity.Player;

public class PlayerDataBlock {
    private final Player player;
    private int level;
    private int rank;
    private int thisRankOwnXp;
    private int nextLevelNeedXp;
    private int coinAmount;
    private int prestige;
    private String prefix;
    private int killAmount;
    private int deathAmount;
    private boolean battleState;

    public PlayerDataBlock(Player player, int level, int rank, int thisRankOwnXp, int nextLevelNeedXp, int coinAmount, int prestige, String prefix, int killAmount, int deathAmount, boolean battleState) {
        this.player = player;
        this.level = level;
        this.rank = rank;
        this.thisRankOwnXp = thisRankOwnXp;
        this.nextLevelNeedXp = nextLevelNeedXp;
        this.coinAmount = coinAmount;
        this.prestige = prestige;
        this.prefix = prefix;
        this.killAmount = killAmount;
        this.deathAmount = deathAmount;
        this.battleState = battleState;
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

    public int getThisRankOwnXp() {
        return thisRankOwnXp;
    }

    public void setThisRankOwnXp(int thisRankOwnXp) {
        this.thisRankOwnXp = thisRankOwnXp;
    }
}
