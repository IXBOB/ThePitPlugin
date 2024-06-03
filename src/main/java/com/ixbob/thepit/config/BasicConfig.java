package com.ixbob.thepit.config;

import org.bukkit.Location;

public interface BasicConfig {
    Location getLobbyAreaFromLoc();

    Location getLobbyAreaToLoc();

    Location getNPCTalentLoc();

    Location getNPCShopLoc();
}
