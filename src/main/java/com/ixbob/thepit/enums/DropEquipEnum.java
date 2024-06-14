package com.ixbob.thepit.enums;

import com.ixbob.thepit.LangLoader;
import com.ixbob.thepit.Main;
import com.ixbob.thepit.PlayerDataBlock;
import com.ixbob.thepit.enums.gui.talent.GUITalentItemEnum;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

public enum DropEquipEnum {
    IRON_HELMET(new ItemStack(Material.IRON_HELMET)),
    IRON_CHESTPLATE(new ItemStack(Material.IRON_CHESTPLATE)),
    IRON_LEGGINGS(new ItemStack(Material.IRON_LEGGINGS)),
    IRON_BOOTS(new ItemStack(Material.IRON_BOOTS)),
    DIAMOND_HELMET(new ItemStack(Material.DIAMOND_HELMET)),
    DIAMOND_CHESTPLATE(new ItemStack(Material.DIAMOND_CHESTPLATE)),
    DIAMOND_LEGGINGS(new ItemStack(Material.DIAMOND_LEGGINGS)),
    DIAMOND_BOOTS(new ItemStack(Material.DIAMOND_BOOTS));

    private ItemStack itemStack;

    DropEquipEnum(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }

    private void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public void apply(Player player) {
        PlayerDataBlock dataBlock = Main.getPlayerDataBlock(player);
        ItemStack itemHelmet = player.getInventory().getHelmet();
        ItemStack itemChestplate = player.getInventory().getChestplate();
        ItemStack itemLeggings = player.getInventory().getLeggings();
        ItemStack itemBoots = player.getInventory().getBoots();

        //天赋 幸运钻石
        boolean switchToDiamond = Math.random() <= dataBlock.getNormalTalentLevelList().get(GUITalentItemEnum.LUCKY_DIAMOND.getId()) * 0.1 + 0.1;
        if (switchToDiamond) {
            sendSwitchToDiamondMessage(player, itemStack.getItemMeta().displayName());
        }

        if (this == IRON_HELMET && itemHelmet == null) {
            player.getInventory().setHelmet(itemStack);
            player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_CHAIN, 1, 1);
            if (switchToDiamond) {
                player.getInventory().setHelmet(DIAMOND_HELMET.getItemStack());
            }

        } else if (this == IRON_CHESTPLATE && (Objects.isNull(itemChestplate) || itemChestplate.getType() == Material.CHAINMAIL_CHESTPLATE)) {
            player.getInventory().setChestplate(itemStack);
            player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_CHAIN, 1, 1);
            if (switchToDiamond) {
                player.getInventory().setChestplate(DIAMOND_CHESTPLATE.getItemStack());
            }
        }
        else if (this == IRON_LEGGINGS && (Objects.isNull(itemLeggings) || itemLeggings.getType() == Material.CHAINMAIL_LEGGINGS)) {
            player.getInventory().setLeggings(itemStack);
            player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_CHAIN, 1, 1);
            if (switchToDiamond) {
                player.getInventory().setLeggings(DIAMOND_LEGGINGS.getItemStack());
            }
        }
        else if (this == IRON_BOOTS && itemBoots == null) {
            player.getInventory().setBoots(itemStack);
            player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_CHAIN, 1, 1);
            if (switchToDiamond) {
                player.getInventory().setBoots(DIAMOND_BOOTS.getItemStack());
            }
        }
    }

    //天赋 幸运钻石
    private void sendSwitchToDiamondMessage(Player player, Component legacyNameComp) {
        String legacyName = PlainTextComponentSerializer.plainText().serialize(legacyNameComp);
        player.sendMessage(String.format(LangLoader.getString("talent_item_id_11_message"), legacyName));
    }

    public static void init() {
        for (DropEquipEnum item : DropEquipEnum.values()) {
            ItemStack itemStack1 = item.getItemStack();
            ItemMeta itemMeta = itemStack1.getItemMeta();
            if (item == DropEquipEnum.IRON_HELMET
                    || item == DropEquipEnum.IRON_CHESTPLATE
                    || item == DropEquipEnum.IRON_LEGGINGS
                    || item == DropEquipEnum.IRON_BOOTS
                    || item == DropEquipEnum.DIAMOND_HELMET
                    || item == DropEquipEnum.DIAMOND_CHESTPLATE
                    || item == DropEquipEnum.DIAMOND_LEGGINGS
                    || item == DropEquipEnum.DIAMOND_BOOTS) {
                itemMeta.setUnbreakable(true);
            }
            itemStack1.setItemMeta(itemMeta);
            item.setItemStack(itemStack1);
        }
    }
}
