package com.ixbob.thepit;

import com.ixbob.thepit.enums.ScoreboardStructureEnum;
import com.ixbob.thepit.util.NMSUtils;
import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.game.ClientboundSetDisplayObjectivePacket;
import net.minecraft.network.protocol.game.ClientboundSetObjectivePacket;
import net.minecraft.network.protocol.game.ClientboundSetScorePacket;
import net.minecraft.server.ServerScoreboard;
import net.minecraft.server.network.ServerPlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_18_R1.scoreboard.CraftScoreboard;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 更改单行时，仅对单行操作（删除原来+添加）
 * 插入或删除行时，对keys中的相关的第三项（值）批量+1或-1
 */

public class PlayerScoreboard {
    private final Player player;
    private final ServerPlayerConnection connection;
    private PlayerDataBlock dataBlock;
    private ArrayList<ArrayList<Object>> keys;
    private Scoreboard scoreboard;

    private Objective objective;
    private net.minecraft.world.scores.Objective worldObjective;
    private net.minecraft.world.scores.Scoreboard worldScoreboard;
    private static final String objectiveName = "display";
    private static final Component displayName = Component.text(LangLoader.getString("main_scoreboard_title"));

    public PlayerScoreboard(Player player) {
        this.player = player;
        this.connection = NMSUtils.getPlayerConnection(player);
        init();
        create();
    }

    private void init() {
        this.scoreboard = Bukkit.getServer().getScoreboardManager().getMainScoreboard();
        this.dataBlock = Main.getPlayerDataBlock(player);
        assert dataBlock != null;
        constructKeys();
    }

    public void create() {
        this.objective = scoreboard.getObjective(objectiveName);
        if (this.objective != null) {
            this.objective.unregister();
        }
        this.objective = scoreboard.registerNewObjective(objectiveName, "dummy", displayName);
        this.worldScoreboard = ((CraftScoreboard) scoreboard).getHandle();
        this.worldObjective = worldScoreboard.getObjective(objectiveName);

        connection.send(createObjectivePacket());
        connection.send(setObjectiveDisplayPacket());
        for (ArrayList<Object> contents : keys) {
            String key = (String) contents.get(1);
            int value = (int) contents.get(2);
            connection.send(setChangeScorePacket(key, value));
        }
    }

    public void delete() {
        connection.send(deleteObjectivePacket());
    }

    public void updateBoardLevel() {
        replaceBoardScoreNormally(ScoreboardStructureEnum.LEVEL, String.format(LangLoader.getString("main_scoreboard_line2"), dataBlock.getLevel()));
    }

    public void updateBoardNextLevelNeedXp() {
        replaceBoardScoreNormally(ScoreboardStructureEnum.NEXT_LEVEL_NEED_XP, String.format(LangLoader.getString("main_scoreboard_line3"), Mth.formatDecimalWithFloor(dataBlock.getNextLevelNeedXp(), 2)));
    }

    public void updateBoardCoinAmount() {
        replaceBoardScoreNormally(ScoreboardStructureEnum.COIN, String.format(LangLoader.getString("main_scoreboard_line5"), Mth.formatDecimalWithFloor(dataBlock.getCoinAmount(), 1)));
    }

    public void updateBoardBattleState() {
        boolean isBattling = dataBlock.getBattleState();
        if (isBattling) {
            replaceBoardScoreNormally(ScoreboardStructureEnum.BATTLE_STATE, String.format(LangLoader.getString("main_scoreboard_line7_battling"),
                    Mth.formatDecimalWithFloor(dataBlock.getBattleStateCoolCountDownerRunnable().getTimeLeft(), 1)));
        } else {
            replaceBoardScoreNormally(ScoreboardStructureEnum.BATTLE_STATE, String.format(LangLoader.getString("main_scoreboard_line7")));
        }
    }

    public void updateBoardTalentStrength() {
        boolean isInStrengthState = dataBlock.getTalentStrengthValidCountDownerRunnable() != null;
        boolean isBoardContain = queryContain(ScoreboardStructureEnum.TALENT_STRENGTH);
        if (!isBoardContain && isInStrengthState) {
            addKeyInKeys(ScoreboardStructureEnum.TALENT_STRENGTH);
            System.out.println("add");
        } else if (isBoardContain && !isInStrengthState) {
            removeKeyInKeys(ScoreboardStructureEnum.TALENT_STRENGTH);
            System.out.println("remove");
        } else if (isInStrengthState && isBoardContain) {
            replaceBoardScoreNormally(ScoreboardStructureEnum.TALENT_STRENGTH, String.format(LangLoader.getString("talent_item_id_4_scoreboard"),
                    dataBlock.getTalentStrengthValidCountDownerRunnable().getAddDamagePercentagePoint(),
                    Mth.formatDecimalWithFloor(dataBlock.getTalentStrengthValidCountDownerRunnable().getTimeLeft(), 1)));
        }
    }

    private void replaceBoardScoreNormally(ScoreboardStructureEnum structureEnum, String newKey) {
        ArrayList<?> levelScore = findInKeys(structureEnum).get(0);  //计分板只有一个记录LEVEL的，只找得到一个。因为findInKeys返回一个列表，所以还需要加上.get(0)
        String originKey = (String) levelScore.get(1);
        int originValue = (int) levelScore.get(2);
        connection.send(setRemoveScorePacket(originKey, originValue));
        setContentsInKeys(structureEnum, newKey, originValue);
        connection.send(setChangeScorePacket(newKey, originValue));
    }

    /**
     * 返回包含有若干List的List，包含的每个List就是一个匹配find条件(Enum)的对象
     */
    private ArrayList<ArrayList<Object>> findInKeys(ScoreboardStructureEnum structureEnum) {
        ArrayList<ArrayList<Object>> returnList = new ArrayList<>();
        for (ArrayList<Object> contents : keys) {
            if (contents.get(0) == structureEnum) {
                returnList.add(contents);
            }
        }
        return returnList;
    }

