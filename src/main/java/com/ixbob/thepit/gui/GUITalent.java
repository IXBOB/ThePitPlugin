package com.ixbob.thepit.gui;

import com.ixbob.thepit.*;
import com.ixbob.thepit.enums.GUIGridTypeEnum;
import com.ixbob.thepit.enums.TalentIdEnum;
import com.ixbob.thepit.enums.TalentItemsEnum;
import com.ixbob.thepit.LangLoader;
import com.ixbob.thepit.util.TalentUtils;
import com.ixbob.thepit.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
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
    private boolean movingState = false;
    private TalentItemsEnum movingTalentItem;

    public GUITalent(Player player) {
        super(player, size, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
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
        if (type == GUIGridTypeEnum.RIGHT_BUTTON) {
            if (inventory.getItem(index).getType() == TalentItemsEnum.HEALTH_BOOST.getMaterial()) {
                purchaseUpgrade(TalentItemsEnum.HEALTH_BOOST.getId(), TalentItemsEnum.HEALTH_BOOST);
            }
        } else if (type == GUIGridTypeEnum.LEFT_BUTTON) {
            if (!movingState && index >= 37 && index <= 43) {
                setEquipTalent(index, null, false);
                initContent();
            }

            if (!movingState && ((index >= 10 && index <= 16) || (index >= 19 && index <= 25))) {
                movingTalentItem = TalentItemsEnum.getById(TalentUtils.getTalentIdByInventoryIndex(index));
                movingState = true;
                initMovingContent(); //传入点击的index
            } else if (movingState) {
                if (index >= 37 && index <= 43) {
                    setEquipTalent(index, movingTalentItem, true);
                }
                movingState = false;
                initContent();
            }
        } else {
            System.out.println("this is invalid");
        }
    }

    public void setEquipTalent(int index, TalentItemsEnum talentItem, boolean isEquipped) {
        if (isEquipped) {
            Utils.addEquippedTalent(player, index, talentItem);
            player.sendMessage(String.format(LangLoader.get("talent_equip_success_message"), talentItem.getDisplayName(), TalentUtils.getEquipTalentIdByInventoryIndex(index) + 1)); //!!!  装备天赋显示的槽位比代码内槽位id + 1
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 1, 2);
        } else {
            Utils.removeEquippedTalent(player, index);
            player.sendMessage("not equip!!!");
        }

    }

    public void initContent() {
        PlayerDataBlock dataBlock = Main.getPlayerDataBlock(player);
        int playerLevel = dataBlock.getLevel();
        int playerPrestigeLevel = dataBlock.getPrestigeLevel();
        ArrayList<Integer> talentLevelList = dataBlock.getTalentLevelList();
        ArrayList<?> equippedTalentList = dataBlock.getEquippedTalentList();

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
        ItemStack hasEquipped = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 12);
        ItemMeta hasEquippedItemMeta = hasEquipped.getItemMeta();
        hasEquippedItemMeta.setDisplayName(LangLoader.get("talent_item_has_equipped"));
        hasEquipped.setItemMeta(hasEquippedItemMeta);
        for (int index = 10; index <= 16; index++) {
            inventory.setItem(index, locked);
            int talentId = TalentUtils.getTalentIdByInventoryIndex(index);
            if (!equippedTalentList.contains(talentId)) {
                if (talentId == 0) {
                    //TODO: 什么鸟代码，下面这个还有boolean返回值很烦，有空优化下。
                    if (TalentUtils.setTalentItem(inventory, TalentIdEnum.valueOf("ID_" + 0).getInventoryIndex(), TalentItemsEnum.HEALTH_BOOST, talentLevelList.get(0), false)) {
                        rightButton.add(Utils.getInventoryIndex(2,2));
                        leftButton.add(Utils.getInventoryIndex(2, 2));
                    }
                }
            }
            else {
                inventory.setItem(index, hasEquipped);
            }
        }
        for (int index = 19; index <= 25; index++) {
            inventory.setItem(index, locked);
        }

        ItemStack barrier = new ItemStack(Material.BARRIER);
        ItemMeta barrierItemMeta = barrier.getItemMeta();
        barrierItemMeta.setDisplayName(LangLoader.get("talent_equip_grid_locked"));
        barrier.setItemMeta(barrierItemMeta);
        ItemStack empty = new ItemStack(Material.AIR);
        for (int index = 37; index <= 43; index++) {
            int needLevel = (index - 36) * 15; //每15级解锁一个天赋槽位
            int needPrestigeLevel = 0;
            if (playerPrestigeLevel < needPrestigeLevel || (playerPrestigeLevel == needPrestigeLevel && playerLevel < needLevel)) {
                inventory.setItem(index, barrier);
            } else {
                if (dataBlock.getEquippedTalentList().get(TalentUtils.getEquipTalentIdByInventoryIndex(index)) != null) {
                    int talentId = (int) dataBlock.getEquippedTalentList().get(TalentUtils.getEquipTalentIdByInventoryIndex(index)); //获得已装备的talent id
                    TalentUtils.setTalentItem(inventory, index, TalentItemsEnum.getById(talentId), dataBlock.getTalentLevelList().get(talentId), true);
                    leftButton.add(index);
                    rightButton.add(index);
                } else {
                    inventory.setItem(index, empty);
                }
            }
        }
    }

    public void initMovingContent() {
        ItemStack dropButton = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
        ItemMeta dropItemMeta = dropButton.getItemMeta();
        dropItemMeta.setDisplayName(LangLoader.get("talent_system_item_drop_name"));
        dropButton.setItemMeta(dropItemMeta);
        for (int index = 10; index <= 16; index++) {
            inventory.setItem(index, dropButton);
        }
        for (int index = 19; index <= 25; index++) {
            inventory.setItem(index, dropButton);
        }

        ItemStack equipButton = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5);
        ItemMeta equipItemMeta = equipButton.getItemMeta();
        equipItemMeta.setDisplayName(LangLoader.get("talent_system_item_equip_name"));
        equipButton.setItemMeta(equipItemMeta);
        for (int index = 37; index <= 43; index++) {
            if (inventory.getItem(index) == null) {
                inventory.setItem(index, equipButton);
            }
        }
    }

    public void purchaseUpgrade(int id, TalentItemsEnum upgradeTalentItemType) {
        PlayerDataBlock playerDataBlock = Main.getPlayerDataBlock(player);
        ArrayList<Integer> talentLevelList = playerDataBlock.getTalentLevelList();
        int currentLevel = talentLevelList.get(id);
        int ownCoinAmount = playerDataBlock.getCoinAmount();
        int needCoinAmount = TalentUtils.getNextLevelNeedCoinAmount(upgradeTalentItemType, currentLevel);
        if (ownCoinAmount >= needCoinAmount) {
            Utils.setTalentLevel(player, id, currentLevel + 1);
            TalentUtils.setTalentItem(inventory, TalentIdEnum.valueOf("ID_" + id).getInventoryIndex(), upgradeTalentItemType, talentLevelList.get(id), false);
            Utils.addCoin(player, -needCoinAmount);
        }
    }
}
