package com.ixbob.thepit.event;

import com.ixbob.thepit.Main;
import com.ixbob.thepit.PlayerDataBlock;
import com.ixbob.thepit.event.thepit.PlayerTalentLevelChangeEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OnPlayerTalentLevelChangeListener implements Listener {
    @EventHandler
    public void onPlayerTalentLevelChange(PlayerTalentLevelChangeEvent event) {
        Player player = event.getPlayer();
        int id = event.getId();
        int newLevel = event.getChangeToLevel();
        PlayerDataBlock dataBlock = Main.getPlayerDataBlock(player);
        updateDataBlock(dataBlock, id, newLevel);
        dataBlock.updatePlayerDBData();
    }

    private void updateDataBlock(PlayerDataBlock dataBlock, int id, int newLevel) {
        dataBlock.setTalentLevel(id, newLevel);
    }
}
