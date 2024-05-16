package com.ixbob.thepit.gui;

import com.ixbob.thepit.*;
import com.ixbob.thepit.enums.GUIGridTypeEnum;
import com.ixbob.thepit.enums.TalentItemsEnum;
import com.ixbob.thepit.LangLoader;
import com.ixbob.thepit.util.TalentUtils;
import com.ixbob.thepit.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class GUITalent extends AbstractGUI {
    private static final int size = 54;
    private Inventory inventory;
    private Player player;

    public GUITalent(Player player) {
        super(player, size,
                new ArrayList<>(), //rightButton
                new ArrayList<>()); //leftMoveable
    }

    public void initFrame(Player player) {
        this.inventory = Bukkit.createInventory(player, size, LangLoader.get("talent_gui_title"));
        this.player = player;
    }

    public void open() {
        player.openInventory(inventory);
    }

    @Override
    public void onClick(int index, ClickType clickType) {
        GUIGridTypeEnum type = getGridType(index, clickType);
        if (type == GUIGridTypeEnum.BUTTON) {
            System.out.println("this is a button");
        } else if (type == GUIGridTypeEnum.MOVEABLE) {
            System.out.println("this is a moveable");
        } else {
            System.out.println("this is invalid");
        }
    }

    public void initContent() {
        PlayerDataBlock playerDataBlock = Main.getPlayerDataBlock(player);
        int playerLevel = playerDataBlock.getLevel();
        int playerPrestigeLevel = playerDataBlock.getPrestigeLevel();

        ItemStack emptyWall = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);
        ItemMeta emptyItemMeta = emptyWall.getItemMeta();
        emptyItemMeta.setDisplayName(" ");
        emptyWall.setItemMeta(emptyItemMeta);
        for (int index = 0; index < size; index++) {
            inventory.setItem(index, new ItemStack(emptyWall));
        }

        ItemStack locked = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
        ItemMeta lockedItemMeta = locked.getItemMeta();
        lockedItemMeta.setDisplayName(LangLoader.get("talent_item_locked"));
        locked.setItemMeta(lockedItemMeta);
        for (int index = 10; index <= 16; index++) {
            inventory.setItem(index, locked);
        }
        for (int index = 19; index <= 25; index++) {
            inventory.setItem(index, locked);
        }

        ItemStack barrier = new ItemStack(Material.BARRIER);
        ItemStack empty = new ItemStack(Material.AIR);
        for (int index = 37; index <= 43; index++) {
            int needLevel = (index - 36) * 15; //每15级解锁一个天赋槽位
            int needPrestigeLevel = 0;
            if (playerPrestigeLevel < needPrestigeLevel || (playerPrestigeLevel == needPrestigeLevel && playerLevel < needLevel)) {
                inventory.setItem(index, barrier);
            } else {
                inventory.setItem(index, empty);
                leftMoveable.add(index);
            }
        }

        PlayerDataBlock dataBlock = Main.getPlayerDataBlock(player);
        ArrayList<Integer> talentLevelList = dataBlock.getTalentLevelList();
        ArrayList<?> equippedTalentList = dataBlock.getEquippedTalentList();

        TalentUtils.setTalentItem(inventory, Utils.getInventoryIndex(2, 2), TalentItemsEnum.HEALTH_BOOST, talentLevelList.get(0));
        rightButton.add(Utils.getInventoryIndex(2,2));
        leftMoveable.add(Utils.getInventoryIndex(2, 2));
    }
}
