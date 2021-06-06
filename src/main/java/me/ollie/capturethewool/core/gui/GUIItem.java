package me.ollie.capturethewool.core.gui;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.function.BiConsumer;

public record GUIItem(ItemStack item,
                      BiConsumer<Player, ItemStack> action,
                      boolean itemClosesMenu) {

    public static GUIItem empty(ItemStack item) {
        return new GUIItem(item, (p, i) -> {
        }, false);
    }
}
