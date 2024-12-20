package com.ixbob.thepit;

import com.ixbob.thepit.command.*;
import com.ixbob.thepit.enums.PitItemEnumHolder;
import com.ixbob.thepit.enums.ScoreboardStructureEnum;
import com.ixbob.thepit.event.*;
import com.ixbob.thepit.event.citizens.OnCitizensEnableListener;
import com.ixbob.thepit.event.citizens.OnNPCRightClickListener;
import com.ixbob.thepit.event.paper.OnPlayerChatListener;
import com.ixbob.thepit.service.MongoDBService;
import com.ixbob.thepit.task.onstart.delayed.ResetHologramsRunnable;
import com.ixbob.thepit.util.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Main extends JavaPlugin {
    private static Plugin instance;
    private static MongoDBService mongoDBService;
    public static Map<Player, PlayerDataBlock> playerDataMap = new HashMap<>();
    public static Location spawnLocation;
    public static PlayerGUIManager GUIManager;
    public static TaskManager taskManager;
    public static String bungeecordChannelName = "ixbob_thepit:bungeecord";
    public static int signInPlayerAmountFromLaunch = 0;

    @Override
    public void onEnable() {
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, bungeecordChannelName);

        this.saveDefaultConfig();
        Main.instance = this;

        MongoDBService mongoDBService = MongoDBService.getInstance();
        getServer().getServicesManager().register(MongoDBService.class, mongoDBService, this, ServicePriority.Normal);
        Main.mongoDBService = mongoDBService;

        spawnLocation = new Location(Bukkit.getWorlds().get(0), -8, 153, -5);

        LangLoader.init();
        PitItemEnumHolder.init();
        ScoreboardStructureEnum.init();

        GUIManager = PlayerGUIManager.getInstance();
        taskManager = TaskManager.getInstance();

        for (Entity entity : Bukkit.getWorlds().get(0).getEntities()) {
            if (entity instanceof Item item) {
                item.remove();
            }
        }

//调试显示掉落物个数和getGoldIngotExistAmount()个数
//        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
//                System.out.println(Bukkit.getServer().getWorlds().get(0).getEntities().stream().filter(entity -> entity.getType() == EntityType.DROPPED_ITEM).count());
//            System.out.println(taskManager.getRandomGoldIngotSpawnTaskHandler().getGoldIngotExistAmount());}, 0, 5);

        Objects.requireNonNull(this.getCommand("test")).setExecutor(new CommandTest());
        Objects.requireNonNull(this.getCommand("spawn")).setExecutor(new CommandSpawn());
        Objects.requireNonNull(this.getCommand("talent")).setExecutor(new CommandTalent());
        Objects.requireNonNull(this.getCommand("shop")).setExecutor(new CommandShop());
        Objects.requireNonNull(this.getCommand("watchman")).setExecutor(new CommandWatchman());
        Objects.requireNonNull(this.getCommand("ench")).setExecutor(new CommandEnch());
        Objects.requireNonNull(this.getCommand("help")).setExecutor(new CommandHelp());

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
                new OnPlayerPickupItemListener(),
                new OnPlayerPlaceBlockListener(),
                new OnPlayerBreakBlockListener(),
                new OnItemDespawnListener(),
                new OnCitizensEnableListener(),
                new OnNPCRightClickListener(),
                new OnPlayerChatListener()
        );

        getServer().getScheduler().runTaskLater(this, new ResetHologramsRunnable(), 20);

//        getServer().getScheduler().runTask(this, new SendScoreboardPacketAdapterRunnable());
//        getServer().getScheduler().runTask(this, new RegisterPlayerNamePacketAdapterRunnable());
    }

    @Override
    public void onDisable() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            PlayerUtils.storePlayerInventoryData(player);
        }
        getTaskManager().getPlacedBlockTaskHandler().removeAll();
    }

    private void registerEvents(Listener... listeners) {
        for (Listener listener : listeners) {
            getServer().getPluginManager().registerEvents(listener, this);
        }
    }

    public static Plugin getInstance() {
        return instance;
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

    public static MongoDBService getMongoDBService() {
        return mongoDBService;
    }
}