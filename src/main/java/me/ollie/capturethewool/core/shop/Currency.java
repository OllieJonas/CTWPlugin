package me.ollie.capturethewool.core.shop;

import me.ollie.capturethewool.core.util.ItemStackUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public record Currency(String name, String formattedName, ItemStack itemRepresentation) {

    public static Currency from(Material material) {
        ItemStack item = new ItemStack(material);
        String name = ItemStackUtil.getName(material);
        return new Currency(name, ChatColor.WHITE + name, item);
    }

}
