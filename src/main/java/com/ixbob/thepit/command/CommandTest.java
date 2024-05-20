package com.ixbob.thepit.command;

import com.ixbob.thepit.LangLoader;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class CommandTest implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        ItemStack itemStack = new ItemStack(Material.REDSTONE, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(LangLoader.get("talent_item_id_0_name"));
        ArrayList<String> lore = new ArrayList<>();
        lore.add(LangLoader.get("talent_item_id_0_lore1"));
        lore.add(LangLoader.get("talent_item_id_0_lore2"));
        lore.add(LangLoader.get("talent_item_id_0_lore3"));
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            player.getInventory().addItem(itemStack);
        }
        return true;
    }
}
