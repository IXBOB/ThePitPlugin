package com.ixbob.thepit.config;

import org.bukkit.Location;

public interface BasicConfig {
    Location getLobbyAreaFromLoc();

    Location getLobbyAreaToLoc();

    Location getDhInfoTipLoc();

    Location getDhJumpTipLoc();

    Location getDhLobbyRankingsLoc();

    Location getNPCTalentLoc();

    Location getNPCShopLoc();
}
