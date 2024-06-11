package com.ixbob.thepit.event.thepit;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerBattleStateChangeEvent extends Event {
    public static final HandlerList HANDLERS = new HandlerList();
    private final Player player;
    private final boolean changeToState;

    public PlayerBattleStateChangeEvent(Player player, boolean changeToState) {
        this.player = player;
        this.changeToState = changeToState;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean getChangeToState() {
        return changeToState;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
}
