package com.ixbob.thepit.event;

import com.ixbob.thepit.gui.AbstractGUI;
import com.ixbob.thepit.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

public class OnPlayerClickGUIListener implements Listener {
    @EventHandler
    public void onPlayerClickGUI(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        AbstractGUI openingGUI = Main.getGUIManager().getOpeningGUI(player);
        //处理自定义物品栏
        if (openingGUI != null) {
            int index = event.getSlot();
            ClickType clickType = event.getClick();
            if (!openingGUI.isMoveable(index, clickType)) {
                event.setCancelled(true);
            }
            openingGUI.onClick(index, clickType);
            return;
        }

        //处理其他物品栏
        if (player.getInventory().getType() == InventoryType.PLAYER) {
            if (event.getRawSlot() == 45) {  //有时会触发 InventoryDragEvent。 因为玩家按住鼠标的时间太长，并向服务器发送了跨一个插槽的拖动意图。 //https://www.spigotmc.org/threads/inventoryclickevent-sometimes-not-firing.540695/
                event.setCancelled(true);
            }
        }

    }
}
