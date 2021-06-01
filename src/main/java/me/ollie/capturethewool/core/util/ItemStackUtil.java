package me.ollie.capturethewool.core.util;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

@UtilityClass
public class ItemStackUtil {

    public boolean weakEquals(ItemStack i1, ItemStack i2) {
        if (i1 == null && i2 == null) return true;

        if (i1 == null || i2 == null) return false;

        return i1.getType() == i2.getType() && i1.getItemMeta().getDisplayName().equals(i2.getItemMeta().getDisplayName());
    }

    public ItemStack stripLore(ItemStack item) {

        if (item == null) return null;

        ItemMeta meta = item.getItemMeta();
        meta.setLore(Collections.emptyList());
        item.setItemMeta(meta);
        return item;
    }

    public String getName(ItemStack item) {
        String name;
        return (name = item.getItemMeta().getDisplayName()).equals("") ? getName(item.getType()) : name;
    }

    public String getName(Material material) {
        return ChatColor.GRAY + WordUtils.capitalizeFully(material.toString().replace("_", " "));
    }

    public String getNameAndAmount(ItemStack item) {
        int amount;
        String name = getName(item);
        return (amount = item.getAmount()) != 1 ? name + ChatColor.YELLOW + " x" + amount : name;
    }
}
