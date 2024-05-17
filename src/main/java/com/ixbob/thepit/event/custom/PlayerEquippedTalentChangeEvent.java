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
    private final boolean equipped;

    /**
     *
     * @param player
     * @param equipToIndex
     * @param talentItem 要删除已装备的talent物品，传入null，否则传入要装备talent物品枚举
     * @param equipped 要删除已装备的talent物品，传入false，否则true
     */
    public PlayerEquippedTalentChangeEvent(Player player, int equipToIndex, TalentItemsEnum talentItem, boolean equipped) {
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
