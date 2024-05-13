package com.ixbob.thepit;

import com.ixbob.thepit.event.OnPlayerBeKilledListener;
import com.ixbob.thepit.event.OnPlayerJoinListener;
import com.ixbob.thepit.event.OnPlayerLeaveListener;
import com.ixbob.thepit.event.OnPlayerOwnXpModifiedListener;
import com.ixbob.thepit.handler.config.LangLoader;
import org.bukkit.Bukkit;
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
    @Override
    public void onEnable() {
        Main.plugin = this;

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
    }

    public static Plugin getPlugin() {
        return plugin;
    }

    public static MongoDB getDB() {
        return mongoDB;
    }
}