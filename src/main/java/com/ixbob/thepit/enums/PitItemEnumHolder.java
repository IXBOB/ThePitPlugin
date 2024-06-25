package com.ixbob.thepit.enums;

import com.ixbob.thepit.LangLoader;
import com.ixbob.thepit.Main;
import com.ixbob.thepit.PlayerDataBlock;
import com.ixbob.thepit.enums.gui.shop.GUIShopItemEnum;
import com.ixbob.thepit.enums.gui.talent.GUITalentItemEnum;
import com.ixbob.thepit.enums.gui.talent.TalentGivingItemEnum;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.Base64;
import java.util.Objects;
import java.util.UUID;

public enum PitItemEnumHolder {
    //各处东西在此做总结，方便调用和记忆

    GOLDEN_CHOCOLATE(CustomSkullEnum.GOLDEN_CHOCOLATE.getItemStack(), LangLoader.getString("talent_item_id_1_name")),
    BASIC_STONE_SWORD(CustomBasicToolEnum.BASIC_STONE_SWORD.getItemStack()),
    BASIC_BOW(CustomBasicToolEnum.BASIC_BOW.getItemStack()),
    BASIC_CHAINMAIL_CHESTPLATE(CustomBasicToolEnum.BASIC_CHAINMAIL_CHESTPLATE.getItemStack()),
    BASIC_CHAINMAIL_LEGGINGS(CustomBasicToolEnum.BASIC_CHAINMAIL_LEGGINGS.getItemStack()),

    DEFAULT_IRON_HELMET(DropEquipEnum.IRON_HELMET.getItemStack()),
    DEFAULT_IRON_CHESTPLATE(DropEquipEnum.IRON_CHESTPLATE.getItemStack()),
    DEFAULT_IRON_LEGGINGS(DropEquipEnum.IRON_LEGGINGS.getItemStack()),
    DEFAULT_IRON_BOOTS(DropEquipEnum.IRON_BOOTS.getItemStack()),
    DEFAULT_DIAMOND_HELMET(ShopGivingItemEnum.DIAMOND_HELMET.getItemStack()),
    DEFAULT_DIAMOND_CHESTPLATE(ShopGivingItemEnum.DIAMOND_CHESTPLATE.getItemStack()),
    DEFAULT_DIAMOND_LEGGINGS(ShopGivingItemEnum.DIAMOND_LEGGINGS.getItemStack()),
    DEFAULT_DIAMOND_BOOTS(ShopGivingItemEnum.DIAMOND_BOOTS.getItemStack()),
    DEFAULT_DIAMOND_SWORD(ShopGivingItemEnum.DIAMOND_SWORD.getItemStack()),
    DEFAULT_OBSIDIAN(ShopGivingItemEnum.OBSIDIAN.getItemStack()),
    DEFAULT_FISHING_ROD(TalentGivingItemEnum.DEFAULT_FISHING_ROD.getItemStack()),
    DEFAULT_CHAINMAIL_HELMET(TalentGivingItemEnum.DEFAULT_CHAINMAIL_HELMET.getItemStack()),
    DIAMOND_PICKAXE_EFFICIENCY_4(TalentGivingItemEnum.DIAMOND_PICKAXE_EFFICIENCY_4.getItemStack()),
    DEFAULT_COBBLESTONE(TalentGivingItemEnum.DEFAULT_COBBLESTONE.getItemStack()),

    MYTHICAL_RED_LEATHER_LEGGINGS(MythicalItemEnum.MYTHICAL_RED_LEATHER_LEGGINGS.getItemStack());

    private final ItemStack itemStack;

    PitItemEnumHolder(ItemStack itemStack, String name) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemStack.setItemMeta(itemMeta);
        this.itemStack = itemStack;
    }

    PitItemEnumHolder(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public static void init() {
        CustomSkullEnum.init();
        CustomBasicToolEnum.init();
        DropEquipEnum.init();
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }

    public ItemStack getItemStack(int amount) {
        return new ItemStack(this.itemStack.getType(), amount);
    }



    public enum CustomBasicToolEnum {
        BASIC_STONE_SWORD(new ItemStack(Material.STONE_SWORD)),
        BASIC_BOW(new ItemStack(Material.BOW)),
        BASIC_CHAINMAIL_CHESTPLATE(new ItemStack(Material.CHAINMAIL_CHESTPLATE)),
        BASIC_CHAINMAIL_LEGGINGS(new ItemStack(Material.CHAINMAIL_LEGGINGS));

        private ItemStack itemStack;

        CustomBasicToolEnum(ItemStack itemStack) {
            this.itemStack = itemStack;
        }

        public ItemStack getItemStack() {
            return this.itemStack;
        }

        private void setItemStack(ItemStack itemStack) {
            this.itemStack = itemStack;
        }

        public static void init() {
            for (CustomBasicToolEnum tool : CustomBasicToolEnum.values()) {
                ItemStack itemStack1 = tool.getItemStack();
                ItemMeta itemMeta = itemStack1.getItemMeta();
                if (tool == CustomBasicToolEnum.BASIC_STONE_SWORD
                        || tool == CustomBasicToolEnum.BASIC_BOW
                        || tool == CustomBasicToolEnum.BASIC_CHAINMAIL_CHESTPLATE
                        || tool == CustomBasicToolEnum.BASIC_CHAINMAIL_LEGGINGS) {
                    itemMeta.setUnbreakable(true);
                }
                itemStack1.setItemMeta(itemMeta);
                tool.setItemStack(itemStack1);
            }
        }
    }


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


    public enum ShopGivingItemEnum {
        DIAMOND_HELMET(Material.DIAMOND_HELMET),
        DIAMOND_CHESTPLATE(Material.DIAMOND_CHESTPLATE),
        DIAMOND_LEGGINGS(Material.DIAMOND_LEGGINGS),
        DIAMOND_BOOTS(Material.DIAMOND_BOOTS),
        OBSIDIAN(Material.OBSIDIAN),
        DIAMOND_SWORD(Material.DIAMOND_SWORD);

        private final ItemStack itemStack;

        ShopGivingItemEnum(Material material){
            ItemStack itemStack = new ItemStack(material);
            ItemMeta itemMeta = itemStack.getItemMeta();
            if (material == Material.DIAMOND_HELMET
                    || material == Material.DIAMOND_CHESTPLATE
                    || material == Material.DIAMOND_LEGGINGS
                    || material == Material.DIAMOND_BOOTS
                    || material == Material.DIAMOND_SWORD){
                itemMeta.setUnbreakable(true);
            }
            if (material == Material.OBSIDIAN) {
                itemStack.setAmount(GUIShopItemEnum.OBSIDIAN.getAmount());
            }
            itemStack.setItemMeta(itemMeta);
            this.itemStack = itemStack;
        }

        public ItemStack getItemStack(){
            return this.itemStack;
        }
    }


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
}
