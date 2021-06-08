package me.ollie.capturethewool.core.pve.boss;

import me.ollie.capturethewool.core.ability.Ability;
import me.ollie.capturethewool.core.pve.Enemy;
import me.ollie.capturethewool.core.pve.boss.phase.EndCondition;
import me.ollie.capturethewool.core.pve.boss.phase.Phase;
import org.bukkit.entity.LivingEntity;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class InvinciblePhase implements Phase {

    private final EndCondition condition;

    private final Map<AbilityTriggerReason, Ability> abilities;

    public InvinciblePhase(Collection<? extends Enemy<?>> entities) {
        this(new EndCondition.EnemiesDead(entities.stream().map(Enemy::getEntity).collect(Collectors.toList())), Collections.emptyMap());
    }

    public InvinciblePhase(EndCondition condition, Map<AbilityTriggerReason, Ability> abilities) {
        this.condition = condition;
        this.abilities = abilities;
    }

    @Override
    public void onStart() {

    }

    @Override
    public Map<AbilityTriggerReason, Ability> abilitySet() {
        return abilities;
    }

    @Override
    public EndCondition endCondition() {
        return condition;
    }

    @Override
    public void onFinish() {

    }
}
