package com.ixbob.thepit.gui;

import com.ixbob.thepit.AbstractGUI;
import com.ixbob.thepit.handler.config.LangLoader;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class GUITalent extends AbstractGUI {
    private static final int size = 54;

    public GUITalent(Player player) {
        super(player, size);
    }

    public void open(Player player) {
        Inventory inventory = Bukkit.createInventory(player, size, LangLoader.get("talent_gui_title"));
        player.openInventory(inventory);
    }
}
