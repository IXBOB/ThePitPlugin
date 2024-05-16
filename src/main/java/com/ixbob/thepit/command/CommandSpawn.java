package com.ixbob.thepit.command;

import com.ixbob.thepit.Main;
import com.ixbob.thepit.PlayerDataBlock;
import com.ixbob.thepit.event.custom.PlayerBattleStateChangeEvent;
import com.ixbob.thepit.LangLoader;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public class CommandSpawn implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            PlayerDataBlock dataBlock = Main.getPlayerDataBlock(player);
            boolean battleState = dataBlock.getBattleState();
            if (args.length == 0) {
                if (!battleState) {
                    player.teleport(Main.spawnLocation);
                    player.sendMessage(LangLoader.get("command_spawn_success"));
                } else {
                    dataBlock.setTypedSpawn(true);
                    player.sendMessage(LangLoader.get("command_spawn_deny_in_battlestate"));
                }
            }
            if (args.length == 1) {
                if (args[0].equals("confirm")) {
                    if (dataBlock.isTypedSpawn()) {
                        player.teleport(Main.spawnLocation);
                        player.sendMessage(LangLoader.get("command_spawn_success"));
                        dataBlock.setTypedSpawn(false);
                        PlayerBattleStateChangeEvent damagerBattleStateChangeEvent = new PlayerBattleStateChangeEvent(player, false);
                        Bukkit.getPluginManager().callEvent(damagerBattleStateChangeEvent);
                    } else {
                        player.sendMessage(LangLoader.get("command_spawn_deny_in_battlestate_confirm_failed"));
                    }
                }
            }
        } else {
            Bukkit.getLogger().log(Level.SEVERE, LangLoader.get("command_error_not_on_player"));
        }
        return true;
    }
}
