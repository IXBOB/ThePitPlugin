package com.ixbob.thepit;

import com.ixbob.thepit.command.CommandShop;
import com.ixbob.thepit.command.CommandSpawn;
import com.ixbob.thepit.command.CommandTalent;
import com.ixbob.thepit.command.CommandTest;
import com.ixbob.thepit.enums.CustomBasicToolEnum;
import com.ixbob.thepit.enums.CustomSkullEnum;
import com.ixbob.thepit.enums.DropItemEnum;
import com.ixbob.thepit.event.*;
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
    public static final PlayerGUIManager GUIManager = new PlayerGUIManager();
    public static final TaskManager taskManager = new TaskManager();

    @Override
    public void onEnable() {
        Main.plugin = this;

        spawnLocation = new Location(Bukkit.getWorlds().get(0), 6, 153, 5);

        MongoDB mongoDB = new MongoDB();
        mongoDB.connect("127.0.0.1", 27017, this);
        mongoDB.setCollection("ThePit_IXBOB");
        Main.mongoDB = mongoDB;

        LangLoader.init(this);
        CustomSkullEnum.init();
        CustomBasicToolEnum.init();
        DropItemEnum.init();

        this.saveDefaultConfig();
        lobbyAreaFromPosList = config.getIntegerList("lobby_area.from");
        lobbyAreaToPosList = config.getIntegerList("lobby_area.to");

        this.getCommand("test").setExecutor(new CommandTest());
        this.getCommand("spawn").setExecutor(new CommandSpawn());
        this.getCommand("talent").setExecutor(new CommandTalent());
        this.getCommand("shop").setExecutor(new CommandShop());

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

        Listener onPlayerTypedSpawnChangeListener = new OnPlayerTypedSpawnChangeListener();
        getServer().getPluginManager().registerEvents(onPlayerTypedSpawnChangeListener, this);

        Listener onPlayerClickGUIListener = new OnPlayerClickGUIListener();
        getServer().getPluginManager().registerEvents(onPlayerClickGUIListener, this);

        Listener onPlayerDragInventoryListener = new OnPlayerDragInventoryListener();
        getServer().getPluginManager().registerEvents(onPlayerDragInventoryListener, this);

        Listener onPlayerCloseGUIListener = new OnPlayerCloseGUIListener();
        getServer().getPluginManager().registerEvents(onPlayerCloseGUIListener, this);

        Listener onPlayerTalentLevelChangeListener = new OnPlayerTalentLevelChangeListener();
        getServer().getPluginManager().registerEvents(onPlayerTalentLevelChangeListener, this);

        Listener onPlayerEquippedTalentChangeListener = new OnPlayerEquippedTalentChangeListener();
        getServer().getPluginManager().registerEvents(onPlayerEquippedTalentChangeListener, this);

        Listener onPlayerInteractListener = new OnPlayerInteractListener();
        getServer().getPluginManager().registerEvents(onPlayerInteractListener, this);

        Listener onPlayerConsumeItemListener = new OnPlayerConsumeItemListener();
        getServer().getPluginManager().registerEvents(onPlayerConsumeItemListener, this);

        Listener onPlayerSwapHandItemsListener = new OnPlayerSwapHandItemsListener();
        getServer().getPluginManager().registerEvents(onPlayerSwapHandItemsListener, this);

        Listener onPlayerDropItemListener = new OnPlayerDropItemListener();
        getServer().getPluginManager().registerEvents(onPlayerDropItemListener, this);

        Listener onEntityPickupItemListener = new OnEntityPickupItemListener();
        getServer().getPluginManager().registerEvents(onEntityPickupItemListener, this);

        Listener onPlayerPlaceBlockListener = new OnPlayerPlaceBlockListener();
        getServer().getPluginManager().registerEvents(onPlayerPlaceBlockListener, this);

        Listener onPlayerBreakBlockListener = new OnPlayerBreakBlockListener();
        getServer().getPluginManager().registerEvents(onPlayerBreakBlockListener, this);
    }

    @Override
    public void onDisable() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            Utils.storePlayerInventoryData(player);
        }
        getTaskManager().getPlacedBlockTaskHandler().removeAll();
    }

    public static Plugin getPlugin() {
        return plugin;
    }

    public static MongoDB getDB() {
        return mongoDB;
    }

    public static PlayerGUIManager getGUIManager() {
        return GUIManager;
    }

    public static TaskManager getTaskManager() {
        return taskManager;
    }

    public static PlayerDataBlock getPlayerDataBlock(Player player) {
        return playerDataMap.get(player);
    }

    public static void addPlayerDataBlock(Player player, PlayerDataBlock playerDataBlock) {
        playerDataMap.put(player, playerDataBlock);
    }
}