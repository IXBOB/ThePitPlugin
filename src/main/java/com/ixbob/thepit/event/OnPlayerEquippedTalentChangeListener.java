package com.ixbob.thepit.event;

import com.ixbob.thepit.Main;
import com.ixbob.thepit.PlayerDataBlock;
import com.ixbob.thepit.enums.TalentItemsEnum;
import com.ixbob.thepit.event.custom.PlayerEquippedTalentChangeEvent;
import com.ixbob.thepit.util.TalentUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OnPlayerEquippedTalentChangeListener implements Listener {
    @EventHandler
    public void onPlayerEquippedTalentChange(PlayerEquippedTalentChangeEvent event) {
        boolean isEquipped = event.getIsEquipped();
        Player player = event.getPlayer();
        int equipToIndex = event.getEquipToIndex();
        TalentItemsEnum talentItem = event.getTalentItem();
        PlayerDataBlock dataBlock = Main.getPlayerDataBlock(player);
        updateDataBlock(dataBlock, TalentUtils.getEquipTalentIdByInventoryIndex(equipToIndex), talentItem.getId(), isEquipped);
        dataBlock.updatePlayerDBData();
    }

    private void updateDataBlock(PlayerDataBlock dataBlock, int equipGridId, int talentId, boolean isEquipped) {
        if (isEquipped) {
            dataBlock.setEquippedTalent(equipGridId, talentId);
        } else {
            dataBlock.removeEquippedTalent(equipGridId);
        }

    }
}