package com.ixbob.thepit.gui;

import com.ixbob.thepit.*;
import com.ixbob.thepit.enums.GUIGridTypeEnum;
import com.ixbob.thepit.enums.TalentItemsEnum;
import com.ixbob.thepit.util.TalentUtils;
import com.ixbob.thepit.util.Utils;
import lombok.NonNull;
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
        if (type == GUIGridTypeEnum.RIGHT_BUTTON) {  //升级天赋
            if ((index >= 10 && index <= 16) || (index >= 19 && index <= 25)) {
                int talentItemId = TalentUtils.getNotEquippedTalentIdByInventoryIndex(index);
                TalentItemsEnum clickedTalentItem = TalentUtils.getTalentItemEnumById(talentItemId);
                purchaseUpgrade(index, talentItemId, clickedTalentItem);
            } else if (index >= 37 && index <= 43) {
                int talentItemId = (int) Main.getPlayerDataBlock(player).getEquippedTalentList().get(TalentUtils.getEquipGridIdByInventoryIndex(index));
                TalentItemsEnum clickedTalentItem = TalentUtils.getTalentItemEnumById(talentItemId);
                purchaseUpgrade(index, talentItemId, clickedTalentItem);
            }

        } else if (type == GUIGridTypeEnum.LEFT_BUTTON) {
            if (!movingState && index >= 37 && index <= 43) {   //移除天赋
                PlayerDataBlock dataBlock = Main.getPlayerDataBlock(player);
                ArrayList<?> equippedTalentList = dataBlock.getEquippedTalentList();
                TalentItemsEnum clickTalentItem = TalentUtils.getTalentItemEnumById((int) equippedTalentList.get(TalentUtils.getEquipGridIdByInventoryIndex(index)));
                setEquipTalent(index, clickTalentItem, false);
                initContent();
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_HARP, 1, 1);
            }

            if (!movingState && ((index >= 10 && index <= 16) || (index >= 19 && index <= 25))) {  //装备天赋
                movingTalentItem = TalentUtils.getTalentItemEnumById(TalentUtils.getNotEquippedTalentIdByInventoryIndex(index));
                movingState = true;
                initMovingContent(); //传入点击的index
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_HARP, 1, 1);
            } else if (movingState) {
                if (index >= 37 && index <= 43) {
                    setEquipTalent(index, movingTalentItem, true);
                }
                movingState = false;
                initContent();
            }
        } else {
            System.out.println("this is invalid");
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_HAT, 1, 1);
        }
    }

    public void setEquipTalent(int index, TalentItemsEnum talentItem, boolean isEquipped) {
        Utils.changeEquippedTalent(player, index, talentItem, isEquipped);
        if (isEquipped) {
            player.sendMessage(String.format(LangLoader.get("talent_equip_success_message"), talentItem.getDisplayName(), TalentUtils.getEquipGridIdByInventoryIndex(index) + 1)); //!!!  装备天赋显示的槽位比代码内槽位id + 1
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 1, 2);
        } else {
            player.sendMessage(String.format(LangLoader.get("talent_drop_success_message"), talentItem.getDisplayName()));
        }

    }

    public void initContent() {
        leftButton.clear();
        rightButton.clear();

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

        ItemStack locked = new ItemStack(Material.BEDROCK);
        ItemMeta lockedItemMeta = locked.getItemMeta();
        lockedItemMeta.setDisplayName(LangLoader.get("talent_item_locked"));
        locked.setItemMeta(lockedItemMeta);
        ItemStack hasEquipped = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 12);
        ItemMeta hasEquippedItemMeta = hasEquipped.getItemMeta();
        hasEquippedItemMeta.setDisplayName(LangLoader.get("talent_item_has_equipped"));
        hasEquipped.setItemMeta(hasEquippedItemMeta);
        for (int index = 10; index <= 25; index++) {
            if (index == 17 || index == 18 ) {
                continue;
            }

            inventory.setItem(index, locked);

            int talentId = TalentUtils.getNotEquippedTalentIdByInventoryIndex(index);
            TalentItemsEnum talentItem = TalentUtils.getTalentItemEnumById(talentId);
            if (talentItem == null) {
                continue;
            }
            if (!equippedTalentList.contains(talentId)) {
                if (TalentUtils.setTalentItem(inventory, index, talentItem, talentLevelList.get(talentId), false, talentLevelList.get(talentId) >= talentItem.getMaxTalentLevel())) {
                    rightButton.add(talentItem.getIndex());
                    leftButton.add(talentItem.getIndex());
                }
            }
            else {
                inventory.setItem(index, hasEquipped);
            }
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
                if (dataBlock.getEquippedTalentList().get(TalentUtils.getEquipGridIdByInventoryIndex(index)) != null) {
                    int talentId = (int) dataBlock.getEquippedTalentList().get(TalentUtils.getEquipGridIdByInventoryIndex(index)); //获得已装备的talent id
                    TalentItemsEnum talentItemsEnum = TalentUtils.getTalentItemEnumById(talentId);
                    if (talentItemsEnum == null) { //id不存在
                        continue;
                    }
                    TalentUtils.setTalentItem(inventory, index, talentItemsEnum, dataBlock.getTalentLevelList().get(talentId), true, talentLevelList.get(talentId) >= talentItemsEnum.getMaxTalentLevel());
                    leftButton.add(index);
                    rightButton.add(index);
                } else {
                    inventory.setItem(index, empty);
                }
            }
        }
    }

    public void initMovingContent() {
        leftButton.clear();
        rightButton.clear();

        ItemStack dropButton = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
        ItemMeta dropItemMeta = dropButton.getItemMeta();
        dropItemMeta.setDisplayName(LangLoader.get("talent_system_item_drop_name"));
        dropButton.setItemMeta(dropItemMeta);
        for (int index = 10; index <= 16; index++) {
            inventory.setItem(index, dropButton);
            leftButton.add(index);
        }
        for (int index = 19; index <= 25; index++) {
            inventory.setItem(index, dropButton);
            leftButton.add(index);
        }

        ItemStack equipButton = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5);
        ItemMeta equipItemMeta = equipButton.getItemMeta();
        equipItemMeta.setDisplayName(LangLoader.get("talent_system_item_equip_name"));
        equipButton.setItemMeta(equipItemMeta);
        for (int index = 37; index <= 43; index++) {
            if (inventory.getItem(index) == null) {
                inventory.setItem(index, equipButton);
                leftButton.add(index);
                rightButton.add(index);
            }
        }
    }

    public void purchaseUpgrade(int clickIndex, int id, @NonNull TalentItemsEnum upgradeTalentItemType) {
        PlayerDataBlock playerDataBlock = Main.getPlayerDataBlock(player);
        ArrayList<Integer> talentLevelList = playerDataBlock.getTalentLevelList();
        int currentTalentLevel = talentLevelList.get(id);
        int maxTalentLevel = upgradeTalentItemType.getMaxTalentLevel();
        double ownCoinAmount = playerDataBlock.getCoinAmount();
        double needCoinAmount = TalentUtils.getNextLevelNeedCoinAmount(upgradeTalentItemType, currentTalentLevel);

        if (currentTalentLevel >= maxTalentLevel) {
            player.sendMessage(LangLoader.get("talent_upgrade_failed_as_already_level_max"));
            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 1, 1);
            return;
        }

        if (ownCoinAmount >= needCoinAmount) {
            int newLevel = currentTalentLevel + 1;
            Utils.setTalentLevel(player, id, newLevel);
            boolean equipped = clickIndex >= 37 && clickIndex <= 43;
            if (equipped) { //升级时，如果升级的物品已装备，那么执行更改装备的天赋即Utils.changeEquippedTalent。如果未装备，直接TalentUtils.setTalentItem即可。
                Utils.changeEquippedTalent(player, clickIndex, upgradeTalentItemType, true);
                TalentUtils.setTalentItem(inventory, clickIndex, upgradeTalentItemType, talentLevelList.get(id), true, talentLevelList.get(id) >= upgradeTalentItemType.getMaxTalentLevel());
            } else {
                TalentUtils.setTalentItem(inventory, clickIndex, upgradeTalentItemType, talentLevelList.get(id), false, talentLevelList.get(id) >= upgradeTalentItemType.getMaxTalentLevel());
            }
            Utils.addCoin(player, -needCoinAmount);
            player.sendMessage(String.format(LangLoader.get("talent_upgrade_success_message"), upgradeTalentItemType.getDisplayName(), newLevel));
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 2);
            return;
        }
        player.sendMessage(String.format(LangLoader.get("talent_upgrade_failed_as_coin"), Mth.formatDecimalWithFloor(needCoinAmount - ownCoinAmount, 1)));
        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 1, 1);
    }
}
