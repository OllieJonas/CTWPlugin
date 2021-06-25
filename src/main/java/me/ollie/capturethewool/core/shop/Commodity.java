package me.ollie.capturethewool.core.shop;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public record Commodity(ItemStack item, Price price) {

    public ItemStack buildIcon(Player player) {
        ItemStack newItem = item.clone();
        ItemMeta newItemMeta = newItem.getItemMeta();

        List<String> lore = newItemMeta.getLore();
        List<String> newLore = price.itemLore(player);

        if (lore != null) {
            newLore.add(" ");
            newLore.addAll(lore);
        }

        newItemMeta.setLore(newLore);

        newItem.setItemMeta(newItemMeta);

        return newItem;
    }

    public static Commodity of(ItemStack item, Price price) {
        return new Commodity(item, price);
    }
}
