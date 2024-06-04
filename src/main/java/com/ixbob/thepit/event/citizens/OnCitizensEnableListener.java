package com.ixbob.thepit.event.citizens;

import com.ixbob.thepit.LangLoader;
import com.ixbob.thepit.citizens.trait.CommandShopTrait;
import com.ixbob.thepit.citizens.trait.CommandTalentTrait;
import com.ixbob.thepit.config.PitConfig;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.event.CitizensEnableEvent;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OnCitizensEnableListener implements Listener {
    @EventHandler
    public void onCitizensEnable (CitizensEnableEvent event) {
        NPCRegistry npcRegistry = CitizensAPI.getNPCRegistry();
        npcRegistry.deregisterAll();

        PitConfig config = PitConfig.getInstance();

        //生成NPC
        NPC talentNPC = npcRegistry.createNPC(EntityType.PLAYER, LangLoader.get("npc_talent_name"));
        talentNPC.spawn(config.getNPCTalentLoc());
        talentNPC.addTrait(new CommandTalentTrait());
        NPC shopNPC = npcRegistry.createNPC(EntityType.PLAYER, LangLoader.get("npc_shop_name"));
        shopNPC.spawn(config.getNPCShopLoc());
        shopNPC.addTrait(new CommandShopTrait());
    }
}
