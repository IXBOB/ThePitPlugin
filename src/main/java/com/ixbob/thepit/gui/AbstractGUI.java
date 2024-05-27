package com.ixbob.thepit.gui;

import com.ixbob.thepit.enums.GUIGridTypeEnum;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.ArrayList;

public abstract class AbstractGUI {
    private final Player player;
    private final int size;
    protected final ArrayList<Integer> leftButton;
    protected final ArrayList<Integer> rightButton;
    protected final ArrayList<Integer> leftMoveable;
    protected final ArrayList<Integer> rightMoveable;

    public AbstractGUI(Player player, int size, ArrayList<Integer> leftButton, ArrayList<Integer> rightButton, ArrayList<Integer> leftMoveable, ArrayList<Integer> rightMoveable) {
        this.player = player;
        this.size = size;
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

    public Player getPlayer() {
        return player;
    }

    public int getSize() {
        return size;
    }
}
