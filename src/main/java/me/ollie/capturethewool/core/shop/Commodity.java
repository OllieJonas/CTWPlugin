package me.ollie.capturethewool.core.shop;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.logging.LogRecord;

@Getter
public class Commodity {

    private final ItemStack item;

    private final Price price;

    private Commodity(ItemStack item, Price price) {
        this.item = item;
        this.price = price;
    }

    public ItemStack buildIcon(Player player) {
        ItemStack newItem = item.clone();
        ItemMeta newItemMeta = newItem.getItemMeta();

        List<String> lore = newItemMeta.getLore();
        List<String> newLore = price.itemLore(player);

        if (lore != null)
            newLore.addAll(lore);

        newItemMeta.setLore(newLore);

        newItem.setItemMeta(newItemMeta);

        return newItem;
    }

    public static Commodity of(ItemStack item, Price price) {
        return new Commodity(item, price);
    }
}
