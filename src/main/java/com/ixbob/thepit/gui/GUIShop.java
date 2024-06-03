package com.ixbob.thepit.gui;

import com.ixbob.thepit.LangLoader;
import com.ixbob.thepit.Main;
import com.ixbob.thepit.Mth;
import com.ixbob.thepit.PlayerDataBlock;
import com.ixbob.thepit.enums.gui.GUIGridTypeEnum;
import com.ixbob.thepit.enums.gui.GUISystemItemEnum;
import com.ixbob.thepit.enums.gui.shop.GUIShopItemEnum;
import com.ixbob.thepit.enums.gui.shop.ShopGivingItemEnum;
import com.ixbob.thepit.util.GUIUtils;
import com.ixbob.thepit.util.PlayerUtils;
import com.ixbob.thepit.util.ShopUtils;
import lombok.NonNull;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.ArrayList;

public class GUIShop extends AbstractGUI {

    public GUIShop(Player player) {
        super(player, 54, 0, 0, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    @Override
    public void initContent() {
        leftButton.clear();
        rightButton.clear();

        GUIUtils.fillAll(inventory, GUISystemItemEnum.DEFAULT_WALL.getItemStack());

        for (int index = 10; index < 17; index ++) {

            inventory.setItem(index, GUISystemItemEnum.DEFAULT_EMPTY.getItemStack());

            int id = ShopUtils.getShopItemIdByInventoryIndex(index);
            GUIShopItemEnum shopItem = ShopUtils.getGUIShopItemEnumById(id);
            if (shopItem == null) {
                continue;
            }

            inventory.setItem(index, shopItem.getNamedItem());
            leftButton.add(index);
        }

    }

    @Override
    public void onClick(int index, ClickType clickType) {
        GUIGridTypeEnum type = getGridType(index, clickType);
        if (type == GUIGridTypeEnum.LEFT_BUTTON) {
            int id = ShopUtils.getShopItemIdByInventoryIndex(index);
            GUIShopItemEnum clickedShopItem = ShopUtils.getGUIShopItemEnumById(id);
            purchaseShop(clickedShopItem);
        }
    }

    public void purchaseShop(@NonNull GUIShopItemEnum clickedShopItem) {
        PlayerDataBlock playerDataBlock = Main.getPlayerDataBlock(player);
        double ownCoinAmount = playerDataBlock.getCoinAmount();
        double needCoinAmount = clickedShopItem.getNeedCoinAmount();
        if (ownCoinAmount >= needCoinAmount) {
            //购买成功
            PlayerUtils.addCoin(player, -needCoinAmount);
            player.sendMessage(String.format(LangLoader.get("shop_purchase_success"), clickedShopItem.getDisplayName()));
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 1, 2);
            switch (clickedShopItem) {
                case DIAMOND_HELMET: {
                    player.getInventory().addItem(ShopGivingItemEnum.DIAMOND_HELMET.getItemStack());
                    break;
                }
                case DIAMOND_CHESTPLATE: {
                    player.getInventory().addItem(ShopGivingItemEnum.DIAMOND_CHESTPLATE.getItemStack());
                    break;
                }
                case DIAMOND_LEGGINGS: {
                    player.getInventory().addItem(ShopGivingItemEnum.DIAMOND_LEGGINGS.getItemStack());
                    break;
                }
                case DIAMOND_BOOTS: {
                    player.getInventory().addItem(ShopGivingItemEnum.DIAMOND_BOOTS.getItemStack());
                    break;
                }
                case OBSIDIAN: {
                    player.getInventory().addItem(ShopGivingItemEnum.OBSIDIAN.getItemStack());
                    break;
                }
                case DIAMOND_SWORD: {
                    player.getInventory().addItem(ShopGivingItemEnum.DIAMOND_SWORD.getItemStack());
                    break;
                }
            }
            return;
        }
        //购买失败
        player.sendMessage(String.format(LangLoader.get("shop_purchase_fail_as_coin"), Mth.formatDecimalWithFloor(needCoinAmount - ownCoinAmount, 1)));
        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 1, 1);

    }
}
