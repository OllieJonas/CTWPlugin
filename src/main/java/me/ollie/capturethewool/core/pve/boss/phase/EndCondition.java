package me.ollie.capturethewool.core.pve.boss.phase;

import me.ollie.capturethewool.core.pve.Enemy;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;

import java.util.Collection;
import java.util.Objects;

/**
 * wow do i wish Java had a way of properly declaring case classes
 */
public sealed interface EndCondition permits EndCondition.HealthReduction, EndCondition.EnemiesDead {

    final record HealthReduction() implements EndCondition {}

    final record EnemiesDead(Collection<? extends Enemy<?>> entities) implements EndCondition {

        public boolean isEnemy(LivingEntity entity) {
            entities.stream().map(e -> e.getEntity().getCustomName()).filter(Objects::nonNull).forEach(e -> Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage(e)));
            return entities.stream().map(e -> e.getEntity().getEntityId()).anyMatch(e -> e == entity.getEntityId());
        }
    }
}
