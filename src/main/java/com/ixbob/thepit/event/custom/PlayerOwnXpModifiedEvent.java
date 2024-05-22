package com.ixbob.thepit.event.custom;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerOwnXpModifiedEvent extends Event {
    public static final HandlerList HANDLERS = new HandlerList();
    private final Player player;
    private final double originXp;
    private final double modifiedXp;
    public final double newXp;

    public PlayerOwnXpModifiedEvent(Player player, double originXp, double modifiedXp) {
        this.player = player;
        this.originXp = originXp;
        this.modifiedXp = modifiedXp;
        this.newXp = originXp + modifiedXp;
    }

    public Player getPlayer() {
        return player;
    }

    public double getOriginXp() {
        return originXp;
    }

    public double getModifiedXp() {
        return modifiedXp;
    }

    public double getNewXp() {
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
