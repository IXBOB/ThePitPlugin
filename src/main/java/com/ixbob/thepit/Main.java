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
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
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
    public static PlayerGUIManager GUIManager;
    public static TaskManager taskManager;

    @Override
    public void onEnable() {
        Main.plugin = this;

        spawnLocation = new Location(Bukkit.getWorlds().get(0), 6, 153, 5);

        MongoDB mongoDB = new MongoDB();
        mongoDB.connect("127.0.0.1", 27017, this);
        mongoDB.setCollection("ThePit_IXBOB");
        Main.mongoDB = mongoDB;

        LangLoader.init();
        CustomSkullEnum.init();
        CustomBasicToolEnum.init();
        DropItemEnum.init();

        this.saveDefaultConfig();
        lobbyAreaFromPosList = config.getIntegerList("lobby_area.from");
        lobbyAreaToPosList = config.getIntegerList("lobby_area.to");

        GUIManager = new PlayerGUIManager();
        taskManager = new TaskManager();

        for (Entity entity : Bukkit.getWorlds().get(0).getEntities()) {
            if (entity instanceof Item) {
                Item item = (Item) entity;
                item.remove();
            }
        }

//调试显示掉落物个数和getGoldIngotExistAmount()个数
//        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
//                System.out.println(Bukkit.getServer().getWorlds().get(0).getEntities().stream().filter(entity -> entity.getType() == EntityType.DROPPED_ITEM).count());
//            System.out.println(taskManager.getRandomGoldIngotSpawnTaskHandler().getGoldIngotExistAmount());}, 0, 5);

        this.getCommand("test").setExecutor(new CommandTest());
        this.getCommand("spawn").setExecutor(new CommandSpawn());
        this.getCommand("talent").setExecutor(new CommandTalent());
        this.getCommand("shop").setExecutor(new CommandShop());

        registerEvents(
                new OnPlayerJoinListener(),
                new OnPlayerLeaveListener(),
                new OnEntityDamageEntityListener(),
                new OnPlayerOwnXpModifiedListener(),
                new OnPlayerOwnCoinModifiedListener(),
                new OnPlayerUpgradeLevelListener(),
                new OnEntityDamagedListener(),
                new OnPlayerBattleStateChangeListener(),
                new OnPlayerTypedSpawnChangeListener(),
                new OnPlayerClickGUIListener(),
                new OnPlayerDragInventoryListener(),
                new OnPlayerCloseGUIListener(),
                new OnPlayerTalentLevelChangeListener(),
                new OnPlayerEquippedTalentChangeListener(),
                new OnPlayerInteractListener(),
                new OnPlayerConsumeItemListener(),
                new OnPlayerSwapHandItemsListener(),
                new OnPlayerDropItemListener(),
                new OnPlayerPickupItemListener(), // 请确认这个监听器类名是否正确
                new OnPlayerPlaceBlockListener(),
                new OnPlayerBreakBlockListener(),
                new OnItemDespawnListener()
        );
    }

    @Override
    public void onDisable() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            Utils.storePlayerInventoryData(player);
        }
        getTaskManager().getPlacedBlockTaskHandler().removeAll();
    }

    private void registerEvents(Listener... listeners) {
        for (Listener listener : listeners) {
            getServer().getPluginManager().registerEvents(listener, this);
        }
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