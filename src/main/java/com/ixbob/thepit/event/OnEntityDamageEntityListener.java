package com.ixbob.thepit.event;

import com.ixbob.thepit.Main;
import com.ixbob.thepit.PlayerDataBlock;
import com.ixbob.thepit.enums.PitItem;
import com.ixbob.thepit.enums.TalentItemsEnum;
import com.ixbob.thepit.event.custom.PlayerBattleStateChangeEvent;
import com.ixbob.thepit.LangLoader;
import com.ixbob.thepit.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
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
            //若有一方在大厅，则取消伤害
            if (Utils.isInLobbyArea(damagedPlayer.getLocation()) || Utils.isInLobbyArea(damager.getLocation())) {
                event.setCancelled(true);
                return;
            }
            //正常伤害逻辑
            damageEvent(damager, damagedPlayer, event);
        }
    }

    private void damageEvent(Player damager, Player damagedPlayer, EntityDamageByEntityEvent event) {
        PlayerBattleStateChangeEvent damagerBattleStateChangeEvent = new PlayerBattleStateChangeEvent(damager, true);
        Bukkit.getPluginManager().callEvent(damagerBattleStateChangeEvent);
        PlayerBattleStateChangeEvent damagedPlayerBattleStateChangeEvent = new PlayerBattleStateChangeEvent(damagedPlayer, true);
        Bukkit.getPluginManager().callEvent(damagedPlayerBattleStateChangeEvent);

        //若被击杀的玩家受到的伤害足以导致死亡
        if (damagedPlayer.getHealth() <= event.getFinalDamage()) {
            event.setCancelled(true);

            if (damager == damagedPlayer) { //检测自己击杀自己
                backToLobby(damagedPlayer);
                return;
            }

            onPlayerKillAnother(damagedPlayer, damager);
        }
    }

    private void onPlayerKillAnother(Player deathPlayer, Player killer) {
        int addXp = (10 + (int) (Math.random() * 15))* (1 + Main.getPlayerDataBlock(deathPlayer).getConsecutiveKillAmount());
        Utils.addXp(killer, addXp);
        int addCoin = (5 + (int) (Math.random() * 10))* (1 + Main.getPlayerDataBlock(deathPlayer).getConsecutiveKillAmount());
        Utils.addCoin(killer, addCoin);

        killer.playSound(killer.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
        deathPlayer.playSound(deathPlayer.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 1, 1);

        PlayerDataBlock dataBlock = Main.getPlayerDataBlock(killer);
        ArrayList<?> equippedTalentList = dataBlock.getEquippedTalentList();
        if (equippedTalentList.contains(TalentItemsEnum.GOLDEN_CHOCOLATE.getId())) {
            killer.getInventory().addItem(PitItem.GOLDEN_CHOCOLATE.getItemStack());
        } else killer.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 1));

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

        backToLobby(deathPlayer);
    }

    private void backToLobby(Player deathPlayer) {
        deathPlayer.setHealth(deathPlayer.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
        deathPlayer.setFoodLevel(20);
        Utils.setMostBasicKit(deathPlayer, true);
        deathPlayer.teleport(Main.spawnLocation);
        Utils.setBattleState(deathPlayer, false);
        Utils.setTypedSpawn(deathPlayer, false);
    }
}
