package com.ixbob.thepit.event.citizens;

import com.ixbob.thepit.LangLoader;
import com.ixbob.thepit.Main;
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

import java.util.logging.Level;

public class OnCitizensEnableListener implements Listener {
    @EventHandler
    public void onCitizensEnable (CitizensEnableEvent event) {
        NPCRegistry npcRegistry = CitizensAPI.getNPCRegistry();
        npcRegistry.deregisterAll();

        PitConfig config = PitConfig.getInstance();

        //生成NPC
        NPC talentNPC = npcRegistry.createNPC(EntityType.PLAYER, LangLoader.getString("npc_talent_name"));
        talentNPC.spawn(config.getNPCTalentLoc());
        talentNPC.addTrait(new CommandTalentTrait());
        NPC shopNPC = npcRegistry.createNPC(EntityType.PLAYER, LangLoader.getString("npc_shop_name"));
        shopNPC.spawn(config.getNPCShopLoc());
        shopNPC.addTrait(new CommandShopTrait());

        Main.getInstance().getLogger().log(Level.INFO, LangLoader.getString("system_load_citizens_npcs_finish_info"));
    }
}
