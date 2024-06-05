package com.ixbob.thepit.event;

import com.ixbob.thepit.LangLoader;
import com.ixbob.thepit.Main;
import com.ixbob.thepit.event.custom.PlayerUpgradeLevelEvent;
import com.ixbob.thepit.task.RefreshPlayerHologramVisibleStateRunnable;
import com.ixbob.thepit.util.Utils;
import org.bukkit.Bukkit;
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
        int prestigeLevel = event.getPrestigeLevel();

        String originLevelText = Utils.getLevelStrWithStyle(prestigeLevel, originLevel);
        String newLevelText = Utils.getLevelStrWithStyle(prestigeLevel, newLevel);
        player.sendTitle(LangLoader.getString("upgrade_title"),
                String.format(LangLoader.getString("upgrade_subtitle"), originLevelText, newLevelText),
                10, 50, 10);
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
        Bukkit.getScheduler().runTask(Main.getInstance(), new RefreshPlayerHologramVisibleStateRunnable(player));
    }
}
