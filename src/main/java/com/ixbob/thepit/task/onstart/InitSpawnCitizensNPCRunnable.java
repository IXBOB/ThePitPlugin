package com.ixbob.thepit.task.onstart;

import com.ixbob.thepit.HologramManager;
import com.ixbob.thepit.LangLoader;
import com.ixbob.thepit.Main;
import com.ixbob.thepit.config.PitConfig;
import com.ixbob.thepit.npc.ShopNPC;
import com.ixbob.thepit.npc.TalentNPC;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPCRegistry;

import java.util.logging.Level;

public class InitSpawnCitizensNPCRunnable implements Runnable{
    @Override
    public void run() {
        NPCRegistry npcRegistry = CitizensAPI.getNPCRegistry();
        npcRegistry.deregisterAll();

        PitConfig config = PitConfig.getInstance();

        //生成NPC
        ShopNPC shopNPC = ShopNPC.spawn();
        TalentNPC talentNPC = TalentNPC.spawn();

        //NPC配置完毕后设置HologramManager的初始化状态
        HologramManager.getInstance().setInitialized();
        Main.getInstance().getLogger().log(Level.INFO, LangLoader.getString("system_load_citizens_npcs_finish_info"));
    }
}
