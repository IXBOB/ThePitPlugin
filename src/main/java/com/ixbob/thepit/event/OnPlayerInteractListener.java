package com.ixbob.thepit.event;

import com.ixbob.thepit.Main;
import com.ixbob.thepit.PlayerDataBlock;
import com.ixbob.thepit.enums.PitItem;
import com.ixbob.thepit.enums.TalentItemsEnum;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class OnPlayerInteractListener implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack item = event.getItem();
        PlayerDataBlock dataBlock = Main.getPlayerDataBlock(player);
        ArrayList<Integer> talentLevelList = dataBlock.getTalentLevelList();
        if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
            if (item != null) {
                if (item.getType() == PitItem.GOLDEN_CHOCOLATE.getItemStack().getType()) {
                    event.setCancelled(true);
                    item.setAmount(item.getAmount() - 1);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 2400, talentLevelList.get(TalentItemsEnum.GOLDEN_CHOCOLATE.getId())), false);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 1), false);
                }
            }
        }
    }
}
