package com.ixbob.thepit.event;

import com.ixbob.thepit.Main;
import com.ixbob.thepit.PlayerDataBlock;
import com.ixbob.thepit.PlayerScoreboard;
import com.ixbob.thepit.event.thepit.PlayerOwnXpModifiedEvent;
import com.ixbob.thepit.event.thepit.PlayerUpgradeLevelEvent;
import com.ixbob.thepit.task.ReloadPitLobbyRankingsListRunnable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OnPlayerOwnXpModifiedListener implements Listener {
    @EventHandler
    public void onPlayerOwnXpModified(PlayerOwnXpModifiedEvent event) {
        double originXp;
        double modifiedXp;
        double newOwnXp;
        double nextLevelNeedXp;
        int originLevel;
        int upgradeLevel = 0;
        int newLevel;
        boolean levelUp = false;
        Player player = event.getPlayer();
        PlayerDataBlock dataBlock = Main.getPlayerDataBlock(player);
        PlayerScoreboard scoreboard = dataBlock.getPlayerScoreboard();
        originXp = event.getOriginXp();
        modifiedXp = event.getModifiedXp();
        newOwnXp = event.getNewXp();
        nextLevelNeedXp = dataBlock.getNextLevelNeedXp();
        originLevel = dataBlock.getLevel();
        while (newOwnXp >= nextLevelNeedXp) {
            upgradeLevel++;
            newOwnXp -= nextLevelNeedXp;
            nextLevelNeedXp = 2*((originLevel + upgradeLevel)+1) + (int)(Math.random()*2*((originLevel + upgradeLevel)+1));
            levelUp = true;
        }
        newLevel = originLevel + upgradeLevel;
        if (levelUp) {
            PlayerUpgradeLevelEvent upgradeLevelEvent = new PlayerUpgradeLevelEvent(player, originLevel, newLevel);
            Bukkit.getPluginManager().callEvent(upgradeLevelEvent);
        }

        updateDataBlock(dataBlock, newLevel, newOwnXp, nextLevelNeedXp, modifiedXp);
        dataBlock.updatePlayerDBData();
        scoreboard.updateBoardLevel();
        scoreboard.updateBoardNextLevelNeedXp();
        Bukkit.getScheduler().runTask(Main.getInstance(), new ReloadPitLobbyRankingsListRunnable());
    }
    private void updateDataBlock(PlayerDataBlock dataBlock, int newLevel, double newThisLevelOwnXp, double newNextLevelNeedXp, double modifiedXp) {
        dataBlock.setLevel(newLevel);
        dataBlock.setThisLevelOwnXp(newThisLevelOwnXp);
        dataBlock.setNextLevelNeedXp(newNextLevelNeedXp);
        dataBlock.setXpAmount(dataBlock.getXpAmount() + modifiedXp);
    }
}
