package com.ixbob.thepit.npc;

import com.ixbob.thepit.HologramManager;
import com.ixbob.thepit.LangLoader;
import com.ixbob.thepit.NPCHologramManager;
import com.ixbob.thepit.citizens.trait.CommandTalentTrait;
import com.ixbob.thepit.config.PitConfig;
import com.ixbob.thepit.enums.NPCNamesEnum;
import com.ixbob.thepit.gui.GUITalent;
import com.ixbob.thepit.util.Utils;
import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;

public class TalentNPC extends LobbyBasicNPCImpl {

    private static TalentNPC instance;

    private TalentNPC() {
        super(NPCNamesEnum.NPC_TALENT.getName(), PitConfig.getInstance().getNPCTalentLoc(), new CommandTalentTrait());

        Hologram hologram = DHAPI.createHologram(NPCNamesEnum.NPC_TALENT.getLockedHologramName(), PitConfig.getInstance().getNPCTalentLoc().add(0,2.7,0));
        hologram.setDefaultVisibleState(true);
        DHAPI.addHologramLine(hologram, String.format(LangLoader.getString("hd_npc_locked"), Utils.getLevelStrWithStyle(GUITalent.needPrestigeLevel, GUITalent.needLevel)));
        HologramManager.getInstance().add(hologram);
        NPCHologramManager.getInstance().addHologram(getNPCInstance(), hologram);
    }

    public static TalentNPC spawn() {
        if (instance == null) {
            instance = new TalentNPC();
            return instance;
        }
        throw new IllegalStateException("You cannot spawn more than one TalentNPC!");
    }

    public static TalentNPC getInstance() {
        return instance;
    }

}
