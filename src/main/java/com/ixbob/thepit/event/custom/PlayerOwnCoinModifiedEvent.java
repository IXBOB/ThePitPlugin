package com.ixbob.thepit.event.custom;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerOwnCoinModifiedEvent extends Event {
    public static final HandlerList HANDLERS = new HandlerList();
    private final Player player;
    private final int originCoin;
    private final int modifiedCoin;
    public final int newCoin;

    public PlayerOwnCoinModifiedEvent(Player player, int originCoin, int modifiedCoin) {
        this.player = player;
        this.originCoin = originCoin;
        this.modifiedCoin = modifiedCoin;
        this.newCoin = originCoin + modifiedCoin;
    }

    public Player getPlayer() {
        return player;
    }

    public int getOriginCoin() {
        return originCoin;
    }

    public int getModifiedCoin() {
        return modifiedCoin;
    }

    public int getNewCoin() {
        return newCoin;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
}
