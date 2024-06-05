package com.ixbob.thepit.task;

import com.ixbob.thepit.Main;
import com.ixbob.thepit.NPCHologramManager;
import com.ixbob.thepit.npc.TalentNPC;
import org.bukkit.entity.Player;

public class RefreshPlayerHologramVisibleStateRunnable implements Runnable{

    private Player player;

    public RefreshPlayerHologramVisibleStateRunnable(Player player) {
        this.player = player;
    }

    @Override
    public void run() {
        int level = Main.getPlayerDataBlock(player).getLevel();
        NPCHologramManager npcHologramManager = NPCHologramManager.getInstance();
        if (level >= 10) {
            npcHologramManager.setNpcHologramsHideToPlayer(TalentNPC.getInstance().getNPCInstance(), player);
        }
    }
}
