package com.ixbob.thepit;

import org.bukkit.entity.Player;

public abstract class AbstractGUI {
    private final Player player;
    private final int size;

    protected AbstractGUI(Player player, int size) {
        this.player = player;
        this.size = size;
    }

    public Player getPlayer() {
        return player;
    }

    public int getSize() {
        return size;
    }
}
