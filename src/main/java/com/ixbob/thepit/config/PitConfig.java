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
    private static Location npcTalentLoc;
    private static Location npcShopLoc;

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
        List<Double> lobbyAreaFromLocList = config.getDoubleList("lobby_area.from");
        lobbyAreaFromLoc = listToLoc(lobbyAreaFromLocList);
        List<Double> lobbyAreaToLocList = config.getDoubleList("lobby_area.to");
        lobbyAreaToLoc = listToLoc(lobbyAreaToLocList);
        List<Double> npcTalentLocation = config.getDoubleList("npc_talent_location");
        npcTalentLoc = listToLoc(npcTalentLocation);
        List<Double> npcShopLocation = config.getDoubleList("npc_shop_location");
        npcShopLoc = listToLoc(npcShopLocation);
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
    public Location getNPCTalentLoc() {
        return npcTalentLoc;
    }

    @Override
    public Location getNPCShopLoc() {
        return npcShopLoc;
    }

    private static Location listToLoc(List<?> list) {
        Location loc = new Location(Bukkit.getWorlds().get(0), (Double) list.get(0), (Double) list.get(1), (Double) list.get(2));
        if (list.size() == 5) {
            loc.setYaw(((Double) list.get(3)).floatValue());
            loc.setPitch(((Double) list.get(4)).floatValue());
        }
        return loc;
    }
}
