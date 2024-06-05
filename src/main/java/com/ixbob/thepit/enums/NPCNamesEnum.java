package com.ixbob.thepit.enums;

import com.ixbob.thepit.LangLoader;

public enum NPCNamesEnum {
    NPC_TALENT(LangLoader.getString("npc_talent_name"), "lock_1"),
    NPC_SHOP(LangLoader.getString("npc_shop_name"), null);

    private final String name;
    private final String lockedHologramName; //记录hologram的名字（不是显示的文字）

    NPCNamesEnum(String npcName, String lockedHologramName) {
        this.name = npcName;
        this.lockedHologramName = lockedHologramName;
    }

    public String getName() {
        return name;
    }

    public String getLockedHologramName() {
        return lockedHologramName;
    }
}
