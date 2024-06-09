package com.ixbob.thepit.config;

import com.ixbob.thepit.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class PitConfig implements BasicConfig {
    public static PitConfig instance;
    private static Location lobbyAreaFromLoc;
    private static Location lobbyAreaToLoc;
    private static Location dhInfoTipLoc;
    private static Location dhJumpTipLoc;
    private static Location dhLobbyRankingsLoc;
    private static Location npcTalentLoc;
    private static Location npcShopLoc;
    private static Location npcWatchmanLoc;

    private PitConfig() {}

    public static PitConfig getInstance() {
        if (instance == null) {
            instance = new PitConfig();
            instance.init();
        }
        return instance;
    }

    public void init() {
        FileConfiguration config = Main.getInstance().getConfig();
        lobbyAreaFromLoc = listToLoc(config.getDoubleList("lobby_area.from"));
        lobbyAreaToLoc = listToLoc(config.getDoubleList("lobby_area.to"));
        dhInfoTipLoc = listToLoc(config.getDoubleList("dh_info_tip_location"));
        dhJumpTipLoc = listToLoc(config.getDoubleList("dh_jump_tip_location"));
        dhLobbyRankingsLoc = listToLoc(config.getDoubleList("dh_lobby_rankings_location"));
        npcTalentLoc = listToLoc(config.getDoubleList("npc_talent_location"));
        npcShopLoc = listToLoc(config.getDoubleList("npc_shop_location"));
        npcWatchmanLoc = listToLoc(config.getDoubleList("npc_watchman_location"));
    }


    @Override
    public Location getLobbyAreaFromLoc() {
        return lobbyAreaFromLoc;
    }

    @Override
    public Location getLobbyAreaToLoc() {
        return lobbyAreaToLoc;
    }

    @Override
    public Location getDhInfoTipLoc() {
        return dhInfoTipLoc;
    }

    @Override
    public Location getDhJumpTipLoc() {
        return dhJumpTipLoc;
    }

    @Override
    public Location getDhLobbyRankingsLoc() {
        return dhLobbyRankingsLoc;
    }

    @Override
    public Location getNPCTalentLoc() {
        return npcTalentLoc;
    }

    @Override
    public Location getNPCShopLoc() {
        return npcShopLoc;
    }

    @Override
    public Location getNPCWatchmanLoc() {
        return npcWatchmanLoc;    }

    private static Location listToLoc(List<?> list) {
        Location loc = new Location(Bukkit.getWorlds().get(0), (Double) list.get(0), (Double) list.get(1), (Double) list.get(2));
        if (list.size() == 5) {
            loc.setYaw(((Double) list.get(3)).floatValue());
            loc.setPitch(((Double) list.get(4)).floatValue());
        }
        return loc;
    }
}
