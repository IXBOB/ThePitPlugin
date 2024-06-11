package com.ixbob.thepit.command;

import com.ixbob.thepit.Main;
import com.ixbob.thepit.PlayerGUIManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandShop implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player player) {
            PlayerGUIManager GUIManager = Main.getGUIManager();
            GUIManager.openShopGUI(player);
        }
        return true;
    }
}
