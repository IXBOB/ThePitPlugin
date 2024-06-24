package com.ixbob.thepit.gui;

import com.ixbob.thepit.enums.gui.GUIGridTypeEnum;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;

public interface BasicGUI {
    void display(String title); //调用下面三个

    void initFrame(String title);

    void open();

    void initContent();


    void onClose();

    GUIGridTypeEnum getGridType(int index, ClickType clickType);

    boolean isMoveable(int index, ClickType clickType);

    void onClick(int index, ClickType clickType);

    Player getPlayer();

    Inventory getInventory();

    int getSize();

    int getNeedLevel();
}
