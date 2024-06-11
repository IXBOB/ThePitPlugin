package com.ixbob.thepit.event.thepit;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class PlayerOwnCoinModifiedEvent extends Event {
    public static final HandlerList HANDLERS = new HandlerList();
    private final Player player;
    private final double originCoin;
    private final double modifiedCoin;
    public final double newCoin;

    public PlayerOwnCoinModifiedEvent(Player player, double originCoin, double modifiedCoin) {
        this.player = player;
        this.originCoin = originCoin;
        this.modifiedCoin = modifiedCoin;
        this.newCoin = originCoin + modifiedCoin;
    }

    public Player getPlayer() {
        return player;
    }

    public double getOriginCoin() {
        return originCoin;
    }

    public double getModifiedCoin() {
        return modifiedCoin;
    }

    public double getNewCoin() {
        return newCoin;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }
}
