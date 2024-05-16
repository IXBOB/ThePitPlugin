package com.ixbob.thepit;

import com.ixbob.thepit.enums.GUIGridTypeEnum;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.ArrayList;

public abstract class AbstractGUI {
    private final Player player;
    private final int size;
    protected final ArrayList<Integer> rightButton;
    protected final ArrayList<Integer> leftMoveable;

    protected AbstractGUI(Player player, int size, ArrayList<Integer> rightButton, ArrayList<Integer> leftMoveable) {
        this.player = player;
        this.size = size;
        this.rightButton = rightButton;
        this.leftMoveable = leftMoveable;
    }

    public GUIGridTypeEnum getGridType(int index, ClickType clickType) {
        if (clickType.isLeftClick()) {
            if (leftMoveable.contains(index)) {
                return GUIGridTypeEnum.MOVEABLE;
            } else {
                return GUIGridTypeEnum.INVALID;
            }
        }
        if (clickType.isRightClick()) {
            if (rightButton.contains(index)) {
                return GUIGridTypeEnum.BUTTON;
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
