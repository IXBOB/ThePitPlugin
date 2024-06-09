package com.ixbob.thepit;

import com.ixbob.thepit.gui.BasicGUIImpl;
import com.ixbob.thepit.gui.GUIShop;
import com.ixbob.thepit.gui.GUITalent;
import com.ixbob.thepit.gui.GUIWatchman;
import com.ixbob.thepit.util.Utils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class PlayerGUIManager {
    private static PlayerGUIManager instance;
    private final Map<Player, BasicGUIImpl> playerOpeningCustomGUI = new HashMap<>();

    private PlayerGUIManager() {}

    public static PlayerGUIManager getInstance() {
        if (instance == null) {
            instance = new PlayerGUIManager();
        }
        return instance;
    }

    public BasicGUIImpl getOpeningGUI(Player player) {
        return playerOpeningCustomGUI.get(player);
    }

    public void openTalentGUI(Player player) {
        GUITalent gui = new GUITalent(player);
        addPlayerToHashMap(player, gui);
        gui.display(LangLoader.getString("talent_gui_title"));
    }

    public void openShopGUI(Player player) {
        GUIShop gui = new GUIShop(player);
        addPlayerToHashMap(player, gui);
        gui.display(LangLoader.getString("shop_gui_title"));
    }

    public void openWatchmanGUI(Player player) {
        GUIWatchman gui = new GUIWatchman(player);
        addPlayerToHashMap(player, gui);
        gui.display(LangLoader.getString("watchman_gui_title"));
    }

    public void onCloseGUI(Player player) {
        removePlayerFromHashMap(player);
    }

    private void addPlayerToHashMap(Player player, BasicGUIImpl gui) {
        if (playerOpeningCustomGUI.containsKey(player)) {
            player.closeInventory();
            try {
                throw new IllegalArgumentException("Player " + player + " has already opening a custom gui");
            } catch (IllegalArgumentException e) {
                player.sendMessage(ChatColor.RED + Utils.getFormattedNowTime() + " " + e.getMessage() + " " + Arrays.toString(e.getStackTrace()));
                player.sendMessage(LangLoader.getString("system_error_message"));
                Main.getInstance().getLogger().log(Level.SEVERE, e.getMessage(), e);
            }
        }
        playerOpeningCustomGUI.put(player, gui);
    }

    private void removePlayerFromHashMap(Player player) {
        playerOpeningCustomGUI.remove(player);
    }
}
