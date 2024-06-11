package com.ixbob.thepit;

import eu.decentsoftware.holograms.api.holograms.Hologram;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class NPCHologramManager {
    private static NPCHologramManager instance;
    private final HashMap<NPC, ArrayList<Hologram>> npcHologramsMap = new HashMap<>();

    private NPCHologramManager() {

    }

    public static NPCHologramManager getInstance() {
        if (instance == null) {
            instance = new NPCHologramManager();
        }
        return instance;
    }

    public void addHologram(NPC npc, Hologram hologram) {
        if (HologramManager.getInstance().isExist(hologram)) {
            npcHologramsMap.merge(npc, new ArrayList<>(Collections.singletonList(hologram)), (oldList, newList) -> {
                oldList.addAll(newList);
                return oldList;
            });
            return;
        }
        throw new IllegalStateException("Hologram not found in the HologramManager!");
    }

    public void removeHologram(NPC npc, Hologram hologram) {
        if (HologramManager.getInstance().isExist(hologram)) {
            npcHologramsMap.get(npc).remove(hologram);
            HologramManager.getInstance().remove(hologram);
        }
        throw new IllegalStateException("Hologram not found in the HologramManager!");
    }

    public void setNpcHologramsHideToPlayer(NPC npc, Player player) { //NPC拥有的Holograms默认全部显示
        for (Hologram hologram : npcHologramsMap.get(npc)) {
            if (!hologram.isDefaultVisibleState()) { //如果默认隐藏，则报错
                throw new IllegalCallerException("hologram must be default visible");
            }
            hologram.setHidePlayer(player);
        }
    }
}
