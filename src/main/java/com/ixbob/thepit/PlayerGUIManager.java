package com.ixbob.thepit;

import com.ixbob.thepit.gui.AbstractGUI;
import com.ixbob.thepit.gui.GUIShop;
import com.ixbob.thepit.gui.GUITalent;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class PlayerGUIManager {
    private final Map<Player, AbstractGUI> playerOpeningCustomGUI = new HashMap<>();

    public PlayerGUIManager() {}

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
            throw new IllegalArgumentException("Player " + player + " is already has opening custom gui");
        }
        playerOpeningCustomGUI.put(player, gui);
    }

    private void removePlayerFromHashMap(Player player) {
        playerOpeningCustomGUI.remove(player);
    }
}
