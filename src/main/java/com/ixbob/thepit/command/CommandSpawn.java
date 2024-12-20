package com.ixbob.thepit.command;

import com.ixbob.thepit.LangLoader;
import com.ixbob.thepit.Main;
import com.ixbob.thepit.PlayerDataBlock;
import com.ixbob.thepit.util.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;

public class CommandSpawn implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,@NotNull String[] args) {
        if (sender instanceof Player player) {
            PlayerDataBlock dataBlock = Main.getPlayerDataBlock(player);
            boolean battleState = dataBlock.getBattleState();
            if (args.length == 0) {
                if (!battleState) {
                    back(player, false);
                } else {
                    dataBlock.setTypedSpawn(true);
                    player.sendMessage(LangLoader.getString("command_spawn_deny_in_battlestate"));
                }
            }
            if (args.length == 1) {
                if (args[0].equals("confirm")) {
                    if (dataBlock.isTypedSpawn()) {
                        PlayerUtils.onPlayerEscapePunish(player);
                    } else {
                        player.sendMessage(LangLoader.getString("command_spawn_deny_in_battlestate_confirm_failed"));
                    }
                }
            }
        } else {
            Bukkit.getLogger().log(Level.SEVERE, LangLoader.getString("command_error_not_on_player"));
        }
        return true;
    }

    private void back(Player player, boolean isClear) {
        Main.getPlayerDataBlock(player).getPlayerGetDamagedHistory().clear();
        player.teleport(Main.spawnLocation);
        PlayerUtils.setMostBasicKit(player, isClear);
        player.sendMessage(LangLoader.getString("command_spawn_success"));
        player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
        player.setFoodLevel(20);
    }
}
