package com.ixbob.thepit.enums;

import com.ixbob.thepit.LangLoader;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum PitItems {
    GOLDEN_CHOCOLATE(CustomSkull.GOLDEN_CHOCOLATE.getItemStack(), LangLoader.get("talent_item_id_1_name")),
    BASIC_STONE_SWORD(CustomBasicTool.BASIC_STONE_SWORD.getItemStack(), LangLoader.get("pititem_basic_bow")),
    BASIC_CHAINMAIL_CHESTPLATE(CustomBasicTool.BASIC_CHAINMAIL_CHESTPLATE.getItemStack(), LangLoader.get("pititem_basic_chainmail_chestplate")),
    BASIC_CHAINMAIL_LEGGINGS(CustomBasicTool.BASIC_CHAINMAIL_LEGGINGS.getItemStack(), LangLoader.get("pititem_basic_chainmail_leggings")),

    DEFAULT_IRON_HELMET(DropItems.IRON_HELMET.getItemStack(), LangLoader.get("pittalentitem_default_iron_helmet")),
    DEFAULT_IRON_CHESTPLATE(DropItems.IRON_CHESTPLATE.getItemStack(), LangLoader.get("pittalentitem_default_iron_chestplate")),
    DEFAULT_IRON_LEGGINGS(DropItems.IRON_LEGGINGS.getItemStack(), LangLoader.get("pittalentitem_default_iron_leggings")),
    DEFAULT_IRON_BOOTS(DropItems.IRON_BOOTS.getItemStack(), LangLoader.get("pittalentitem_default_iron_boots")),
    DEFAULT_FISHING_ROD(TalentGivingItems.DEFAULT_FISHING_ROD.getItemStack(), LangLoader.get("pittalentitem_default_fishing_rod"));

    private final ItemStack itemStack;
    private final String name;

    PitItems(ItemStack itemStack, String name) {
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
