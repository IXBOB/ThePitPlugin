package com.ixbob.thepit.event;

import com.ixbob.thepit.util.NMSUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class OnPlayerConsumeItemListener implements Listener {
    @EventHandler
    public void onPlayerConsumeItem(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        if (item.getType() == Material.GOLDEN_APPLE) {
            event.setCancelled(true);
            NMSUtils.getEntityPlayer(player).setAbsorptionAmount(8);
            Collection<PotionEffect> effects = new ArrayList<>(List.of(new PotionEffect(PotionEffectType.REGENERATION, 100, 1, false, false)));
            player.addPotionEffects(effects);
            int newAmount = item.getAmount() - 1;
            item.setAmount(newAmount);
            player.getInventory().setItemInMainHand(item);
            player.setFoodLevel(player.getFoodLevel() + 4);
        }
    }
}
