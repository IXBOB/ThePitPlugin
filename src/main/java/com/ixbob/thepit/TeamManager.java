package com.ixbob.thepit;

import com.ixbob.thepit.util.Utils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;

public class TeamManager {

    private static TeamManager instance;
    private final HashMap<Player, String> joinedTeamNameMap = new HashMap<>();

    private final ScoreboardManager manager = Bukkit.getScoreboardManager();
    private final Scoreboard board = manager.getMainScoreboard();

    private TeamManager() {
    }

    public static TeamManager getInstance() {
        if (instance == null) {
            instance = new TeamManager();
        }
        return instance;
    }

    public void joinRandomTeamToChangeDisplayName(Player player) {
        String randomTeamName = String.valueOf(((int)(Math.random() * 10000000)));
        Team team = board.getTeam(randomTeamName);
        if (team == null) {
            team = board.registerNewTeam(randomTeamName);
        }
        PlayerDataBlock playerDataBlock = Main.getPlayerDataBlock(player);
        joinedTeamNameMap.put(player, randomTeamName);
        team.prefix(Component.text(Utils.getLevelStrWithStyle(playerDataBlock.getPrestigeLevel(), playerDataBlock.getLevel())));
        team.addEntry(player.getName());
    }

    public void leaveTeam(Player player) {
        String joinedTeam = joinedTeamNameMap.get(player);
        if (joinedTeam != null) {
            board.getTeam(joinedTeam).removeEntry(player.getName());
            joinedTeamNameMap.remove(player);
            return;
        }
        throw new IllegalStateException("player haven't joined any team!");
    }
}
