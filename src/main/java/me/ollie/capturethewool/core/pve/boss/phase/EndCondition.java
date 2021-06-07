package me.ollie.capturethewool.core.pve.boss.phase;

import org.bukkit.entity.LivingEntity;

import java.util.Collection;

/**
 * wow do i wish Java had a way of properly declaring case classes
 */
public sealed interface EndCondition permits EndCondition.HealthReduction, EndCondition.EnemiesDead {

    final record HealthReduction() implements EndCondition {}

    final record EnemiesDead(Collection<? extends LivingEntity> entities) implements EndCondition {}
}
