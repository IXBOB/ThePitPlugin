package com.ixbob.thepit;

import com.ixbob.thepit.task.BattleStateCoolCountDowner;
import com.ixbob.thepit.task.TalentStrengthValidCountDowner;
import com.mongodb.DBObject;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;

public class PlayerDataBlock {
    private final Player player;
    private int level;
    private int rank;
    private double thisLevelOwnXp;
    private double nextLevelNeedXp;
    private double coinAmount;
    private int prestigeLevel;
    private int prestigePointAmount;
    private String prefix;
    private int consecutiveKillAmount;
    private int killAmount;
    private int deathAmount;
    private boolean battleState;
    private boolean hasTalentStrength;
    private ArrayList<Integer> normalTalentLevelList;
    private ArrayList<Integer> equippedNormalTalentList;
    private BattleStateCoolCountDowner battleStateCoolCountDowner;
    private TalentStrengthValidCountDowner talentStrengthValidCountDowner;
    private PlayerScoreboard playerScoreboard;
    private boolean typedSpawn;
    private ArrayList<LinkedHashMap<Player, ArrayList<Object>>> playerGetDamagedHistory; //最里面使用的ArrayList便于后续添加更多受击信息
    private ArrayList<Player> damagedByArrowPlayers;

    public PlayerDataBlock(Player player) {
        this.player = player;
    }

    @SuppressWarnings("unchecked")
    public void init() {
        DBObject dataDBObj = Main.getDB().findByUUID(player.getUniqueId());
        this.level = (int) dataDBObj.get("level");
        this.rank = (int) dataDBObj.get("rank");
        this.thisLevelOwnXp = (double) dataDBObj.get("this_level_own_xp");
        this.nextLevelNeedXp = (double) dataDBObj.get("next_level_need_xp");
        this.coinAmount = (double) dataDBObj.get("coin_amount");
        this.prestigeLevel = (int) dataDBObj.get("prestige_level");
        this.prestigePointAmount = (int) dataDBObj.get("prestige_point_amount");
        this.prefix = (String) dataDBObj.get("prefix");
        this.consecutiveKillAmount = (int) dataDBObj.get("consecutive_kill_amount");
        this.killAmount = (int) dataDBObj.get("kill_amount");
        this.deathAmount = (int) dataDBObj.get("death_amount");
        this.normalTalentLevelList = (ArrayList<Integer>) dataDBObj.get("NormalTalentLevelList");
        this.equippedNormalTalentList = (ArrayList<Integer>) dataDBObj.get("EquippedNormalTalentList");
        this.playerScoreboard = new PlayerScoreboard(player);
        this.battleState = false;
        this.typedSpawn = false;
        this.playerGetDamagedHistory = new ArrayList<>();
        this.damagedByArrowPlayers = new ArrayList<>();
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
        dataObj.put("NormalTalentLevelList", normalTalentLevelList);
        dataObj.put("EquippedNormalTalentList", equippedNormalTalentList);
        mongoDB.updateDataByUUID(dataObj, player.getUniqueId());
    }

    public void updateScoreboardLevel() {
        playerScoreboard.replaceKey(1, String.format(LangLoader.get("main_scoreboard_line2"), level));
    }

    public void updateScoreboardNextLevelNeedXp() {
        playerScoreboard.replaceKey(2, String.format(LangLoader.get("main_scoreboard_line3"), Mth.formatDecimalWithFloor(nextLevelNeedXp-thisLevelOwnXp, 2)));
    }

    public void updateScoreboardOwnCoinAmount() {
        playerScoreboard.replaceKey(4, String.format(LangLoader.get("main_scoreboard_line5"), Mth.formatDecimalWithFloor(coinAmount, 1)));
    }

    public void updateScoreboardBattleState() {
        playerScoreboard.replaceKey(6, battleState ?
                String.format(LangLoader.get("battle_state_true_scoreboard_line7"), Mth.formatDecimalWithFloor(battleStateCoolCountDowner.getTimeLeft(), 1))
                :
                LangLoader.get("battle_state_false_scoreboard_line7"));
    }

