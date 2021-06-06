package me.ollie.capturethewool.game.key;

import lombok.Getter;
import me.ollie.capturethewool.core.util.ItemStackBuilder;
import me.ollie.capturethewool.core.util.ItemStackUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

@Getter
public enum KeyType {

    IRON("Iron", Material.IRON_INGOT, ChatColor.GRAY, new LockType[]{LockType.IRON}),
    GOLD("Gold", Material.GOLD_INGOT, ChatColor.GOLD, new LockType[]{LockType.GOLD}),
    DIAMOND("Diamond", Material.DIAMOND, ChatColor.AQUA, new LockType[]{LockType.DIAMOND}),
    EMERALD("Emerald", Material.EMERALD, ChatColor.GREEN, new LockType[]{LockType.EMERALD});

    private final String name;

    private final Material material;

    private final ChatColor colour;

    private final LockType[] canOpen;

    private static final KeyType[] VALUES = values();

    KeyType(String name, Material material, ChatColor colour, LockType[] canOpen) {
        this.name = name;
        this.material = material;
        this.colour = colour;
        this.canOpen = canOpen;
    }

    public String getFormattedName() {
        return colour + "" + ChatColor.BOLD + name + " Key";
    }

    public static boolean isAKey(ItemStack item) {
        return Arrays.stream(values()).anyMatch(p -> ItemStackUtil.weakEquals(p.buildItemStack(), item));
    }

    public static KeyType keyFrom(Material material) {
        return Arrays.stream(values()).filter(m -> m.getMaterial() == material).findFirst().orElse(null);
    }

    public static void removeFromInventory(Player player, KeyType keyType) {
        player.getInventory().removeItemAnySlot(keyType.buildItemStack());
    }

    public ItemStack buildItemStack() {
        return new ItemStackBuilder(material).withName(getFormattedName()).withGlow().build();
    }
}
