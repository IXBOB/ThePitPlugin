package com.ixbob.thepit.event;

import com.ixbob.thepit.event.custom.PlayerUpgradeLevelEvent;
import com.ixbob.thepit.handler.config.LangLoader;
import com.ixbob.thepit.util.Utils;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OnPlayerUpgradeLevelListener implements Listener {
    @EventHandler
    public void onPlayerUpgradeLevel(PlayerUpgradeLevelEvent event) {
        Player player = event.getPlayer();
        int originLevel = event.getOriginLevel();
        int newLevel = event.getNewLevel();
        int rank = event.getRank();

        String originLevelText = Utils.getLevelStrWithStyle(rank, originLevel);
        String newLevelText = Utils.getLevelStrWithStyle(rank, newLevel);
        player.sendTitle(LangLoader.get("upgrade_title"),
                String.format(LangLoader.get("upgrade_subtitle"), originLevelText, newLevelText),
                10, 50, 10);
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
    }
}
