package com.ixbob.thepit.event;

import com.ixbob.thepit.Main;
import com.ixbob.thepit.PlayerDataBlock;
import com.ixbob.thepit.enums.gui.talent.TalentGivingItemEnum;
import com.ixbob.thepit.enums.gui.talent.GUITalentItemEnum;
import com.ixbob.thepit.event.custom.PlayerEquippedTalentChangeEvent;
import com.ixbob.thepit.util.TalentCalcuUtils;
import com.ixbob.thepit.util.TalentUtils;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class OnPlayerEquippedTalentChangeListener implements Listener {
    private Player player;
    private int equipToIndex;
    private GUITalentItemEnum talentItem;
    private boolean isEquipped;
    private PlayerDataBlock dataBlock;
    @EventHandler
    public void onPlayerEquippedTalentChange(PlayerEquippedTalentChangeEvent event) {
        isEquipped = event.getIsEquipped();
        player = event.getPlayer();
        equipToIndex = event.getEquipToIndex();
        talentItem = event.getTalentItem();
        dataBlock = Main.getPlayerDataBlock(player);

        updateDataBlock(dataBlock, TalentUtils.getEquipGridIdByInventoryIndex(equipToIndex), talentItem != null ? talentItem.getId() : null , isEquipped);
        dataBlock.updatePlayerDBData();
        applyEquipChangeResult();
    }

    private void updateDataBlock(PlayerDataBlock dataBlock, int equipGridId, Integer talentId, boolean isEquipped) {  //Integer类型可传入null
        if (isEquipped) {
            dataBlock.setEquippedTalent(equipGridId, talentId);
        } else {
            dataBlock.removeEquippedTalent(equipGridId);
        }
    }

    private void applyEquipChangeResult() {
        int id = talentItem.getId();
        int level = dataBlock.getTalentLevelList().get(id);
        switch (talentItem) {
            case HEALTH_BOOST: {
                if (isEquipped) {
                    float maxHealth = 20 + talentItem.getAddPointValue(level);
                    player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth);
                    player.setHealth(maxHealth);
                } else {
                    player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
                    player.setHealth(20);
                }
                break;
            }
            case GOLDEN_CHOCOLATE: {
                break;
            }
            case FISHERMAN: {
                if (isEquipped) {
                    player.getInventory().addItem(TalentGivingItemEnum.DEFAULT_FISHING_ROD.getItemStack());
                } else {
                    player.getInventory().remove(TalentGivingItemEnum.DEFAULT_FISHING_ROD.getItemStack());
                }
                break;
            }
            case SAFETY_FIRST: {
                if (isEquipped) {
                    player.getInventory().setHelmet(TalentGivingItemEnum.DEFAULT_CHAINMAIL_HELMET.getItemStack());
                } else {
                    player.getInventory().setHelmet(new ItemStack(Material.AIR));
                    player.getInventory().remove(TalentGivingItemEnum.DEFAULT_CHAINMAIL_HELMET.getItemStack());
                }
                break;
            }
            case MINER: {
                if (isEquipped) {
                    id = GUITalentItemEnum.MINER.getId();
                    player.getInventory().addItem(TalentGivingItemEnum.DIAMOND_PICKAXE_EFFICIENCY_4.getItemStack());
                    player.getInventory().addItem(TalentGivingItemEnum.DEFAULT_COBBLESTONE.getItemStack((int) TalentCalcuUtils.getAddPointValue(id, dataBlock.getTalentLevelList().get(GUITalentItemEnum.MINER.getId()))));
                } else {
                    player.getInventory().remove(TalentGivingItemEnum.DIAMOND_PICKAXE_EFFICIENCY_4.getItemStack());
                    player.getInventory().remove(TalentGivingItemEnum.DEFAULT_COBBLESTONE.getItemStack().getType());
                }
            }
        }
    }
}