    /**
     * 还未考虑optional, 只会更改find结果的第一个
     */
    private void setContentsInKeys(ScoreboardStructureEnum structureEnum, String key, int value) {
        ArrayList<Object> find = findInKeys(structureEnum).get(0);
        find.set(1, key);
        find.set(2, value);
    }

    private ClientboundSetObjectivePacket createObjectivePacket() {
        // mode:  0：创建,  1：删除,  2：更新)
        return new ClientboundSetObjectivePacket(worldObjective, 0);
    }

    private ClientboundSetObjectivePacket deleteObjectivePacket() {
        // mode:  0：创建,  1：删除,  2：更新)
        return new ClientboundSetObjectivePacket(worldObjective, 1);
    }

    private ClientboundSetDisplayObjectivePacket setObjectiveDisplayPacket() {
        return new ClientboundSetDisplayObjectivePacket(1, worldObjective);
    }

    private ClientboundSetScorePacket setChangeScorePacket(String scoreContent, int value) {
        return new ClientboundSetScorePacket(ServerScoreboard.Method.CHANGE, objectiveName, scoreContent, value);
    }

    private ClientboundSetScorePacket setRemoveScorePacket(String scoreContent, int value) {
        return new ClientboundSetScorePacket(ServerScoreboard.Method.REMOVE, objectiveName, scoreContent, value);
    }

    /**
     * List
     *   - 其中一个(e.g. list[0])
     *     - ScoreboardStructureEnum
     *     - scoreContent
     *     - scoreValue
     */
    private void constructKeys() {
        if (keys != null) {
            keys.clear();
        }
        keys = new ArrayList<>();
        keys.add(new ArrayList<>(Arrays.asList(ScoreboardStructureEnum.EMPTY, LangLoader.getString("main_scoreboard_line1"), -1)));
        keys.add(new ArrayList<>(Arrays.asList(ScoreboardStructureEnum.LEVEL, String.format(LangLoader.getString("main_scoreboard_line2"), dataBlock.getLevel()), -2)));
        keys.add(new ArrayList<>(Arrays.asList(ScoreboardStructureEnum.NEXT_LEVEL_NEED_XP, String.format(LangLoader.getString("main_scoreboard_line3"), Mth.formatDecimalWithFloor(dataBlock.getNextLevelNeedXp() - dataBlock.getThisLevelOwnXp(), 2)), -3)));
        keys.add(new ArrayList<>(Arrays.asList(ScoreboardStructureEnum.EMPTY, LangLoader.getString("main_scoreboard_line4"), -4)));
        keys.add(new ArrayList<>(Arrays.asList(ScoreboardStructureEnum.COIN, String.format(LangLoader.getString("main_scoreboard_line5"), Mth.formatDecimalWithFloor(dataBlock.getCoinAmount(), 1)), -5)));
        keys.add(new ArrayList<>(Arrays.asList(ScoreboardStructureEnum.EMPTY, LangLoader.getString("main_scoreboard_line6"), -6)));
        keys.add(new ArrayList<>(Arrays.asList(ScoreboardStructureEnum.BATTLE_STATE, LangLoader.getString("main_scoreboard_line7"), -7)));
        keys.add(new ArrayList<>(Arrays.asList(ScoreboardStructureEnum.EMPTY, LangLoader.getString("main_scoreboard_line8"), -8)));
        keys.add(new ArrayList<>(Arrays.asList(ScoreboardStructureEnum.BOTTOM_INFO, LangLoader.getString("main_scoreboard_line9"), -9)));
    }

    private void addKeyInKeys(ScoreboardStructureEnum scoreboardStructureEnum) {
        ScoreboardStructureEnum addAfter = scoreboardStructureEnum.getPositionAfter();
        if (addAfter == null) {
            throw new IllegalArgumentException("This score cannot be added!");
        }
        int insertKeyValue = (int)findInKeys(addAfter).get(0).get(2) - 1;
        // 将分数值小于给定分数的分数 - 1 （将分数值小于插入的分数的分数值的分数的分数值 - 1，什么寄吧注释，不会写，自己理解，反正就这个大概意思）
        for (ArrayList<Object> key : findInKeys(scoreboardStructureEnum)) {
            if ((int) key.get(2) <= insertKeyValue) {
                key.set(2, (int)key.get(2) - 1);
            }
        }
        //插入分数
        keys.add(new ArrayList<>(Arrays.asList(ScoreboardStructureEnum.TALENT_STRENGTH, LangLoader.getString("talent_item_id_4_scoreboard"), insertKeyValue)));
        refreshBoard();
    }

    private void removeKeyInKeys(ScoreboardStructureEnum scoreboardStructureEnum) {
        int removedKeyValue = -114514;
        for (ArrayList<Object> key : findInKeys(scoreboardStructureEnum)) {
            if (key.get(0) == scoreboardStructureEnum) {
                removedKeyValue = (int) key.get(2);
                keys.remove(key);
            }
        }
        for (ArrayList<Object> key : findInKeys(scoreboardStructureEnum)) { // 将分数值小于给定分数的分数 - 1 （将分数值小于插入的分数的分数值的分数的分数值 - 1，什么寄吧注释，不会写，自己理解，反正就这个大概意思）
            if ((int) key.get(2) < removedKeyValue) {
                key.set(2, (int)key.get(2) + 1);
            }
        }
        refreshBoard();
    }

    public boolean queryContain(ScoreboardStructureEnum structureEnum) {
        return !findInKeys(structureEnum).isEmpty();
    }

    public void refreshBoard() {
        delete();
        create();
    }
}
