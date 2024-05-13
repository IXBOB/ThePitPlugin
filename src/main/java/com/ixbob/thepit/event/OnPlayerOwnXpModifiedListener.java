package com.ixbob.thepit.event;

import com.ixbob.thepit.Main;
import com.ixbob.thepit.PlayerDataBlock;
import com.ixbob.thepit.event.custom.PlayerOwnXpModifiedEvent;
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
        }
        newLevel = originLevel + upgradeLevel;

        updateDataBlock(dataBlock, newLevel, newOwnXp, nextLevelNeedXp);
        dataBlock.updatePlayerDBData();
        dataBlock.updatePlayerScoreboard();
    }
    private void updateDataBlock(PlayerDataBlock dataBlock, int newLevel, int newThisLevelOwnXp, int newNextLevelNeedXp) {
        dataBlock.setLevel(newLevel);
        dataBlock.setThisLevelOwnXp(newThisLevelOwnXp);
        dataBlock.setNextLevelNeedXp(newNextLevelNeedXp);
    }
}
