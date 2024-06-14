package com.ixbob.thepit.event;

import com.ixbob.thepit.LangLoader;
import com.ixbob.thepit.Main;
import com.ixbob.thepit.PlayerDataBlock;
import com.ixbob.thepit.enums.DropEquipEnum;
import com.ixbob.thepit.enums.gui.talent.GUITalentItemEnum;
import com.ixbob.thepit.task.handler.RandomGoldIngotSpawnSingleTaskHandler;
import com.ixbob.thepit.util.PlayerUtils;
import com.ixbob.thepit.util.TalentCalcuUtils;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class OnPlayerPickupItemListener implements Listener {
    @EventHandler
    public void onEntityPickupItem(EntityPickupItemEvent event) {
        if (event.getEntity() instanceof Player player) {
            Item item = event.getItem();
            ItemStack itemStack = item.getItemStack();
            for (DropEquipEnum dropItem : DropEquipEnum.values()) {
                if (itemStack.getType() == dropItem.getItemStack().getType()) {
                    dropItem.apply(player);
                    item.remove();
                    event.setCancelled(true);
                    break;
                }
                else if (itemStack.getType() == Material.GOLD_INGOT) {
                    RandomGoldIngotSpawnSingleTaskHandler taskHandler = Main.getTaskManager().getRandomGoldIngotSpawnTaskHandler();
                    int amount = itemStack.getAmount();
                    taskHandler.setGoldIngotExistAmount(taskHandler.getGoldIngotExistAmount() - amount);
                    if (taskHandler.isPaused()) {
                        taskHandler.resume();
                    }
                    int addPoint = new Random().ints(amount, 1, 5).reduce(1, Integer::sum);

                    PlayerDataBlock dataBlock = Main.getPlayerDataBlock(player);
                    //涓流 天赋
                    if (dataBlock.getEquippedNormalTalentList().contains(GUITalentItemEnum.STREAM.getId())) {
                        addPoint += 10 * amount;
                        int id = GUITalentItemEnum.STREAM.getId();
                        int level = dataBlock.getNormalTalentLevelList().get(id);
                        double applyHealth = player.getHealth() + TalentCalcuUtils.getAddPointValue(id, level);
                        double maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
                        player.setHealth(Math.min(applyHealth, maxHealth));
                    }

                    pickupGoldIngot(player, addPoint);
                    item.remove();
                    event.setCancelled(true);
                    break;
                }
            }
        }
    }

    private void pickupGoldIngot(Player player, int addPoint) {
        PlayerUtils.addCoin(player, addPoint);
        player.sendMessage(String.format(LangLoader.getString("player_pickup_coin_message"), addPoint));
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 2);
    }
}
