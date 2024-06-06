package com.ixbob.thepit.enums;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.Base64;
import java.util.UUID;

public enum CustomSkullEnum {
    GOLDEN_CHOCOLATE(new ItemStack(Material.PLAYER_HEAD, 1, (short) 3));

    private ItemStack itemStack;

    CustomSkullEnum(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }

    private void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public static void init() {
        for (CustomSkullEnum skullEnum : CustomSkullEnum.values()) {
            if (skullEnum == GOLDEN_CHOCOLATE) {
                ItemStack skull = skullEnum.getItemStack();
                SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
                GameProfile profile = new GameProfile(UUID.randomUUID(), null);
                String url = "http://textures.minecraft.net/texture/d30798f27d04b37697747cd5ec86a1639b29c062fb00221a5b14843325310bfe";
                byte[] encodedData = Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
                profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
                Field profileField = null;
                try {
                    profileField = skullMeta.getClass().getDeclaredField("profile");
                } catch (NoSuchFieldException | SecurityException e) {
                    e.printStackTrace();
                }
                profileField.setAccessible(true);
                try {
                    profileField.set(skullMeta, profile);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                skull.setItemMeta(skullMeta);
                skullEnum.setItemStack(skull);
            }
        }
    }
}
