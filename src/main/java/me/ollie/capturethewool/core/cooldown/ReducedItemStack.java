package me.ollie.capturethewool.core.cooldown;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

@AllArgsConstructor(staticName = "of")
@Getter
public class ReducedItemStack {

    private final String name;

    private final Material material;

    public static ReducedItemStack from(ItemStack item) {
        return new ReducedItemStack(ChatColor.stripColor(item.getItemMeta().getDisplayName()), item.getType());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReducedItemStack that = (ReducedItemStack) o;
        return Objects.equals(name, that.name) &&
                material == that.material;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, material);
    }
}
