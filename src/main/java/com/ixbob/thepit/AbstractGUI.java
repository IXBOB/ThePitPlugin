package com.ixbob.thepit;

import org.bukkit.entity.Player;

public abstract class AbstractGUI {
    private final Player player;
    private final int size;
    private final int[] button;
    private final int[] moveable;

    protected AbstractGUI(Player player, int size, int[] button, int[] moveable) {
        this.player = player;
        this.size = size;
        this.button = button;
        this.moveable = moveable;
    }

    public Player getPlayer() {
        return player;
    }

    public int getSize() {
        return size;
    }

    public int[] getButton() {
        return button;
    }

    public int[] getMoveable() {
        return moveable;
    }
}
