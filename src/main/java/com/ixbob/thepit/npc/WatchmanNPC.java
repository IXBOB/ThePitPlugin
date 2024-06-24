package com.ixbob.thepit.npc;

import com.ixbob.thepit.citizens.trait.CommandWatchmanTrait;
import com.ixbob.thepit.config.PitConfig;
import com.ixbob.thepit.enums.NPCNamesEnum;

public class WatchmanNPC extends LobbyBasicNPCImpl{

    private static WatchmanNPC instance;

    private WatchmanNPC() {
        super(NPCNamesEnum.NPC_WATCHMAN.getName(), PitConfig.getInstance().getNPCWatchmanLoc(), new CommandWatchmanTrait());
    }

    public static WatchmanNPC spawn() {
        if (instance == null) {
            instance = new WatchmanNPC();
            return instance;
        }
        throw new IllegalStateException("You cannot spawn more than one WatchmanNPC!");
    }

    public static WatchmanNPC getInstance() {
        return instance;
    }

}
