package com.ixbob.thepit;

import com.ixbob.thepit.gui.AbstractGUI;
import com.ixbob.thepit.gui.GUIShop;
import com.ixbob.thepit.gui.GUITalent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PlayerGUIManager {
    private static PlayerGUIManager instance;
    private final Map<Player, AbstractGUI> playerOpeningCustomGUI = new HashMap<>();

    private PlayerGUIManager() {}

    public static PlayerGUIManager getInstance() {
        if (instance == null) {
            instance = new PlayerGUIManager();
        }
        return instance;
    }

    public AbstractGUI getOpeningGUI(Player player) {
        return playerOpeningCustomGUI.get(player);
    }

    public void openTalentGUI(Player player) {
        GUITalent gui = new GUITalent(player);
        addPlayerToHashMap(player, gui);
        gui.initFrame(player);
        gui.open();
        gui.initContent();
    }

    public void openShopGUI(Player player) {
        GUIShop gui = new GUIShop(player);
        addPlayerToHashMap(player, gui);
        gui.initFrame(player);
        gui.open();
        gui.initContent();
    }

    public void onCloseGUI(Player player) {
        removePlayerFromHashMap(player);
    }

    private void addPlayerToHashMap(Player player, AbstractGUI gui) {
        if (playerOpeningCustomGUI.containsKey(player)) {
            player.closeInventory();
            try {
                throw new IllegalArgumentException("Player " + player + " has already opening a custom gui");
            } catch (IllegalArgumentException e) {
                player.sendMessage(ChatColor.RED + (new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")).format(new Date()) + " " + e.getMessage() + " " + Arrays.toString(e.getStackTrace()));
                player.sendMessage(LangLoader.get("system_error_message"));
            }

        }
        playerOpeningCustomGUI.put(player, gui);
    }

    private void removePlayerFromHashMap(Player player) {
        playerOpeningCustomGUI.remove(player);
    }
}
