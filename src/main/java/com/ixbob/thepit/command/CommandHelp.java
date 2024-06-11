package com.ixbob.thepit.command;

import com.ixbob.thepit.LangLoader;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class CommandHelp implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        sender.sendMessage(LangLoader.getString("command_help_message_line1"));
        sender.sendMessage(LangLoader.getString("command_help_message_line2"));
        sender.sendMessage(LangLoader.getString("command_help_message_line3"));
        return true;
    }
}
