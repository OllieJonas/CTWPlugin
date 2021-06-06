package me.ollie.capturethewool.game.key;

import lombok.Getter;
import org.bukkit.Material;

public enum LockType {
    IRON(Material.IRON_BLOCK),
    GOLD(Material.GOLD_BLOCK),
    DIAMOND(Material.DIAMOND_BLOCK),
    EMERALD(Material.EMERALD_BLOCK);

    @Getter
    private final Material material;

    LockType(Material material) {
        this.material = material;
    }
}
