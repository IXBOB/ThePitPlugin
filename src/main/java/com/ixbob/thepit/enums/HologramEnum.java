package com.ixbob.thepit.enums;

public enum HologramEnum {
    LOCKED("locked"),
    TIP_INFO("tip_info"),
    TIP_JUMP("tip_jump"),
    LOBBY_RANKINGS("lobby_rankings");

    private final String name;

    HologramEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
