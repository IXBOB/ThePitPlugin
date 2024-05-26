package com.ixbob.thepit.enums;

import com.ixbob.thepit.LangLoader;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum PitItemEnum {
    GOLDEN_CHOCOLATE(CustomSkullEnum.GOLDEN_CHOCOLATE.getItemStack(), LangLoader.get("talent_item_id_1_name")),
    BASIC_STONE_SWORD(CustomBasicToolEnum.BASIC_STONE_SWORD.getItemStack(), LangLoader.get("pititem_basic_bow")),
    BASIC_CHAINMAIL_CHESTPLATE(CustomBasicToolEnum.BASIC_CHAINMAIL_CHESTPLATE.getItemStack(), LangLoader.get("pititem_basic_chainmail_chestplate")),
    BASIC_CHAINMAIL_LEGGINGS(CustomBasicToolEnum.BASIC_CHAINMAIL_LEGGINGS.getItemStack(), LangLoader.get("pititem_basic_chainmail_leggings")),

    DEFAULT_IRON_HELMET(DropItemEnum.IRON_HELMET.getItemStack(), LangLoader.get("pittalentitem_default_iron_helmet")),
    DEFAULT_IRON_CHESTPLATE(DropItemEnum.IRON_CHESTPLATE.getItemStack(), LangLoader.get("pittalentitem_default_iron_chestplate")),
    DEFAULT_IRON_LEGGINGS(DropItemEnum.IRON_LEGGINGS.getItemStack(), LangLoader.get("pittalentitem_default_iron_leggings")),
    DEFAULT_IRON_BOOTS(DropItemEnum.IRON_BOOTS.getItemStack(), LangLoader.get("pittalentitem_default_iron_boots")),
    DEFAULT_DIAMOND_HELMET(ShopGivingItemEnum.DIAMOND_HELMET.getItemStack(), LangLoader.get("pitshopitem_default_diamond_helmet")),
    DEFAULT_DIAMOND_CHESTPLATE(ShopGivingItemEnum.DIAMOND_CHESTPLATE.getItemStack(), LangLoader.get("pitshopitem_default_diamond_chestplate")),
    DEFAULT_DIAMOND_LEGGINGS(ShopGivingItemEnum.DIAMOND_LEGGINGS.getItemStack(), LangLoader.get("pitshopitem_default_diamond_leggings")),
    DEFAULT_DIAMOND_BOOTS(ShopGivingItemEnum.DIAMOND_BOOTS.getItemStack(), LangLoader.get("pitshopitem_default_diamond_boots")),
    DEFAULT_FISHING_ROD(TalentGivingItemEnum.DEFAULT_FISHING_ROD.getItemStack(), LangLoader.get("pittalentitem_default_fishing_rod"));

    private final ItemStack itemStack;
    private final String name;

    PitItemEnum(ItemStack itemStack, String name) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemStack.setItemMeta(itemMeta);
        this.itemStack = itemStack;
        this.name = name;
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }

    public String getName() {
        return  this.name;
    }
}
