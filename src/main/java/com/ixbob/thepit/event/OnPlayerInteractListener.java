package com.ixbob.thepit.event;

import com.ixbob.thepit.Main;
import com.ixbob.thepit.PlayerDataBlock;
import com.ixbob.thepit.enums.PitItemEnum;
import com.ixbob.thepit.util.NMSUtils;
import com.ixbob.thepit.util.TalentCalcuUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class OnPlayerInteractListener implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack item = event.getItem();
        PlayerDataBlock dataBlock = Main.getPlayerDataBlock(player);
        ArrayList<Integer> talentLevelList = dataBlock.getNormalTalentLevelList();
        if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
            if (item != null) {
                if (item.getType() == PitItemEnum.GOLDEN_CHOCOLATE.getItemStack().getType()) {
                    event.setCancelled(true);
                    item.setAmount(item.getAmount() - 1);
                    NMSUtils.getEntityPlayer(player).setAbsorptionAmount(TalentCalcuUtils.getAddPointValue(1, talentLevelList.get(1)));
                    Collection<PotionEffect> effects = new ArrayList<>(List.of(new PotionEffect(PotionEffectType.REGENERATION, 100, 1, false, false)));
                    player.addPotionEffects(effects);
                    player.setFoodLevel(player.getFoodLevel() + 4);
                }
            }
        }
    }
}
