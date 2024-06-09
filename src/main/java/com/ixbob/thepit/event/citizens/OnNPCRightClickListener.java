package com.ixbob.thepit.event.citizens;

import com.ixbob.thepit.citizens.trait.CommandShopTrait;
import com.ixbob.thepit.citizens.trait.CommandTalentTrait;
import com.ixbob.thepit.citizens.trait.CommandWatchmanTrait;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OnNPCRightClickListener implements Listener {
    @EventHandler
    public void onNPCRightClick(NPCRightClickEvent event) {
        NPC npc = event.getNPC();
        Player player = event.getClicker();
        if (npc.hasTrait(CommandTalentTrait.class)) {
            player.performCommand("talent");
        }
        if (npc.hasTrait(CommandShopTrait.class)) {
            player.performCommand("shop");
        }
        if (npc.hasTrait(CommandWatchmanTrait.class)) {
            player.performCommand("watchman");
        }
    }
}
