package com.ixbob.thepit.event;

import com.ixbob.thepit.LangLoader;
import com.ixbob.thepit.Main;
import com.ixbob.thepit.PlayerDataBlock;
import com.ixbob.thepit.enums.PitHitType;
import com.ixbob.thepit.enums.PitItem;
import com.ixbob.thepit.enums.TalentItemsEnum;
import com.ixbob.thepit.event.custom.PlayerBattleStateChangeEvent;
import com.ixbob.thepit.util.NMSUtils;
import com.ixbob.thepit.util.TalentCalcuUtils;
import com.ixbob.thepit.util.Utils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
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
    public void onEntityDamagedByEntity(EntityDamageByEntityEvent event) {
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
                    if (damagedPlayer == damager) {
                        damagerEntity.remove();
                        event.setCancelled(true);
                        return;
                    }
                    damageEvent(damager, damagedPlayer, event, PitHitType.ARROW);
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
            damageEvent(damager, damagedPlayer, event, PitHitType.NORMAL);
        }
    }

    private void damageEvent(Player damager, Player damagedPlayer, EntityDamageByEntityEvent event, PitHitType hitType) {
        PlayerBattleStateChangeEvent damagerBattleStateChangeEvent = new PlayerBattleStateChangeEvent(damager, true);
        Bukkit.getPluginManager().callEvent(damagerBattleStateChangeEvent);
        PlayerBattleStateChangeEvent damagedPlayerBattleStateChangeEvent = new PlayerBattleStateChangeEvent(damagedPlayer, true);
        Bukkit.getPluginManager().callEvent(damagedPlayerBattleStateChangeEvent);

        PlayerDataBlock damagerDataBlock = Main.getPlayerDataBlock(damager);
        ArrayList<?> damagerEquippedTalentList = damagerDataBlock.getEquippedTalentList();
        ArrayList<Integer> talentLevelList = damagerDataBlock.getTalentLevelList();

        if (hitType == PitHitType.ARROW) {
            int infiniteArrowsId = TalentItemsEnum.INFINITE_ARROWS.getId();
            if (damagerEquippedTalentList.contains(infiniteArrowsId)) {
                damager.getInventory().addItem(new ItemStack(Material.ARROW, (int) TalentCalcuUtils.getAddPointValue(infiniteArrowsId, talentLevelList.get(infiniteArrowsId))));
            }
        }

        double finalDamageAmount = event.getFinalDamage();

        double absorptionHealthBefore = NMSUtils.getEntityPlayer(damagedPlayer).getAbsorptionHearts();
        double normalHealthBefore = damagedPlayer.getHealth();
        double allHealthBefore = absorptionHealthBefore + normalHealthBefore;

        double absorptionHealthAfter = absorptionHealthBefore - event.getDamage(); //getDamage()? 但是实际似乎比getFinalDamage准确，不懂其计算方式·_·
        double allHealthAfter;
        double normalHealthAfter;
        if (absorptionHealthAfter < 0) {
            normalHealthAfter = normalHealthBefore + absorptionHealthAfter;
            allHealthAfter = normalHealthAfter;
        } else {
            normalHealthAfter = normalHealthBefore;
            allHealthAfter = normalHealthAfter + absorptionHealthAfter;
        }

        if (allHealthAfter > 0) {
            StringBuilder actionbarBuilder = new StringBuilder();
            actionbarBuilder.append(LangLoader.get("damage_show_heart_actionbar_empty").repeat((int) Math.ceil(damagedPlayer.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() / 2)))
                    .replace(0, (int) Math.ceil(normalHealthAfter / 2) * 3, LangLoader.get("damage_show_heart_actionbar_healthful").repeat((int) Math.ceil(normalHealthAfter / 2)))
                    .replace((int) Math.ceil(normalHealthAfter / 2) * 3, (int) Math.ceil(normalHealthAfter / 2) * 3 + (int) Math.ceil(finalDamageAmount / 2) * 3, LangLoader.get("damage_show_heart_actionbar_damaged").repeat((int) Math.ceil(finalDamageAmount / 2)))
                    .append(LangLoader.get("damage_show_heart_actionbar_extra").repeat(
                            absorptionHealthAfter > 0 ? (int) Math.ceil(absorptionHealthAfter / 2) : 0
                    ));
            damager.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Utils.getPitDisplayName(damagedPlayer) + " " + actionbarBuilder));
        }
        else {
            damager.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(String.format(LangLoader.get("damage_kill_show_player_info"), Utils.getPitDisplayName(damagedPlayer))));
        }

        //若被击杀的玩家受到的伤害足以导致死亡
        if (allHealthAfter <= 0) {
            event.setCancelled(true);
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
        String broadcastMes = String.format(LangLoader.get("player_kill_other_message_broadcast"), Utils.getPitDisplayName(killer), Utils.getPitDisplayName(deathPlayer));
        String toKillerMes = String.format(String.format(LangLoader.get("player_kill_other_message_to_killer"), Utils.getPitDisplayName(deathPlayer))) + " " + String.format(LangLoader.get("player_get_xp_message"), addXp) + " " + String.format(LangLoader.get("player_get_coin_message"), addCoin);
        String toDeathPlayerMes = String.format(LangLoader.get("player_kill_other_message_to_death_player"), Utils.getPitDisplayName(killer));

        for (Player onlinePl : Bukkit.getOnlinePlayers()) {
            if ( ! (Objects.equals(onlinePl.getName(), killer.getName()) || Objects.equals(onlinePl.getName(), deathPlayer.getName()))  ) {
                onlinePl.sendMessage(broadcastMes);
            }
        }
        killer.sendMessage(toKillerMes);
        deathPlayer.sendMessage(toDeathPlayerMes);

        deathBackToLobby(deathPlayer);
    }

    private void deathBackToLobby(Player player) {
        Utils.setMostBasicKit(player, true);
        Utils.setBattleState(player, false);
        Utils.setTypedSpawn(player, false);
        Utils.backToLobby(player);
    }
}
