package me.ollie.capturethewool.items.types;

import me.ollie.capturethewool.core.util.ItemStackUtil;
import me.ollie.capturethewool.items.ItemRarity;
import me.ollie.capturethewool.items.meta.ItemsUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public record RarityItem(String name, ItemRarity rarity, ItemStack item) {

    public RarityItem(ItemStack item, ItemRarity rarity) {
        this(ItemStackUtil.getName(item), rarity, ItemsUtil.transform(item, rarity));
    }

    public RarityItem(Material material, ItemRarity rarity) {
        this(ItemStackUtil.getName(material), material, rarity);
    }

    public RarityItem(String name, Material material, ItemRarity rarity) {
        this(name, rarity, ItemsUtil.buildItemFrom(name, material, rarity));
    }
}
