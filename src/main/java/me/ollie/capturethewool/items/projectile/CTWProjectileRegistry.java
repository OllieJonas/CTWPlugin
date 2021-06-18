package me.ollie.capturethewool.items.projectile;

import lombok.Getter;
import me.ollie.capturethewool.core.projectile.SpecialProjectile;
import me.ollie.capturethewool.core.projectile.SpecialProjectileRegistry;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;

import java.util.Collections;

public class CTWProjectileRegistry {

    public static final SpecialProjectile TEST_ARROW = new SpecialProjectile(EntityType.ARROW, "Testing Arrows", Collections.emptyList(), e -> Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage("hit entity!")), l -> Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage("hit block!")));

    @Getter
    private final SpecialProjectileRegistry registry;

    public CTWProjectileRegistry() {
        this.registry = new SpecialProjectileRegistry();

        registerAll();
    }

    private void registerAll() {
        registry.register(TEST_ARROW);
    }
}
