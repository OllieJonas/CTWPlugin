package me.ollie.capturethewool.core.gui;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jooq.lambda.function.Consumer3;

import java.util.function.BiConsumer;

public record GUIItem(ItemStack item,
                      Consumer3<Player, Integer, ItemStack> action,
                      boolean itemClosesMenu) {


    public static GUIItem of(ItemStack item, BiConsumer<Player, ItemStack> action, boolean itemClosesMenu) {
        return new GUIItem(item, (p, ignored, i) -> action.accept(p, i), itemClosesMenu);
    }

    public static GUIItem empty(ItemStack item) {
        return new GUIItem(item, (p, ind, i) -> {}, false);
    }
}
