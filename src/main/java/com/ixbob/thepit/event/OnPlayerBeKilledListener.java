package com.ixbob.thepit.event;

import com.ixbob.thepit.Main;
import com.ixbob.thepit.PlayerDataBlock;
import com.ixbob.thepit.event.custom.PlayerOwnXpModifiedEvent;
import com.ixbob.thepit.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class OnPlayerBeKilledListener implements Listener {
    @EventHandler
    public void onPlayerBeKilled(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            Player deathPlayer = (Player) event.getEntity();
            Player killer = (Player) event.getDamager();
            if (deathPlayer.getHealth() <= event.getDamage()) {
                onPlayerKillAnother(deathPlayer, killer);
                onPlayerBeKilled(deathPlayer, killer);
            }
        }
    }

    private void onPlayerKillAnother(Player deathPlayer, Player killer) {
        int addXp = (10 + (int) (Math.random() * 15))* (1 + Main.playerDataMap.get(deathPlayer).getConsecutiveKillAmount());
        Utils.addXp(killer, addXp);
        int addCoin = (5 + (int) (Math.random() * 10))* (1 + Main.playerDataMap.get(deathPlayer).getConsecutiveKillAmount());
        Utils.addCoin(killer, addCoin);
    }

    private void onPlayerBeKilled(Player deathPlayer, Player killer) {

    }
}
