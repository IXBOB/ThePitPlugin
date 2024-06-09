package com.ixbob.thepit.citizens.trait;

public enum TraitNames {
    COMMAND_SHOP("CommandShop"),
    COMMAND_TALENT("CommandTalent"),
    COMMAND_WATCHMAN("CommandWatchman");

    private final String name;

    TraitNames(String traitName) {
        this.name = traitName;
    }

    public String getName() {
        return name;
    }
}
