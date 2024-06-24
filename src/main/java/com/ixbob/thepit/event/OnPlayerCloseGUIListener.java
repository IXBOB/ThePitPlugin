package com.ixbob.thepit.event;

import com.ixbob.thepit.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class OnPlayerCloseGUIListener implements Listener {
    //Paper 1.18.1 - 216 玩家退出时也会触发
    @EventHandler
    public void onPlayerCloseGUI(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        Main.getGUIManager().onCloseGUI(player);
    }
}
