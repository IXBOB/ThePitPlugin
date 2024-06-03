package com.ixbob.thepit.command;

import com.ixbob.thepit.LangLoader;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandHelp implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage(LangLoader.get("command_help_message_line1"));
        sender.sendMessage(LangLoader.get("command_help_message_line2"));
        sender.sendMessage(LangLoader.get("command_help_message_line3"));
        return true;
    }
}
