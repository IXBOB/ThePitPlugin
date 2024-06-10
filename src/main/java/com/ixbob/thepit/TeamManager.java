package com.ixbob.thepit;

import com.ixbob.thepit.util.PlayerUtils;
import com.ixbob.thepit.util.Utils;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Objects;

public class TeamManager {

    private static TeamManager instance;

    private TeamManager() {
    }

    public static TeamManager getInstance() {
        if (instance == null) {
            instance = new TeamManager();
        }
        return instance;
    }

    public void joinTeamToChangeDisplayName(Player player) {
        PlayerDataBlock dataBlock = Main.getPlayerDataBlock(player);
        Scoreboard board = dataBlock.getPlayerScoreboard().getScoreboard();
        String teamName = String.valueOf(dataBlock.getId());  //玩家加入的team名字就是玩家的id
        Team team = board.registerNewTeam(teamName);
        PlayerDataBlock playerDataBlock = Main.getPlayerDataBlock(player);
        team.prefix(Component.text(Utils.getLevelStrWithStyle(playerDataBlock.getPrestigeLevel(), playerDataBlock.getLevel())));
        team.addEntry(player.getName());
        team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);
        //额外设置玩家列表内显示的名称，team自动更改似乎有问题
        player.setPlayerListName(PlayerUtils.getPitDisplayName(player));
    }

    public void leaveTeam(Player player) {
        PlayerDataBlock dataBlock = Main.getPlayerDataBlock(player);
        String joinedTeam = String.valueOf(dataBlock.getId());
        Scoreboard board = dataBlock.getPlayerScoreboard().getScoreboard();
        Objects.requireNonNull(board.getTeam(joinedTeam)).removeEntry(player.getName());
    }
}
