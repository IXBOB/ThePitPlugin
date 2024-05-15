package com.ixbob.thepit.event;

import com.ixbob.thepit.AbstractGUI;
import com.ixbob.thepit.Main;
import com.ixbob.thepit.gui.GUITalent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class OnPlayerClickGUIListener implements Listener {
    @EventHandler
    public void onPlayerClickGUI(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        AbstractGUI openingGUI = Main.getGUIManager().getOpeningGUI(player);
        if (openingGUI instanceof GUITalent) {
            System.out.println("in talent GUI");
        }
    }
}
