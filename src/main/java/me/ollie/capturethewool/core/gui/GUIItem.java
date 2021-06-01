package me.ollie.capturethewool.core.gui;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.function.BiConsumer;

@Getter
@AllArgsConstructor
@Builder
public class GUIItem {

    private final ItemStack item;

    private final BiConsumer<Player, ItemStack> action;

    private final boolean itemClosesMenu;

    public static GUIItem empty(ItemStack item) {
        return new GUIItem(item, (p, i) -> {
        }, false);
    }
}
