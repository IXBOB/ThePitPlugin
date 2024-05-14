package com.ixbob.thepit.event;

import com.ixbob.thepit.Main;
import com.ixbob.thepit.PlayerDataBlock;
import com.ixbob.thepit.event.custom.PlayerOwnCoinModifiedEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OnPlayerOwnCoinModifiedListener implements Listener {
    @EventHandler
    public void onPlayerOwnCoinModified(PlayerOwnCoinModifiedEvent event) {
        Player player = event.getPlayer();
        int newCoin = event.getNewCoin();
        PlayerDataBlock dataBlock = Main.playerDataMap.get(player);
        updateDataBlock(dataBlock, newCoin);
        dataBlock.updatePlayerDBData();
        dataBlock.updateScoreboardOwnCoinAmount();
    }

    private void updateDataBlock(PlayerDataBlock dataBlock, int newCoin) {
        dataBlock.setCoinAmount(newCoin);
    }
}
