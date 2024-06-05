package com.ixbob.thepit.npc;

import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.Trait;
import org.bukkit.Location;

public interface LobbyBasicNPC {

    NPC getNPCInstance();

    String getName();

    Location getLocation();

    Trait getTrait();
}
