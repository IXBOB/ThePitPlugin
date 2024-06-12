package com.ixbob.thepit.event;

import com.ixbob.thepit.LangLoader;
import com.ixbob.thepit.Main;
import com.ixbob.thepit.PlayerDataBlock;
import com.ixbob.thepit.enums.PitHitTypeEnum;
import com.ixbob.thepit.enums.gui.talent.GUITalentItemEnum;
import com.ixbob.thepit.event.thepit.PlayerBattleStateChangeEvent;
import com.ixbob.thepit.util.NMSUtils;
import com.ixbob.thepit.util.PlayerUtils;
import com.ixbob.thepit.util.TalentCalcuUtils;
import com.ixbob.thepit.util.Utils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Objects;

public class OnEntityDamageEntityListener implements Listener {
    private PlayerDataBlock damagedPlayerDataBlock;
    private PlayerDataBlock damagerDataBlock;
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
                if (damagerEntity instanceof Arrow damagerArrow) {
                    Player damager = (Player) damagerArrow.getShooter();
                    Player damagedPlayer = (Player) event.getEntity();
                    if (damagedPlayer == damager || Utils.isInLobbyArea(Objects.requireNonNull(damager).getLocation())) {
                        damagerEntity.remove();
                        event.setCancelled(true);
                        return;
                    }
                    damageEvent(damager, damagedPlayer, event, PitHitTypeEnum.ARROW);
                }
            }
        }

        if (event.getDamager() instanceof Player damager && event.getEntity() instanceof Player damagedPlayer) {
            //若有一方在大厅，则取消伤害
            if (Utils.isInLobbyArea(damagedPlayer.getLocation()) || Utils.isInLobbyArea(damager.getLocation())) {
                event.setCancelled(true);
                return;
            }
            //正常伤害逻辑
            damageEvent(damager, damagedPlayer, event, PitHitTypeEnum.NORMAL);
        }
    }

    private void damageEvent(Player damager, Player damagedPlayer, EntityDamageByEntityEvent event, PitHitTypeEnum hitType) {
        PlayerBattleStateChangeEvent damagerBattleStateChangeEvent = new PlayerBattleStateChangeEvent(damager, true);
        Bukkit.getPluginManager().callEvent(damagerBattleStateChangeEvent);
        PlayerBattleStateChangeEvent damagedPlayerBattleStateChangeEvent = new PlayerBattleStateChangeEvent(damagedPlayer, true);
        Bukkit.getPluginManager().callEvent(damagedPlayerBattleStateChangeEvent);

        this.damagedPlayerDataBlock = Main.getPlayerDataBlock(damagedPlayer);
        this.damagerDataBlock = Main.getPlayerDataBlock(damager);
        ArrayList<?> damagerEquippedTalentList = damagerDataBlock.getEquippedNormalTalentList();
        ArrayList<?> damagedPlayerEquippedNormalTalentList = damagedPlayerDataBlock.getEquippedNormalTalentList();
        ArrayList<Integer> talentLevelList = damagerDataBlock.getNormalTalentLevelList();

        //天赋 角斗士
        if (damagedPlayerEquippedNormalTalentList.contains(GUITalentItemEnum.GLADIATOR.getId())) {
            long nearbyPlayerAmount = Math.min(Bukkit.getOnlinePlayers().stream()
                    .filter(player -> !player.equals(damagedPlayer))
                    .filter(player -> player.getLocation().distance(damagedPlayer.getLocation()) <= 12)
                    .count(), 12);
            int talentId = GUITalentItemEnum.GLADIATOR.getId();
            int level = damagedPlayerDataBlock.getNormalTalentLevelList().get(talentId);
            event.setDamage(event.getDamage() * (1 - TalentCalcuUtils.getAddPointValue(talentId, level) * 0.01 * nearbyPlayerAmount));
        }

        if (hitType == PitHitTypeEnum.ARROW) {
            int infiniteArrowsId = GUITalentItemEnum.INFINITE_ARROWS.getId();
            if (damagerEquippedTalentList.contains(infiniteArrowsId)) {
                damager.getInventory().addItem(new ItemStack(Material.ARROW, (int) TalentCalcuUtils.getAddPointValue(infiniteArrowsId, talentLevelList.get(infiniteArrowsId))));
            }
            //天赋 灵活战术
            if (damagerEquippedTalentList.contains(GUITalentItemEnum.FLEXIBLE_TACTICS.getId())) {
                damagedPlayerDataBlock.getDamagedByArrowPlayers().add(damager);
            }
        }

        //天赋 吸血鬼
        if (damagerEquippedTalentList.contains(GUITalentItemEnum.BLOOD_SUCKER.getId())) {
            PlayerUtils.addHealth(damager, 1);
            if (hitType == PitHitTypeEnum.ARROW) {
                PlayerUtils.addHealth(damager, 3);
            }
        }

        if (damagerDataBlock.getTalentStrengthValidCountDownerRunnable() != null) {
            event.setDamage(event.getDamage() * (1 + damagerDataBlock.getTalentStrengthValidCountDownerRunnable().getAddDamagePercentagePoint() / 100));
        }

        if (damager.getInventory().getItemInMainHand().getType() == Material.DIAMOND_PICKAXE) {
            event.setDamage(3);
        }

        double finalDamageAmount = event.getFinalDamage();

        damagedPlayerDataBlock.addDamagedHistory(damager, finalDamageAmount);

        double absorptionHealthBefore = NMSUtils.getEntityPlayer(damagedPlayer).getAbsorptionAmount();
        double normalHealthBefore = damagedPlayer.getHealth();
        double allHealthBefore = absorptionHealthBefore + normalHealthBefore;

        double absorptionHealthAfter = Math.max(0, absorptionHealthBefore - finalDamageAmount);
        double allHealthAfter;
        double normalHealthAfter;

        if (absorptionHealthBefore - finalDamageAmount < 0) {
            normalHealthAfter = normalHealthBefore + absorptionHealthBefore - finalDamageAmount;
            allHealthAfter = normalHealthAfter;
        } else {
            normalHealthAfter = normalHealthBefore;
            allHealthAfter = normalHealthAfter + absorptionHealthAfter;
        }

        if (allHealthAfter > 0) {
            StringBuilder actionbarBuilder = new StringBuilder();
            actionbarBuilder.append(LangLoader.getString("damage_show_heart_actionbar_empty").repeat((int) Math.ceil(damagedPlayer.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() / 2)))
                    .replace(0, (int) Math.ceil(normalHealthAfter / 2) * 3, LangLoader.getString("damage_show_heart_actionbar_healthful").repeat((int) Math.ceil(normalHealthAfter / 2)))
                    .replace((int) Math.ceil(normalHealthAfter / 2) * 3, (int) Math.ceil(normalHealthAfter / 2) * 3 + (int) Math.ceil(finalDamageAmount / 2) * 3, LangLoader.getString("damage_show_heart_actionbar_damaged").repeat((int) Math.ceil(finalDamageAmount / 2)))
                    .append(LangLoader.getString("damage_show_heart_actionbar_extra").repeat(
                            absorptionHealthAfter > 0 ? (int) Math.ceil(absorptionHealthAfter / 2) : 0
                    ));
            damager.sendActionBar(Component.text(PlayerUtils.getPitDisplayName(damagedPlayer) + " " + actionbarBuilder));
        }
        else {
            damager.sendActionBar(Component.text(String.format(LangLoader.getString("damage_kill_show_player_info"), PlayerUtils.getPitDisplayName(damagedPlayer))));
        }

        //若被击杀的玩家受到的伤害足以导致死亡
        if (allHealthAfter <= 0) {
            event.setCancelled(true);
            PlayerUtils.onPlayerKillAnother(damagedPlayer, damager);
        }
    }
}