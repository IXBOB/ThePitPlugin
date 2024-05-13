package com.ixbob.thepit.event.custom;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerOwnXpModifiedEvent extends Event {
    public static final HandlerList HANDLERS = new HandlerList();
    private final Player player;
    private final int originXp;
    private final int modifiedXp;
    public final int newXp;

    public PlayerOwnXpModifiedEvent(Player player, int originXp, int modifiedXp) {
        this.player = player;
        this.originXp = originXp;
        this.modifiedXp = modifiedXp;
        this.newXp = originXp + modifiedXp;
    }

    public Player getPlayer() {
        return player;
    }

    public int getOriginXp() {
        return originXp;
    }

    public int getModifiedXp() {
        return modifiedXp;
    }

    public int getNewXp() {
        return newXp;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
}
