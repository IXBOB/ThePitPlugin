package com.ixbob.thepit.event;

import com.ixbob.thepit.Main;
import com.ixbob.thepit.PlayerDataBlock;
import com.ixbob.thepit.event.custom.PlayerOwnXpModifiedEvent;
import com.ixbob.thepit.event.custom.PlayerUpgradeLevelEvent;
import com.ixbob.thepit.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OnPlayerOwnXpModifiedListener implements Listener {
    @EventHandler
    public void onPlayerOwnXpModified(PlayerOwnXpModifiedEvent event) {
        int originXp;
        int modifiedXp;
        int newOwnXp;
        int nextLevelNeedXp;
        int originLevel;
        int upgradeLevel = 0;
        int newLevel;
        boolean levelUp = false;
        Player player = event.getPlayer();
        PlayerDataBlock dataBlock = Main.playerDataMap.get(player);
        originXp = event.getOriginXp();
        modifiedXp = event.getModifiedXp();
        newOwnXp = event.getNewXp();
        nextLevelNeedXp = dataBlock.getNextLevelNeedXp();
        originLevel = dataBlock.getLevel();
        while (newOwnXp >= nextLevelNeedXp) {
            upgradeLevel++;
            newOwnXp -= nextLevelNeedXp;
            nextLevelNeedXp = 10*((originLevel + upgradeLevel)+1) + (int)(Math.random()*10*((originLevel + upgradeLevel)+1));
            levelUp = true;
        }
        newLevel = originLevel + upgradeLevel;
        if (levelUp) {
            Utils.updateDisplayName(player);
            PlayerUpgradeLevelEvent upgradeLevelEvent = new PlayerUpgradeLevelEvent(player, originLevel, newLevel);
            Bukkit.getPluginManager().callEvent(upgradeLevelEvent);
        }

        updateDataBlock(dataBlock, newLevel, newOwnXp, nextLevelNeedXp);
        dataBlock.updatePlayerDBData();
        dataBlock.updatePlayerScoreboard();
        Utils.updateDisplayName(player);
    }
    private void updateDataBlock(PlayerDataBlock dataBlock, int newLevel, int newThisLevelOwnXp, int newNextLevelNeedXp) {
        dataBlock.setLevel(newLevel);
        dataBlock.setThisLevelOwnXp(newThisLevelOwnXp);
        dataBlock.setNextLevelNeedXp(newNextLevelNeedXp);
    }
}