    public void updateScoreboardTalentStrength(boolean isInit) {
        if (!isInit) {
            playerScoreboard.removeKey(7);
        }
        playerScoreboard.insertKey(7, String.format(LangLoader.get("talent_item_id_4_scoreboard"),
                talentStrengthValidCountDowner.getAddDamagePercentagePoint(),
                Mth.formatDecimalWithFloor(talentStrengthValidCountDowner.getTimeLeft(), 1)));
    }

    public void addDamagedHistory(Player damager, double finalDamage) {
        playerGetDamagedHistory.add(new LinkedHashMap<>() {{put(damager, new ArrayList<>(Arrays.asList(finalDamage)));}});
    }

    public ArrayList<LinkedHashMap<Player, ArrayList<Object>>> getPlayerGetDamagedHistory() {
        return playerGetDamagedHistory;
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

    public double getCoinAmount() {
        return coinAmount;
    }

    public void setCoinAmount(double coinAmount) {
        this.coinAmount = coinAmount;
    }

    public void setTalentLevel(int id, int newLevel) {
        this.normalTalentLevelList.set(id, newLevel);
    }

    public void setEquippedTalent(int equipGridId, int talentId) {
        this.equippedNormalTalentList.set(equipGridId, talentId);
    }

    public void removeEquippedTalent(int equipGridId) {
        this.equippedNormalTalentList.set(equipGridId, null);
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
                int taskID = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), countDowner, 0, 1);
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

    public void updateTalentStrengthState(boolean state) {
        if (state) {
            if (talentStrengthValidCountDowner == null) {
                TalentStrengthValidCountDowner countDowner = new TalentStrengthValidCountDowner(7.0f, player);
                int taskID = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), countDowner, 0, 1);
                countDowner.setTaskID(taskID);
                talentStrengthValidCountDowner = countDowner;
                updateScoreboardTalentStrength(true);
            }
            else {
                talentStrengthValidCountDowner.addStrengthLevel();
                updateScoreboardTalentStrength(false);
            }
        }
        else {
            talentStrengthValidCountDowner = null;
        }
    }



    public double getNextLevelNeedXp() {
        return nextLevelNeedXp;
    }

    public void setNextLevelNeedXp(double nextLevelNeedXp) {
        this.nextLevelNeedXp = nextLevelNeedXp;
    }

    public double getThisLevelOwnXp() {
        return thisLevelOwnXp;
    }

    public void setThisLevelOwnXp(double thisLevelOwnXp) {
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

    public ArrayList<?> getEquippedNormalTalentList() {
        return equippedNormalTalentList;
    }

    public void setEquippedNormalTalentList(ArrayList<Integer> equippedNormalTalentList) {
        this.equippedNormalTalentList = equippedNormalTalentList;
    }

    public int getPrestigePointAmount() {
        return prestigePointAmount;
    }

    public void setPrestigePointAmount(int prestigePointAmount) {
        this.prestigePointAmount = prestigePointAmount;
    }

    public ArrayList<Integer> getNormalTalentLevelList() {
        return normalTalentLevelList;
    }

    public void setNormalTalentLevelList(ArrayList<Integer> normalTalentLevelList) {
        this.normalTalentLevelList = normalTalentLevelList;
    }

    public PlayerScoreboard getPlayerScoreboard() {
        return playerScoreboard;
    }

    public void setPlayerScoreboard(PlayerScoreboard playerScoreboard) {
        this.playerScoreboard = playerScoreboard;
    }

    public TalentStrengthValidCountDowner getTalentStrengthValidCountDowner() {
        return talentStrengthValidCountDowner;
    }

    public ArrayList<Player> getDamagedByArrowPlayers() {
        return damagedByArrowPlayers;
    }
}
