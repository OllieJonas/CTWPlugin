package me.ollie.capturethewool.core.shop;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@Getter
@AllArgsConstructor
public class Currency {

    private final String name;

    private final String formattedName;

    private final ItemStack itemRepresentation;

    public static Currency from(Material material) {
        ItemStack item = new ItemStack(material);
        String name = WordUtils.capitalizeFully(item.getType().toString().replace("_", " "));

        return new Currency(name, ChatColor.WHITE + name, item);
    }

}
