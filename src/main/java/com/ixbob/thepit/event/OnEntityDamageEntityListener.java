package com.ixbob.thepit.event;

import com.ixbob.thepit.Main;
import com.ixbob.thepit.handler.config.LangLoader;
import com.ixbob.thepit.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.Objects;

public class OnEntityDamageEntityListener implements Listener {
    @EventHandler
    public void onPlayerBeKilled(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player) && event.getEntity() instanceof Player) {
            Entity damagerEntity = event.getDamager();
            if (Utils.isInLobbyArea(damagerEntity.getLocation())) {
                if (damagerEntity.getType() == EntityType.ARROW) {
                    damagerEntity.remove();
                }
                event.setCancelled(true);
                return;
            }
            else {
                if (damagerEntity instanceof Arrow) {
                    Arrow damagerArrow = (Arrow) damagerEntity;
                    Player damager = (Player) damagerArrow.getShooter();
                    Player damagedPlayer = (Player) event.getEntity();

                    damageEvent(damager, damagedPlayer, event);
                }
            }
        }

        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            Player damagedPlayer = (Player) event.getEntity();
            Player damager = (Player) event.getDamager();

            if (Utils.isInLobbyArea(damagedPlayer.getLocation())) {
                event.setCancelled(true);
                return;
            }

            damageEvent(damager, damagedPlayer, event);
        }
    }

    private void damageEvent(Player damager, Player damagedPlayer, EntityDamageByEntityEvent event) {
        if (damagedPlayer.getHealth() <= event.getFinalDamage()) {
            onPlayerKillAnother(damagedPlayer, damager);

            event.setCancelled(true);
            damagedPlayer.setHealth(damagedPlayer.getHealthScale());
            Utils.setMostBasicKit(damagedPlayer, true);
            damagedPlayer.teleport(Main.spawnLocation);
        }
    }

    private void onPlayerKillAnother(Player deathPlayer, Player killer) {
        int addXp = (10 + (int) (Math.random() * 15))* (1 + Main.playerDataMap.get(deathPlayer).getConsecutiveKillAmount());
        Utils.addXp(killer, addXp);
        int addCoin = (5 + (int) (Math.random() * 10))* (1 + Main.playerDataMap.get(deathPlayer).getConsecutiveKillAmount());
        Utils.addCoin(killer, addCoin);

        killer.playSound(killer.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
        deathPlayer.playSound(deathPlayer.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 1, 1);

        killer.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 1));

        for (PotionEffect effect : deathPlayer.getActivePotionEffects()) {
            deathPlayer.removePotionEffect(effect.getType());
        }

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
