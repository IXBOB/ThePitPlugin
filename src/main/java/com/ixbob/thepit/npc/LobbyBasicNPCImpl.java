package com.ixbob.thepit.npc;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.citizensnpcs.api.trait.Trait;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

public abstract class LobbyBasicNPCImpl implements LobbyBasicNPC{
    private final NPCRegistry npcRegistry = CitizensAPI.getNPCRegistry();
    private final NPC npcInstance;
    private final String name;
    private final Location location;
    private final Trait trait;

    protected LobbyBasicNPCImpl(String name, Location location, Trait trait) {
        this.name = name;
        this.location = location;
        this.trait = trait;
        npcInstance = npcRegistry.createNPC(EntityType.PLAYER, name);
        npcInstance.addTrait(trait);
        npcInstance.spawn(location);
    }

    @Override
    public NPC getNPCInstance() {
        return npcInstance;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public Trait getTrait() {
        return trait;
    }
}
