package com.ixbob.thepit.event;

import com.ixbob.thepit.Main;
import com.ixbob.thepit.PlayerDataBlock;
import com.ixbob.thepit.event.custom.PlayerBattleStateChangeEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OnPlayerBattleStateChangeListener implements Listener {
    @EventHandler
    public void onPlayerBattleStateChange(PlayerBattleStateChangeEvent event) {
        Player player = event.getPlayer();
        boolean battleState = event.getChangeToState();
        PlayerDataBlock dataBlock = Main.playerDataMap.get(player);
        dataBlock.setBattleState(battleState);
        dataBlock.updateScoreboardBattleState();
    }
}
