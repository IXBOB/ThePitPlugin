package com.ixbob.thepit.event;

import com.ixbob.thepit.Main;
import com.ixbob.thepit.PlayerDataBlock;
import com.ixbob.thepit.PlayerScoreboard;
import com.ixbob.thepit.event.thepit.PlayerOwnCoinModifiedEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OnPlayerOwnCoinModifiedListener implements Listener {
    @EventHandler
    public void onPlayerOwnCoinModified(PlayerOwnCoinModifiedEvent event) {
        Player player = event.getPlayer();
        double newCoin = event.getNewCoin();
        PlayerDataBlock dataBlock = Main.getPlayerDataBlock(player);
        PlayerScoreboard scoreboard = dataBlock.getPlayerScoreboard();
        updateDataBlock(dataBlock, newCoin);
        dataBlock.updatePlayerDBData();
        scoreboard.updateBoardCoinAmount();
    }

    private void updateDataBlock(PlayerDataBlock dataBlock, double newCoin) {
        dataBlock.setCoinAmount(newCoin);
    }
}
