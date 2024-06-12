package com.ixbob.thepit;

import com.ixbob.thepit.util.Utils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class TeamManager {

    private static TeamManager instance;
    private static final Scoreboard mainSc = Bukkit.getScoreboardManager().getMainScoreboard();

    private TeamManager() {
    }

    public static TeamManager getInstance() {
        if (instance == null) {
            instance = new TeamManager();
        }
        return instance;
    }

    public void joinTeamToChangeDisplayName(Player player) {
        Team queryTeam = mainSc.getTeam(getTeamName(player));
        if (queryTeam != null) {
            queryTeam.unregister();
        }
        Team team = mainSc.registerNewTeam(getTeamName(player));
        PlayerDataBlock playerDataBlock = Main.getPlayerDataBlock(player);
        team.prefix(Component.text(Utils.getLevelStrWithStyle(playerDataBlock.getPrestigeLevel(), playerDataBlock.getLevel())));
        team.addEntry(player.getName());
        team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);
    }

    public void leaveTeam(Player player) {
        PlayerDataBlock dataBlock = Main.getPlayerDataBlock(player);
        String joinedTeamName = String.valueOf(dataBlock.getId());
        Team team = mainSc.getTeam(joinedTeamName);
        assert team != null;
        team.unregister();
    }

    public Team getTeam(Player player) {
        Team queryTeam = mainSc.getTeam(getTeamName(player));
        if (queryTeam == null) {
            throw new NullPointerException("The player haven't joined any team");
        }
        return queryTeam;
    }

    private String getTeamName(Player player) {
        PlayerDataBlock dataBlock = Main.getPlayerDataBlock(player);
        return String.valueOf(dataBlock.getId());   //玩家加入的team名字就是玩家的id
    }
}
