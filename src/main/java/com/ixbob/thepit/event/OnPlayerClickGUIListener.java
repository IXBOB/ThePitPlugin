package com.ixbob.thepit.event;

import com.ixbob.thepit.AbstractGUI;
import com.ixbob.thepit.Main;
import com.ixbob.thepit.gui.GUITalent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

public class OnPlayerClickGUIListener implements Listener {
    @EventHandler
    public void onPlayerClickGUI(InventoryClickEvent event) {
        if (event.getRawSlot() == 45) {
            event.setCancelled(true);
            return;
        }
        Player player = (Player) event.getWhoClicked();
        AbstractGUI openingGUI = Main.getGUIManager().getOpeningGUI(player);
        if (openingGUI instanceof GUITalent) {
            int index = event.getSlot();
            ClickType clickType = event.getClick();
            if (!openingGUI.isMoveable(index, clickType)) {
                event.setCancelled(true);
            }
            openingGUI.onClick(index, clickType);
        }
    }
}
