package com.ixbob.thepit.enums;

public enum HologramEnum {
    TIP_INFO("tip_info"),
    TIP_JUMP("tip_jump");

    private final String name;

    HologramEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
