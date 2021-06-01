package me.ollie.capturethewool.core.game.kit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@AllArgsConstructor
@Getter
public class Kit {

    private String name;

    private Material icon;

    private List<ItemStack> items;
}
