package com.ixbob.thepit;

import com.ixbob.thepit.gui.GUITalent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class PlayerGUIManager {
    private Map<Player, AbstractGUI> playerOpeningCustomGUI = new HashMap<>();

    public PlayerGUIManager() {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            playerOpeningCustomGUI.put(onlinePlayer, null);
        }
    }

    public Map<Player, AbstractGUI> getPlayerOpeningCustomGUIHashMap() {
        return playerOpeningCustomGUI;
    }

    public AbstractGUI getOpeningGUI(Player player) {
        return playerOpeningCustomGUI.get(player);
    }

    public void openTalentGUI(Player player) {
        GUITalent gui = new GUITalent(player);
        playerOpeningCustomGUI.put(player, gui);
        gui.open(player);
    }
}
