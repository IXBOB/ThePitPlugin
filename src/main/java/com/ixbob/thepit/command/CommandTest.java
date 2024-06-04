package com.ixbob.thepit.command;

import com.ixbob.thepit.Main;
import com.ixbob.thepit.task.ReloadPitLobbyRankingsListRunnable;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandTest implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Bukkit.getScheduler().runTask(Main.getInstance(), new ReloadPitLobbyRankingsListRunnable());
        return true;
    }
}
