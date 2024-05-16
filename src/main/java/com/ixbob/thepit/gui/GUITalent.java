package com.ixbob.thepit.gui;

import com.ixbob.thepit.AbstractGUI;
import com.ixbob.thepit.Main;
import com.ixbob.thepit.PlayerDataBlock;
import com.ixbob.thepit.TalentItemsEnum;
import com.ixbob.thepit.handler.config.LangLoader;
import com.ixbob.thepit.util.TalentUtils;
import com.ixbob.thepit.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class GUITalent extends AbstractGUI {
    private static final int size = 54;
    private static final int[] button = new int[]{10};
    private static final int[] moveable = new int[]{Utils.getInventoryIndex(5,2)};
    private Inventory inventory;
    private Player player;

    public GUITalent(Player player) {
        super(player, size, button, moveable);
    }

    public void init(Player player) {
        this.inventory = Bukkit.createInventory(player, size, LangLoader.get("talent_gui_title"));
        this.player = player;
    }

    public void open() {
        player.openInventory(inventory);
    }

    public int[] getButtonList() {
        return button;
    }

    public void initContent() {
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

        ItemStack empty = new ItemStack(Material.AIR);
        for (int index = 37; index <= 43; index++) {
            inventory.setItem(index, empty);
        }



        PlayerDataBlock dataBlock = Main.getPlayerDataBlock(player);
        ArrayList<Integer> talentLevelList = dataBlock.getTalentLevelList();
        ArrayList<?> equippedTalentList = dataBlock.getEquippedTalentList();

        TalentUtils.setTalentItem(inventory, Utils.getInventoryIndex(2, 2), TalentItemsEnum.HEALTH_BOOST, talentLevelList.get(0));
    }
}
