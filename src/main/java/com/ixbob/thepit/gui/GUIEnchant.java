package com.ixbob.thepit.gui;

import com.ixbob.thepit.Main;
import com.ixbob.thepit.enums.gui.GUISystemItemEnum;
import com.ixbob.thepit.task.enchant.table.EnchantGUIIdleAnimationRunnable;
import com.ixbob.thepit.util.GUIUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class GUIEnchant extends BasicGUIImpl{
    private EnchantGUIIdleAnimationRunnable idleAnimationRunnable;
    private int taskId_idleAnimationRunnable;

    public GUIEnchant(Player player) {
        super(player, 45, 30, 0, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    @Override
    public void initContent() {
        leftButton.clear();
        rightButton.clear();

        GUIUtils.fillAll(inventory, GUISystemItemEnum.DEFAULT_WALL_GRAY.getItemStack());
        GUIUtils.fillArea(inventory, GUISystemItemEnum.DEFAULT_WALL_WHITE.getItemStack(), GUIUtils.getInvIndex(2,2), GUIUtils.getInvIndex(4,4));
        inventory.setItem(GUIUtils.getInvIndex(3,3), new ItemStack(Material.AIR));

        idleAnimationRunnable = new EnchantGUIIdleAnimationRunnable(this);
        taskId_idleAnimationRunnable = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), idleAnimationRunnable, 0, EnchantGUIIdleAnimationRunnable.idleAnimationTime);

    }

    @Override
    public void onClose() {
        super.onClose();
        Bukkit.getScheduler().cancelTask(taskId_idleAnimationRunnable);
    }

    @Override
    public void onClick(int index, ClickType clickType) {
        super.onClick(index, clickType);
    }
}
