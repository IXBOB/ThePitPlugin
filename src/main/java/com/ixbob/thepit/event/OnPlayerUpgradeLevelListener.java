package com.ixbob.thepit.event;

import com.ixbob.thepit.LangLoader;
import com.ixbob.thepit.Main;
import com.ixbob.thepit.PlayerDataBlock;
import com.ixbob.thepit.TeamManager;
import com.ixbob.thepit.event.thepit.PlayerUpgradeLevelEvent;
import com.ixbob.thepit.task.RefreshPlayerHologramVisibleStateRunnable;
import com.ixbob.thepit.util.Utils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.util.Ticks;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OnPlayerUpgradeLevelListener implements Listener {
    @EventHandler
    public void onPlayerUpgradeLevel(PlayerUpgradeLevelEvent event) {
        Player player = event.getPlayer();
        PlayerDataBlock dataBlock = Main.getPlayerDataBlock(player);
        int originLevel = event.getOriginLevel();
        int newLevel = event.getNewLevel();
        int prestigeLevel = event.getPrestigeLevel();

        String originLevelText = Utils.getLevelStrWithStyle(prestigeLevel, originLevel);
        String newLevelText = Utils.getLevelStrWithStyle(prestigeLevel, newLevel);
        Title title = Title.title(Component.text(LangLoader.getString("upgrade_title")),
                Component.text(String.format(LangLoader.getString("upgrade_subtitle"), originLevelText, newLevelText)),
                Title.Times.of(Ticks.duration(10L), Ticks.duration(50L), Ticks.duration(10L)));
        player.showTitle(title);
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
        TeamManager.getInstance().getTeam(player).prefix(Component.text(Utils.getLevelStrWithStyle(dataBlock.getPrestigeLevel(), newLevel)));
        Bukkit.getScheduler().runTask(Main.getInstance(), new RefreshPlayerHologramVisibleStateRunnable(player));
    }
}
