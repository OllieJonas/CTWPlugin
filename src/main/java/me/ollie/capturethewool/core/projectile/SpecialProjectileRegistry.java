package me.ollie.capturethewool.core.projectile;

import me.ollie.capturethewool.core.util.ReducedItemStack;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SpecialProjectileRegistry {

    private final Map<EntityType, Map<String, SpecialProjectile>> projectiles;

    private final Map<ReducedItemStack, SpecialProjectile> projectileItems;

    public SpecialProjectileRegistry() {
        this(new HashMap<>());
    }

    public SpecialProjectileRegistry(Map<EntityType, Map<String, SpecialProjectile>> projectiles) {
        this.projectiles = projectiles;
        this.projectileItems = new HashMap<>();
    }

    public void register(SpecialProjectile projectile) {
        projectiles.computeIfAbsent(projectile.type(), k -> new HashMap<>());
        projectiles.get(projectile.type()).put(projectile.getMetadata(), projectile);
        projectileItems.put(ReducedItemStack.from(projectile.asItem()), projectile);
    }

    public SpecialProjectile get(ItemStack item) {
        return get(ReducedItemStack.from(item));
    }

    public SpecialProjectile get(ReducedItemStack stack) {
        return projectileItems.get(stack);
    }

    public SpecialProjectile get(EntityType type, String metadata) {
        Map<String, SpecialProjectile> items = projectiles.get(type);
        return items == null ? null : items.get(metadata);
    }

    public Map<ReducedItemStack, SpecialProjectile> getAllItems() {
        return projectileItems;
    }
}
