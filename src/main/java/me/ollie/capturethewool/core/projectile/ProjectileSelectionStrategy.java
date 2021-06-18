package me.ollie.capturethewool.core.projectile;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.Optional;

public interface ProjectileSelectionStrategy {

    Optional<SpecialProjectile> projectile(EntityType type, Player player);
}
