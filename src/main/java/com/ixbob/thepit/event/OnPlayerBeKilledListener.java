package com.ixbob.thepit.event;

import com.ixbob.thepit.Main;
import com.ixbob.thepit.PlayerDataBlock;
import com.ixbob.thepit.event.custom.PlayerOwnXpModifiedEvent;
import com.ixbob.thepit.handler.config.LangLoader;
import com.ixbob.thepit.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Objects;

public class OnPlayerBeKilledListener implements Listener {
    @EventHandler
    public void onPlayerBeKilled(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            Player deathPlayer = (Player) event.getEntity();
            Player killer = (Player) event.getDamager();
            if (deathPlayer.getHealth() <= event.getFinalDamage()) {
                onPlayerKillAnother(deathPlayer, killer);

                event.setCancelled(true);
                deathPlayer.setHealth(deathPlayer.getHealthScale());
                deathPlayer.teleport(Main.initialLocation);
            }
        }
    }

    private void onPlayerKillAnother(Player deathPlayer, Player killer) {
        int addXp = (10 + (int) (Math.random() * 15))* (1 + Main.playerDataMap.get(deathPlayer).getConsecutiveKillAmount());
        Utils.addXp(killer, addXp);
        int addCoin = (5 + (int) (Math.random() * 10))* (1 + Main.playerDataMap.get(deathPlayer).getConsecutiveKillAmount());
        Utils.addCoin(killer, addCoin);

        killer.playSound(killer.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
        deathPlayer.playSound(deathPlayer.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 1, 1);

        //广播消息不广播给killer和deathPlayer
        String broadcastMes = String.format(LangLoader.get("player_kill_other_message_broadcast"), Utils.getDisplayName(killer), Utils.getDisplayName(deathPlayer));
        String toKillerMes = String.format(String.format(LangLoader.get("player_kill_other_message_to_killer"), Utils.getDisplayName(deathPlayer))) + " " + String.format(LangLoader.get("player_get_xp_message"), addXp) + " " + String.format(LangLoader.get("player_get_coin_message"), addCoin);
        String toDeathPlayerMes = String.format(LangLoader.get("player_kill_other_message_to_death_player"), Utils.getDisplayName(killer));

        for (Player onlinePl : Bukkit.getOnlinePlayers()) {
            if ( ! (Objects.equals(onlinePl.getName(), killer.getName()) || Objects.equals(onlinePl.getName(), deathPlayer.getName()))  ) {
                onlinePl.sendMessage(broadcastMes);
            }
        }
        killer.sendMessage(toKillerMes);
        deathPlayer.sendMessage(toDeathPlayerMes);
    }
}
