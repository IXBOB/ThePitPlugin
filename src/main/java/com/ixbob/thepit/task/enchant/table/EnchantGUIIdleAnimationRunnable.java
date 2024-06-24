package com.ixbob.thepit.task.enchant.table;

import com.ixbob.thepit.enums.gui.GUISystemItemEnum;
import com.ixbob.thepit.enums.gui.enchant.FunctionalIndexes;
import com.ixbob.thepit.gui.GUIEnchant;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class EnchantGUIIdleAnimationRunnable implements Runnable{

    /**  index:
     *   ###  123
     *   # #  8 4
     *   ###  765
     */

    private int animatingIndex = 1;
    private int animatingInvIndex;
    public static final int idleAnimationTime = 16; //tick
    private final GUIEnchant gui;
    private Inventory inventory;
    private final ItemStack idleNotAnimatingItem = GUISystemItemEnum.DEFAULT_WALL_WHITE.getItemStack();
    private final ItemStack idleAnimatingItem = GUISystemItemEnum.DEFAULT_WALL_ORANGE.getItemStack();

    public EnchantGUIIdleAnimationRunnable(GUIEnchant guiEnchant) {
        this.gui = guiEnchant;
        this.inventory = gui.getInventory();
    }

    @Override
    public void run() {
        inventory.setItem(getInvIndex(animatingIndex), idleNotAnimatingItem);
        animatingIndex++;
        if (animatingIndex > 8) {
            animatingIndex = 1;
        }
        inventory.setItem(getInvIndex(animatingIndex), idleAnimatingItem);
    }

    private int getInvIndex(int animatingIndex) {
        return FunctionalIndexes.valueOf("INDEX_" + animatingIndex).getInvIndex();
    }
}
