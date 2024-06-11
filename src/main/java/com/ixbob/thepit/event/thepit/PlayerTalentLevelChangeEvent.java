package com.ixbob.thepit.event.thepit;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerTalentLevelChangeEvent extends Event {
    public static final HandlerList HANDLERS = new HandlerList();

    private final Player player;
    private final int id;
    private final int changeToLevel;

    public PlayerTalentLevelChangeEvent(Player player, int id, int changeToLevel) {
        this.player = player;
        this.id = id;
        this.changeToLevel = changeToLevel;
    }

    public Player getPlayer() {
        return player;
    }

    public int getId() {
        return id;
    }

    public int getChangeToLevel() {
        return changeToLevel;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
}
