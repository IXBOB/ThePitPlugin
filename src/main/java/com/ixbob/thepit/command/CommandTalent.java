package com.ixbob.thepit.command;

import com.ixbob.thepit.Main;
import com.ixbob.thepit.PlayerGUIManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandTalent implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            PlayerGUIManager GUIManager = Main.getGUIManager();
            GUIManager.openTalentGUI(player);
        }
        return true;
    }
}
