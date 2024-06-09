package com.ixbob.thepit;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;

public class PlayerScoreboard {
    private final Player player;
    private ArrayList<String> keys = new ArrayList<>();
    private Scoreboard scoreboard;
    Objective boardObj;

    public PlayerScoreboard(Player player) {
        this.player = player;
        init();
        refreshAll();
    }

    private void init() {
        PlayerDataBlock playerData = Main.getPlayerDataBlock(player);
        keys.add(LangLoader.getString("main_scoreboard_line1"));
        keys.add(String.format(LangLoader.getString("main_scoreboard_line2"), playerData.getLevel()));
        keys.add(String.format(LangLoader.getString("main_scoreboard_line3"), Mth.formatDecimalWithFloor(playerData.getNextLevelNeedXp()-playerData.getThisLevelOwnXp(), 2)));
        keys.add(LangLoader.getString("main_scoreboard_line4"));
        keys.add(String.format(LangLoader.getString("main_scoreboard_line5"), Mth.formatDecimalWithFloor(playerData.getCoinAmount(), 1)));
        keys.add(LangLoader.getString("main_scoreboard_line6"));
        keys.add(LangLoader.getString("battle_state_false_scoreboard_line7"));
        keys.add(LangLoader.getString("main_scoreboard_line8"));
        keys.add(LangLoader.getString("main_scoreboard_line9"));

        this.scoreboard = player.getServer().getScoreboardManager().getMainScoreboard();
        this.boardObj = scoreboard.getObjective(playerData.getScoreboardObjName());
        if (this.boardObj == null) {
            this.boardObj = scoreboard.registerNewObjective(playerData.getScoreboardObjName(), "dummy");
        }
        boardObj.setDisplayName(ChatColor.YELLOW + "" + ChatColor.BOLD + LangLoader.getString("game_name"));
        boardObj.setDisplaySlot(DisplaySlot.SIDEBAR);
        player.setScoreboard(scoreboard); //setScoreboard(): 设置玩家可见的计分板
    }

    public void refreshAll() {
        for (String entry : scoreboard.getEntries()) { //删除所有
            Score score = boardObj.getScore(entry);
            boardObj.getScoreboard().resetScores(score.getEntry());
        }

        int index = 0; //添加所有
        for (String key : keys) {
            boardObj.getScore(key).setScore(index);
            index--;
        }
    }

    /**
     *
     * @param inputListIndex 填写keys list中的索引值，>= 0
     */
    public void refreshSpecific(int inputListIndex) {
        int inputBoardIndex = -inputListIndex;
        for (String entry : scoreboard.getEntries()) {
            Score score = boardObj.getScore(entry);
            int scoreInt = score.getScore();
            if (scoreInt == inputBoardIndex) {
                boardObj.getScoreboard().resetScores(score.getEntry());
            }
            boardObj.getScore(keys.get(inputListIndex)).setScore(inputBoardIndex);
        }
    }

    public void insertKey(int index, String key) {
        modifyEntryScoreArea(index, keys.size() - 1, -1);
        keys.add(index, key);
        refreshSpecific(index);
    }

    public void replaceKey(int index, String key) {
        keys.set(index, key);
        refreshSpecific(index);
    }

    public void removeKey(int index) {
        modifyEntryScoreArea(index + 1, keys.size() - 1, 1);
        keys.remove(index);
        refreshSpecific(index);
    }

    /**
     * 将选定区域的计分板数值增加或减少一个统一的值
     * 此操作会同时刷新计分板
     * @param fromIndex 正整数，较小，表示keys列表中的某一个索引值
     * @param toIndex 正整数，较大，表示keys列表中的某一个索引值
     * @param modifyAmount 整数，表示操作的值的大小
     */
    private void modifyEntryScoreArea(int fromIndex, int toIndex, int modifyAmount) {
        int boardIndexBigger = - fromIndex;
        int boardIndexSmaller = - toIndex;
        for (String entry : scoreboard.getEntries()) {
            Score score = boardObj.getScore(entry);
            int scoreInt = score.getScore();
            if (boardIndexSmaller <= scoreInt && scoreInt <= boardIndexBigger) {
                score.setScore(scoreInt + modifyAmount);
            }
        }
    }


}
