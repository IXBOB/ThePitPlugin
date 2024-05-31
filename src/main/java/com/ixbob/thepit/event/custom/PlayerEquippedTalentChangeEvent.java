package com.ixbob.thepit.event.custom;

import com.ixbob.thepit.enums.gui.talent.GUITalentItemEnum;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerEquippedTalentChangeEvent extends Event {
    public static final HandlerList HANDLERS = new HandlerList();

    private final Player player;
    private final int equipToIndex;
    private final GUITalentItemEnum talentItem;
    private final boolean equipped;

    public PlayerEquippedTalentChangeEvent(Player player, int equipToIndex, GUITalentItemEnum talentItem, boolean equipped) {
        this.player = player;
        this.equipToIndex = equipToIndex;
        this.talentItem = talentItem;
        this.equipped = equipped;
    }

    public boolean getIsEquipped() {
        return equipped;
    }

    public Player getPlayer() {
        return player;
    }

    public int getEquipToIndex() {
        return equipToIndex;
    }

    public GUITalentItemEnum getTalentItem() {
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
