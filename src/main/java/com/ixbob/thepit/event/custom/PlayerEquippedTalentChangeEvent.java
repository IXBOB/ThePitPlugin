package com.ixbob.thepit.event.custom;

import com.ixbob.thepit.enums.TalentItemsEnum;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerEquippedTalentChangeEvent extends Event {
    public static final HandlerList HANDLERS = new HandlerList();

    private final Player player;
    private final int equipToIndex;
    private final TalentItemsEnum talentItem;

    public PlayerEquippedTalentChangeEvent(Player player, int equipToIndex, TalentItemsEnum talentItem) {
        this.player = player;
        this.equipToIndex = equipToIndex;
        this.talentItem = talentItem;
    }

    public Player getPlayer() {
        return player;
    }

    public int getEquipToIndex() {
        return equipToIndex;
    }

    public TalentItemsEnum getTalentItem() {
        return talentItem;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
}
