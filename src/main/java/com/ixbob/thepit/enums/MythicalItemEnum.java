package com.ixbob.thepit.enums;

import com.ixbob.thepit.LangLoader;
import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public enum MythicalItemEnum {
    MYTHICAL_RED_LEATHER_LEGGINGS(Material.LEATHER_LEGGINGS, LangLoader.getString("pititem_mythical_red_leather_leggings"), Color.RED);

    private final Material material;
    private final String name;
    private final Color leatherColor;

    MythicalItemEnum(Material material, String name, Color leatherColor) {
        this.material = material;
        this.name = name;
        this.leatherColor = leatherColor;
    }

    public ItemStack getItemStack() {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta instanceof LeatherArmorMeta leatherMeta) {
            leatherMeta.setColor(leatherColor);
            itemStack.setItemMeta(leatherMeta);
        }
        itemMeta.displayName(Component.text(name));
        itemMeta.setUnbreakable(true);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
