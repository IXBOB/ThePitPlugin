package com.ixbob.thepit.gui;

import com.ixbob.thepit.*;
import com.ixbob.thepit.enums.GUIGridTypeEnum;
import com.ixbob.thepit.enums.GUIShopItemEnum;
import com.ixbob.thepit.enums.GUISystemItemEnum;
import com.ixbob.thepit.enums.ShopGivingItemEnum;
import com.ixbob.thepit.util.GUIUtils;
import com.ixbob.thepit.util.ShopUtils;
import com.ixbob.thepit.util.Utils;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;

public class GUIShop extends AbstractGUI {
    private static final int size = 54;
    private Inventory inventory;
    private Player player;

    public GUIShop(Player player) {
        super(player, size, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    public void initFrame(Player player) {
        this.inventory = Bukkit.createInventory(player, size, LangLoader.get("shop_gui_title"));
        this.player = player;
    }

    public void open() {
        player.openInventory(inventory);
    }

    public void initContent() {
        leftButton.clear();
        rightButton.clear();

        GUIUtils.fillAll(inventory, GUISystemItemEnum.DEFAULT_WALL.getItemStack());

        for (int index = 10; index < 10 + GUIShopItemEnum.values().length; index ++) {

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
            Utils.addCoin(player, -needCoinAmount);
            player.sendMessage(String.format(LangLoader.get("shop_purchase_success"), clickedShopItem.getDisplayName()));
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 2);
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
            }
            return;
        }
        //购买失败
        player.sendMessage(String.format(LangLoader.get("shop_purchase_fail_as_coin"), Mth.formatDecimalWithFloor(needCoinAmount - ownCoinAmount, 2)));
        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 1, 1);

    }
}
