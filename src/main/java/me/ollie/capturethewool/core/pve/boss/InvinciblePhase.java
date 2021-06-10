package me.ollie.capturethewool.core.pve.boss;

import me.ollie.capturethewool.CaptureTheWool;
import me.ollie.capturethewool.core.ability.Ability;
import me.ollie.capturethewool.core.pve.Enemy;
import me.ollie.capturethewool.core.pve.animation.SpawnAnimation;
import me.ollie.capturethewool.core.pve.boss.phase.EndCondition;
import me.ollie.capturethewool.core.pve.boss.phase.Phase;
import me.ollie.capturethewool.core.util.LocationUtil;
import me.ollie.capturethewool.core.util.VectorCircle;
import me.ollie.capturethewool.core.util.stream.Permutations;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class InvinciblePhase implements Phase {

    private final Boss<?> boss;

    private final EndCondition.EnemiesDead condition;

    private final SpawnAnimation animation;

    private final Map<AbilityTriggerReason, BossAbility> abilities;

    private final Location teleport;

    private Location prev;

    private Set<VectorCircle> invincibleCircles;

    public InvinciblePhase(Boss<?> boss, Collection<? extends Enemy<?>> enemies) {
        this(boss, enemies, null);
    }

    public InvinciblePhase(Boss<?> boss, Collection<? extends Enemy<?>> enemies, SpawnAnimation animation) {
        this(boss, enemies, animation, null);
    }

    public InvinciblePhase(Boss<?> boss, Collection<? extends Enemy<?>> entities, SpawnAnimation animation, Location teleport) {
        this(boss, new EndCondition.EnemiesDead(entities), animation, Collections.emptyMap(), teleport);
    }

    public InvinciblePhase(Boss<?> boss, EndCondition.EnemiesDead condition, SpawnAnimation animation, Map<AbilityTriggerReason, BossAbility> abilities, Location teleport) {
        this.boss = boss;
        this.condition = condition;
        this.animation = animation;
        this.abilities = abilities;
        this.teleport = teleport;
    }

    @Override
    public void onStart() {
        LivingEntity entity = boss.getEnemy().getEntity();
        entity.setInvulnerable(true);
        List<Double> doubles = new ArrayList<>();
        doubles.add(-0.3);
        doubles.add(-0.8);
        doubles.add(0.1);
        doubles.add(0.7);

        AtomicLong aLong = new AtomicLong(1);

        // List<VectorCircle> circles = IntStream.range(0, bees.size()).mapToObj(i -> new VectorCircle(player, axes.get(i), 0.1, 2.75, l -> bees.get(i).teleport(l))).toList();
        invincibleCircles = Permutations.of(doubles, 3)
                .map(p -> new Vector(p.get(0), p.get(1), p.get(2)))
                .map(v -> new VectorCircle(entity, v, 0.2, 1.7,
                        l -> l.getWorld().spawnParticle(Particle.REDSTONE, l, 1,
                                new Particle.DustOptions(Color.WHITE, 1))))
                .collect(Collectors.toSet());
        invincibleCircles.forEach(c -> c.runTaskTimer(CaptureTheWool.getInstance(), aLong.getAndAdd(2), 1L));

        condition.entities().forEach(e -> e.spawn(LocationUtil.randomLocationAround(entity.getLocation(), 20), animation));

        if (teleport != null) {
            prev = entity.getLocation();
            entity.teleport(teleport);
        }

    }

    @Override
    public Map<AbilityTriggerReason, BossAbility> abilitySet() {
        return abilities;
    }

    @Override
    public EndCondition endCondition() {
        return condition;
    }

    @Override
    public void onFinish() {
        Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage(this.getClass().getSimpleName() + " has finished"));
        LivingEntity entity = boss.getEnemy().getEntity();
        invincibleCircles.forEach(BukkitRunnable::cancel);
        entity.setInvulnerable(false);
        if (prev != null) entity.teleport(prev);
    }
}
