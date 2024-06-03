package com.ixbob.thepit.event.custom;

import com.ixbob.thepit.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerUpgradeLevelEvent extends Event {
    public static final HandlerList HANDLERS = new HandlerList();
    private final Player player;
    private final int originLevel;
    private final int newLevel;
    private final int prestigeLevel;

    public PlayerUpgradeLevelEvent(Player player, int originLevel, int newLevel) {
        this.player = player;
        this.originLevel = originLevel;
        this.newLevel = newLevel;
        this.prestigeLevel = Main.getPlayerDataBlock(player).getPrestigeLevel();
    }

    public int getPrestigeLevel() {
        return prestigeLevel;
    }

    public Player getPlayer() {
        return player;
    }

    public int getOriginLevel() {
        return originLevel;
    }

    public int getNewLevel() {
        return newLevel;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
}
