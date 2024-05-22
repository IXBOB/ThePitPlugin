package com.ixbob.thepit.event;

import com.ixbob.thepit.Main;
import com.ixbob.thepit.PlayerDataBlock;
import com.ixbob.thepit.enums.TalentGivingItems;
import com.ixbob.thepit.enums.TalentItemsEnum;
import com.ixbob.thepit.event.custom.PlayerEquippedTalentChangeEvent;
import com.ixbob.thepit.util.TalentUtils;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OnPlayerEquippedTalentChangeListener implements Listener {
    private Player player;
    private int equipToIndex;
    private TalentItemsEnum talentItem;
    private boolean isEquipped;
    private PlayerDataBlock dataBlock;
    @EventHandler
    public void onPlayerEquippedTalentChange(PlayerEquippedTalentChangeEvent event) {
        isEquipped = event.getIsEquipped();
        player = event.getPlayer();
        equipToIndex = event.getEquipToIndex();
        talentItem = event.getTalentItem();
        dataBlock = Main.getPlayerDataBlock(player);

        updateDataBlock(dataBlock, TalentUtils.getEquipGridIdByInventoryIndex(equipToIndex), talentItem != null ? talentItem.getId() : null , isEquipped);
        dataBlock.updatePlayerDBData();
        applyEquipChangeResult();
    }

    private void updateDataBlock(PlayerDataBlock dataBlock, int equipGridId, Integer talentId, boolean isEquipped) {  //Integer类型可传入null
        if (isEquipped) {
            dataBlock.setEquippedTalent(equipGridId, talentId);
        } else {
            dataBlock.removeEquippedTalent(equipGridId);
        }
    }

    private void applyEquipChangeResult() {
        int id = talentItem.getId();
        int level = dataBlock.getTalentLevelList().get(id);
        switch (talentItem) {
            case HEALTH_BOOST:
                if (isEquipped) {
                    float maxHealth = 20 + talentItem.getAddPointValue(level);
                    player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth);
                    player.setHealth(maxHealth);
                } else {
                    player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
                    player.setHealth(20);
                } break;
            case GOLDEN_CHOCOLATE:
                if (isEquipped) {
                    System.out.println("equip!");
                } else {
                    System.out.println("Not Equip!");
                } break;
            case FISHERMAN:
                if (isEquipped) {
                    player.getInventory().addItem(TalentGivingItems.DEFAULT_FISHING_ROD.getItemStack());
                } else {
                    player.getInventory().remove(TalentGivingItems.DEFAULT_FISHING_ROD.getItemStack());
                } break;
        }
    }
}
