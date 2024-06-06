package com.ixbob.thepit.gui;

import com.ixbob.thepit.LangLoader;
import com.ixbob.thepit.Main;
import com.ixbob.thepit.Mth;
import com.ixbob.thepit.PlayerDataBlock;
import com.ixbob.thepit.enums.gui.GUIGridTypeEnum;
import com.ixbob.thepit.enums.gui.GUISystemItemEnum;
import com.ixbob.thepit.enums.gui.talent.GUITalentItemEnum;
import com.ixbob.thepit.util.GUIUtils;
import com.ixbob.thepit.util.PlayerUtils;
import com.ixbob.thepit.util.TalentUtils;
import com.ixbob.thepit.util.Utils;
import lombok.NonNull;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;

public class GUITalent extends BasicGUIImpl {
    private boolean movingState = false;
    private GUITalentItemEnum movingTalentItem;
    public static final int size = 54;
    public static final int needLevel = 10;
    public static final int needPrestigeLevel = 0;

    public GUITalent(Player player) {
        super(player, size, needLevel, needPrestigeLevel, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    @Override
    public void initContent() {
        leftButton.clear();
        rightButton.clear();

        PlayerDataBlock dataBlock = Main.getPlayerDataBlock(player);
        int playerLevel = dataBlock.getLevel();
        int playerPrestigeLevel = dataBlock.getPrestigeLevel();
        ArrayList<Integer> talentLevelList = dataBlock.getNormalTalentLevelList();
        ArrayList<?> equippedTalentList = dataBlock.getEquippedNormalTalentList();

        GUIUtils.fillAll(inventory, GUISystemItemEnum.DEFAULT_WALL.getItemStack());

        ItemStack locked = GUISystemItemEnum.TALENT_WALL_LOCKED.getItemStack();
        ItemStack equippedItem = GUISystemItemEnum.TALENT_WALL_ALREADY_EQUIPPED.getItemStack();
        for (int index = 10; index <= 25; index++) {
            if (index == 17 || index == 18 ) {
                continue;
            }

            inventory.setItem(index, locked);

            int talentId = TalentUtils.getNotEquippedTalentIdByInventoryIndex(index);
            GUITalentItemEnum talentItem = TalentUtils.getGUITalentItemEnumById(talentId);
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
                inventory.setItem(index, equippedItem);
            }
        }

        ItemStack empty = new ItemStack(Material.AIR);
        for (int index = 39; index <= 41; index++) {
            int needLevel = 10 + (index - 39) * 20; //每15级解锁一个天赋槽位
            int needPrestigeLevel = 0;
            if (playerPrestigeLevel < needPrestigeLevel || (playerPrestigeLevel == needPrestigeLevel && playerLevel < needLevel)) {
                ItemStack lockedItem = GUISystemItemEnum.TALENT_WALL_EQUIP_GRID_LOCKED.getItemStack(new ArrayList<>(Arrays.asList(Utils.getLevelStrWithStyle(0, needLevel))));
                inventory.setItem(index, lockedItem);
            } else {
                if (dataBlock.getEquippedNormalTalentList().get(TalentUtils.getEquipGridIdByInventoryIndex(index)) != null) {
                    int talentId = (int) dataBlock.getEquippedNormalTalentList().get(TalentUtils.getEquipGridIdByInventoryIndex(index)); //获得已装备的talent id
                    GUITalentItemEnum GUITalentItemEnum = TalentUtils.getGUITalentItemEnumById(talentId);
                    if (GUITalentItemEnum == null) { //id不存在
                        continue;
                    }
                    TalentUtils.setTalentItem(inventory, index, GUITalentItemEnum, dataBlock.getNormalTalentLevelList().get(talentId), true, talentLevelList.get(talentId) >= GUITalentItemEnum.getMaxTalentLevel());
                    leftButton.add(index);
                    rightButton.add(index);
                } else {
                    inventory.setItem(index, empty);
                }
            }
        }
    }

    @Override
    public void onClick(int index, ClickType clickType) {
        GUIGridTypeEnum type = getGridType(index, clickType);
        if (type == GUIGridTypeEnum.RIGHT_BUTTON) {
            //升级天赋
            if ((index >= 10 && index <= 16) || (index >= 19 && index <= 25)) {
                int talentItemId = TalentUtils.getNotEquippedTalentIdByInventoryIndex(index);
                GUITalentItemEnum clickedTalentItem = TalentUtils.getGUITalentItemEnumById(talentItemId);
                purchaseUpgrade(index, talentItemId, clickedTalentItem);
            } else if (index >= 39 && index <= 41) {
                int talentItemId = (int) Main.getPlayerDataBlock(player).getEquippedNormalTalentList().get(TalentUtils.getEquipGridIdByInventoryIndex(index));
                GUITalentItemEnum clickedTalentItem = TalentUtils.getGUITalentItemEnumById(talentItemId);
                purchaseUpgrade(index, talentItemId, clickedTalentItem);
            }

        } else if (type == GUIGridTypeEnum.LEFT_BUTTON) {
            if (!movingState && index >= 39 && index <= 41) {
                //移除天赋
                PlayerDataBlock dataBlock = Main.getPlayerDataBlock(player);
                ArrayList<?> equippedTalentList = dataBlock.getEquippedNormalTalentList();
                GUITalentItemEnum clickTalentItem = TalentUtils.getGUITalentItemEnumById((int) equippedTalentList.get(TalentUtils.getEquipGridIdByInventoryIndex(index)));
                setEquipTalent(index, clickTalentItem, false);
                initContent();
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1, 1);
            }

            if (!movingState && ((index >= 10 && index <= 16) || (index >= 19 && index <= 25))) {
                //进入装备天赋装填
                movingTalentItem = TalentUtils.getGUITalentItemEnumById(TalentUtils.getNotEquippedTalentIdByInventoryIndex(index));
                movingState = true;
                initMovingContent(); //传入点击的index
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1, 1);
            } else if (movingState) {
                //装备天赋
                if (index >= 39 && index <= 41) {
                    setEquipTalent(index, movingTalentItem, true);
                }
                movingState = false;
                initContent();
            }
        } else {
            System.out.println("this is invalid");
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 1);
        }
    }

    public void setEquipTalent(int index, GUITalentItemEnum talentItem, boolean isEquipped) {
        PlayerUtils.changeEquippedTalent(player, index, talentItem, isEquipped);
        if (isEquipped) {
            player.sendMessage(String.format(LangLoader.getString("talent_equip_success_message"), talentItem.getDisplayName(), TalentUtils.getEquipGridIdByInventoryIndex(index) + 1)); //!!!  装备天赋显示的槽位比代码内槽位id + 1
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 2);
        } else {
            player.sendMessage(String.format(LangLoader.getString("talent_drop_success_message"), talentItem.getDisplayName()));
        }

    }

    public void initMovingContent() {
        leftButton.clear();
        rightButton.clear();

        ItemStack dropButton = GUISystemItemEnum.TALENT_BUTTON_CANCEL.getItemStack();
        for (int index = 10; index <= 16; index++) {
            inventory.setItem(index, dropButton);
            leftButton.add(index);
        }
        for (int index = 19; index <= 25; index++) {
            inventory.setItem(index, dropButton);
            leftButton.add(index);
        }

        ItemStack equipButton = GUISystemItemEnum.TALENT_BUTTON_APPLY.getItemStack();
        for (int index = 39; index <= 41; index++) {
            if (inventory.getItem(index) == null) {
                inventory.setItem(index, equipButton);
                leftButton.add(index);
                rightButton.add(index);
            }
        }
    }

    public void purchaseUpgrade(int clickIndex, int id, @NonNull GUITalentItemEnum upgradeTalentItemType) {
        PlayerDataBlock playerDataBlock = Main.getPlayerDataBlock(player);
        ArrayList<Integer> talentLevelList = playerDataBlock.getNormalTalentLevelList();
        int currentTalentLevel = talentLevelList.get(id);
        int maxTalentLevel = upgradeTalentItemType.getMaxTalentLevel();
        double ownCoinAmount = playerDataBlock.getCoinAmount();
        double needCoinAmount = TalentUtils.getNextLevelNeedCoinAmount(upgradeTalentItemType, currentTalentLevel);

        if (currentTalentLevel >= maxTalentLevel) {
            player.sendMessage(LangLoader.getString("talent_upgrade_failed_as_already_level_max_message"));
            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
            return;
        }

        if (ownCoinAmount >= needCoinAmount) {
            //购买成功
            int newLevel = currentTalentLevel + 1;
            PlayerUtils.setTalentLevel(player, id, newLevel);
            boolean equipped = clickIndex >= 39 && clickIndex <= 41;
            if (equipped) { //升级时，如果升级的物品已装备，那么执行更改装备的天赋即Utils.changeEquippedTalent。如果未装备，直接TalentUtils.setTalentItem即可。
                PlayerUtils.changeEquippedTalent(player, clickIndex, upgradeTalentItemType, true);
                TalentUtils.setTalentItem(inventory, clickIndex, upgradeTalentItemType, talentLevelList.get(id), true, talentLevelList.get(id) >= upgradeTalentItemType.getMaxTalentLevel());
            } else {
                TalentUtils.setTalentItem(inventory, clickIndex, upgradeTalentItemType, talentLevelList.get(id), false, talentLevelList.get(id) >= upgradeTalentItemType.getMaxTalentLevel());
            }
            PlayerUtils.addCoin(player, -needCoinAmount);
            player.sendMessage(String.format(LangLoader.getString("talent_upgrade_success_message"), upgradeTalentItemType.getDisplayName(), newLevel));
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 2);
            return;
        }
        //购买失败
        player.sendMessage(String.format(LangLoader.getString("talent_upgrade_failed_as_coin_message"), Mth.formatDecimalWithFloor(needCoinAmount - ownCoinAmount, 1)));
        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
    }


}
