package me.ollie.capturethewool.core.util;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public record ReducedItemStack(String name, Material material) {

    public static ReducedItemStack from(ItemStack item) {
        String name = !item.getItemMeta().getDisplayName().equals("") ? item.getItemMeta().getDisplayName() : ItemStackUtil.getName(item);
        return new ReducedItemStack(ChatColor.stripColor(name), item.getType());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReducedItemStack that = (ReducedItemStack) o;
        return Objects.equals(name, that.name) &&
                material == that.material;
    }
}
