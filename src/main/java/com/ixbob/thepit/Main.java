package com.ixbob.thepit;

import com.ixbob.thepit.event.*;
import com.ixbob.thepit.handler.config.LangLoader;
import com.ixbob.thepit.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main extends JavaPlugin {
    private static Plugin plugin;
    private static MongoDB mongoDB;
    private final FileConfiguration config = getConfig();
    public static Map<Player, PlayerDataBlock> playerDataMap = new HashMap<>();
    public static Location spawnLocation;
    public static List<Integer> lobbyAreaFromPosList;
    public static List<Integer> lobbyAreaToPosList;
    @Override
    public void onEnable() {
        Main.plugin = this;

        spawnLocation = new Location(Bukkit.getWorlds().get(0), 6, 153, 5);

        MongoDB mongoDB = new MongoDB();
        mongoDB.connect("127.0.0.1", 27017, this);
        mongoDB.setCollection("ThePit_IXBOB");
        Main.mongoDB = mongoDB;

        LangLoader.init(this);

        this.saveDefaultConfig();
        lobbyAreaFromPosList = config.getIntegerList("lobby_area.from");
        lobbyAreaToPosList = config.getIntegerList("lobby_area.to");

        Listener onPlayerJoinListener = new OnPlayerJoinListener();
        getServer().getPluginManager().registerEvents(onPlayerJoinListener, this);

        Listener onPlayerQuitListener = new OnPlayerLeaveListener();
        getServer().getPluginManager().registerEvents(onPlayerQuitListener, this);

        Listener onEntityDamageEntityListener = new OnEntityDamageEntityListener();
        getServer().getPluginManager().registerEvents(onEntityDamageEntityListener, this);

        Listener onPlayerOwnXpModifiedListener = new OnPlayerOwnXpModifiedListener();
        getServer().getPluginManager().registerEvents(onPlayerOwnXpModifiedListener, this);

        Listener onPlayerOwnCoinModifiedListener = new OnPlayerOwnCoinModifiedListener();
        getServer().getPluginManager().registerEvents(onPlayerOwnCoinModifiedListener, this);

        Listener onPlayerUpgradeLevelListener = new OnPlayerUpgradeLevelListener();
        getServer().getPluginManager().registerEvents(onPlayerUpgradeLevelListener, this);

        Listener onEntityDamagedListener = new OnEntityDamagedListener();
        getServer().getPluginManager().registerEvents(onEntityDamagedListener, this);

        Listener onPlayerBattleStateChangeListener = new OnPlayerBattleStateChangeListener();
        getServer().getPluginManager().registerEvents(onPlayerBattleStateChangeListener, this);
    }

    @Override
    public void onDisable() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            Utils.storePlayerInventoryData(player);
        }
    }

    public static Plugin getPlugin() {
        return plugin;
    }

    public static MongoDB getDB() {
        return mongoDB;
    }
}