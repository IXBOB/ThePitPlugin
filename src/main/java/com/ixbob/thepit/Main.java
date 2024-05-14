package com.ixbob.thepit;

import com.ixbob.thepit.event.*;
import com.ixbob.thepit.handler.config.LangLoader;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class Main extends JavaPlugin {
    private static Plugin plugin;
    private static MongoDB mongoDB;
    public static Map<Player, PlayerDataBlock> playerDataMap = new HashMap<>();
    public static Location initialLocation;
    @Override
    public void onEnable() {
        Main.plugin = this;

        initialLocation = new Location(Bukkit.getWorlds().get(0), 6, 153, 5);

        MongoDB mongoDB = new MongoDB();
        mongoDB.connect("127.0.0.1", 27017, this);
        mongoDB.setCollection("ThePit_IXBOB");
        Main.mongoDB = mongoDB;

        LangLoader.init(this);

        Listener onPlayerJoinListener = new OnPlayerJoinListener();
        getServer().getPluginManager().registerEvents(onPlayerJoinListener, this);

        Listener onPlayerQuitListener = new OnPlayerLeaveListener();
        getServer().getPluginManager().registerEvents(onPlayerQuitListener, this);

        Listener onPlayerBeKilledListener = new OnPlayerBeKilledListener();
        getServer().getPluginManager().registerEvents(onPlayerBeKilledListener, this);

        Listener onPlayerOwnXpModifiedListener = new OnPlayerOwnXpModifiedListener();
        getServer().getPluginManager().registerEvents(onPlayerOwnXpModifiedListener, this);

        Listener onPlayerOwnCoinModifiedListener = new OnPlayerOwnCoinModifiedListener();
        getServer().getPluginManager().registerEvents(onPlayerOwnCoinModifiedListener, this);

        Listener onPlayerUpgradeLevelListener = new OnPlayerUpgradeLevelListener();
        getServer().getPluginManager().registerEvents(onPlayerUpgradeLevelListener, this);
    }

    public static Plugin getPlugin() {
        return plugin;
    }

    public static MongoDB getDB() {
        return mongoDB;
    }
}