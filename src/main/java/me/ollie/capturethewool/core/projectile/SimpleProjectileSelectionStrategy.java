package me.ollie.capturethewool.core.projectile;

import me.ollie.capturethewool.core.util.ReducedItemStack;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public class SimpleProjectileSelectionStrategy implements ProjectileSelectionStrategy {

    private final SpecialProjectileRegistry registry;

    public SimpleProjectileSelectionStrategy(SpecialProjectileRegistry registry) {
        this.registry = registry;
    }

    @Override
    public Optional<SpecialProjectile> projectile(EntityType type, Player player) {
//        Optional<SpecialProjectile> optional = fromItem(player.getInventory().getItemInOffHand());
//        if (optional.isPresent()) return optional;

        registry.getAllItems().forEach((k, v) -> player.sendMessage(k.toString() + ": " + v.toString()));

        Arrays.stream(player.getInventory().getContents()).filter(Objects::nonNull).forEach(i -> player.sendMessage(ReducedItemStack.from(i).toString()));
        player.sendMessage("here1 proj");

        return Arrays.stream(player.getInventory().getContents())
                .filter(Objects::nonNull)
                .filter(e -> ProjectileUtil.fromMaterial(e.getType()) == type)
                .map(registry::get)
                .findFirst();
    }

    private Optional<SpecialProjectile> fromItem(ItemStack item) {
        return item != null &&
                item.getType() != Material.AIR &&
                ProjectileUtil.fromMaterial(item.getType()) != null ? Optional.ofNullable(registry.get(item)) : Optional.empty();
    }
}
