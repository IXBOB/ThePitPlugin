package com.ixbob.thepit;

import com.ixbob.thepit.gui.GUITalent;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class PlayerGUIManager {
    private Map<Player, AbstractGUI> playerOpeningCustomGUI = new HashMap<>();

    public PlayerGUIManager() {

    }

    public void addPlayerToHashMap(Player player, AbstractGUI gui) {
        if (playerOpeningCustomGUI.containsKey(player)) {
            throw new IllegalArgumentException("Player " + player + " is already has opening custom gui");
        }
        playerOpeningCustomGUI.put(player, gui);
    }

    public void removePlayerFromHashMap(Player player) {
        playerOpeningCustomGUI.remove(player);
    }

    public Map<Player, AbstractGUI> getPlayerOpeningCustomGUIHashMap() {
        return playerOpeningCustomGUI;
    }

    public AbstractGUI getOpeningGUI(Player player) {
        return playerOpeningCustomGUI.get(player);
    }

    public void openTalentGUI(Player player) {
        GUITalent gui = new GUITalent(player);
        addPlayerToHashMap(player, gui);
        gui.open(player);
        //TODO: 添加GUI初始化物品
    }
}
