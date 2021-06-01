package me.ollie.capturethewool.game.key;

import lombok.Getter;
import me.ollie.capturethewool.core.util.ItemStackBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@Getter
public enum KeyType {

    IRON("Iron", Material.IRON_INGOT, ChatColor.GRAY),
    GOLD("Gold", Material.GOLD_INGOT, ChatColor.GOLD),
    DIAMOND("Diamond", Material.DIAMOND, ChatColor.AQUA);

    private final String name;

    private final Material material;

    private final ChatColor colour;

    private static final KeyType[] VALUES = values();

    KeyType(String name, Material material, ChatColor colour) {
        this.name = name;
        this.material = material;
        this.colour = colour;
    }

    public String getFormattedName() {
        return colour + "" + ChatColor.BOLD + name + " Key";
    }

    public ItemStack buildItemStack() {
        return new ItemStackBuilder(material).withName(getFormattedName()).withGlow().build();
    }

    public static boolean isItemAKey(ItemStack item) {
        for (KeyType type : values()) {
            if (type.getMaterial() == item.getType() && type.getFormattedName().equals(item.getItemMeta().getDisplayName()))
                return true;
        }
        return false;
    }
}
