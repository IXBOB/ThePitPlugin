package com.ixbob.thepit.util;

import com.ixbob.thepit.Main;
import com.ixbob.thepit.PlayerDataBlock;
import com.ixbob.thepit.enums.CustomBasicToolEnum;
import com.ixbob.thepit.enums.gui.talent.GUITalentItemEnum;
import com.ixbob.thepit.enums.gui.talent.TalentGivingItemEnum;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;

public class PlayerUtils {
    public static void setMostBasicKit(Player player, boolean clear) {
        PlayerInventory inventory = player.getInventory();
        PlayerDataBlock dataBlock = Main.getPlayerDataBlock(player);
        ArrayList<?> equippedTalentList = dataBlock.getEquippedNormalTalentList();
        if (clear) {
            inventory.clear();
            inventory.addItem(CustomBasicToolEnum.BASIC_STONE_SWORD.getItemStack());
            inventory.addItem(CustomBasicToolEnum.BASIC_BOW.getItemStack());
            if (equippedTalentList.contains(GUITalentItemEnum.FISHERMAN.getId())) {
                inventory.addItem(TalentGivingItemEnum.DEFAULT_FISHING_ROD.getItemStack());
            }
            if (equippedTalentList.contains(GUITalentItemEnum.SAFETY_FIRST.getId())) {
                inventory.setHelmet(TalentGivingItemEnum.DEFAULT_CHAINMAIL_HELMET.getItemStack());
            }
            if (equippedTalentList.contains(GUITalentItemEnum.MINER.getId())) {
                player.getInventory().addItem(TalentGivingItemEnum.DIAMOND_PICKAXE_EFFICIENCY_4.getItemStack());
                player.getInventory().addItem(TalentGivingItemEnum.DEFAULT_COBBLESTONE.getItemStack((int) TalentCalcuUtils.getAddPointValue(GUITalentItemEnum.MINER.getId(), dataBlock.getNormalTalentLevelList().get(GUITalentItemEnum.MINER.getId()))));
            }
            inventory.setChestplate(CustomBasicToolEnum.BASIC_CHAINMAIL_CHESTPLATE.getItemStack());
            inventory.setLeggings(CustomBasicToolEnum.BASIC_CHAINMAIL_LEGGINGS.getItemStack());
        }
        inventory.remove(Material.COOKED_BEEF);
        inventory.addItem(new ItemStack(Material.COOKED_BEEF, 64));
        inventory.remove(Material.ARROW);
        inventory.addItem(new ItemStack(Material.ARROW, 8));

    }
}
