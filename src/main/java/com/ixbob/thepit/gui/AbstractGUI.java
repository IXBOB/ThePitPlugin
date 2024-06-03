package com.ixbob.thepit.gui;

import com.ixbob.thepit.LangLoader;
import com.ixbob.thepit.Main;
import com.ixbob.thepit.enums.gui.GUIGridTypeEnum;
import com.ixbob.thepit.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;

public abstract class AbstractGUI implements OpenableGUI {
    protected final Player player;
    protected final int size;
    private final int needLevel;
    private final int needPrestigeLevel;
    protected Inventory inventory;
    protected final ArrayList<Integer> leftButton;
    protected final ArrayList<Integer> rightButton;
    protected final ArrayList<Integer> leftMoveable;
    protected final ArrayList<Integer> rightMoveable;

    public AbstractGUI(Player player, int size, int needLevel, int needPrestigeLevel, ArrayList<Integer> leftButton, ArrayList<Integer> rightButton, ArrayList<Integer> leftMoveable, ArrayList<Integer> rightMoveable) {
        this.player = player;
        this.size = size;
        this.needLevel = needLevel;
        this.needPrestigeLevel = needPrestigeLevel;
        this.leftButton = leftButton;
        this.rightButton = rightButton;
        this.leftMoveable = leftMoveable;
        this.rightMoveable = rightMoveable;
    }

    public GUIGridTypeEnum getGridType(int index, ClickType clickType) {
        if (clickType.isLeftClick()) {
            if (leftMoveable.contains(index)) {
                return GUIGridTypeEnum.LEFT_MOVEABLE;
            } else if (leftButton.contains(index)) {
                return GUIGridTypeEnum.LEFT_BUTTON;
            } else {
                return GUIGridTypeEnum.INVALID;
            }
        }
        else if (clickType.isRightClick()) {
            if (rightMoveable.contains(index)) {
                return GUIGridTypeEnum.RIGHT_MOVEABLE;
            } else if (rightButton.contains(index)){
                return GUIGridTypeEnum.RIGHT_BUTTON;
            } else {
                return GUIGridTypeEnum.INVALID;
            }
        }
        return GUIGridTypeEnum.INVALID;
    }

    public boolean isMoveable(int index, ClickType clickType) {
        if (clickType.isLeftClick()) {
            return leftMoveable.contains(index);
        }
        return false;
    }

    public void onClick(int index, ClickType clickType) {}

    @Override
    public void display(String title) {
        int playerLevel = Main.getPlayerDataBlock(player).getLevel();
        int playerPrestigeLevel = Main.getPlayerDataBlock(player).getPrestigeLevel();
        if (playerPrestigeLevel < needPrestigeLevel || (playerPrestigeLevel == needPrestigeLevel && playerLevel < needLevel)) {
            player.sendMessage(String.format(LangLoader.get("gui_open_failed"), Utils.getLevelStrWithStyle(needPrestigeLevel, needLevel)));
            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 1, 1);
            Main.getGUIManager().onCloseGUI(player);
            return;
        }
        initFrame(title);
        open();
        initContent();
    }

    @Override
    public void initFrame(String title) {
        inventory = Bukkit.createInventory(player, size, title);
    }

    @Override
    public void open() {
        player.openInventory(inventory);
    }

    @Override
    public void initContent() {}

    public Player getPlayer() {
        return player;
    }

    public int getSize() {
        return size;
    }
}
