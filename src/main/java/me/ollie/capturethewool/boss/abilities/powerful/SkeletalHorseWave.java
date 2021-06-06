package me.ollie.capturethewool.boss.abilities.powerful;

import me.ollie.capturethewool.core.ability.Ability;
import me.ollie.capturethewool.core.util.VectorUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.*;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Set;

public class SkeletalHorseWave extends Ability {

    record DamageContext(SkeletonHorse horse, LivingEntity target) implements Ability.DamageContext {

        @Override
        public double damage() {
            return 8D;
        }
    }
    @Override
    public void damage(Ability.DamageContext context) {
        if (context instanceof DamageContext horseCtx) {
            horseCtx.target().damage(context.damage());
        }
    }

    @Override
    public void power(LivingEntity entity) {

        Location start = entity.getLocation();
        Set<SkeletonHorse> horses = new HashSet<>();
        Set<Slime> dummyTargets = new HashSet<>();

        Vector direction = start.getDirection().normalize();

        for (int i = 1; i < 7; i++) {
            Location horseLocation = buildHorseSpawnLocation(start, direction, (i % 2 == 0 ? -1 : 1) * (i  + 1.5));
            SkeletonHorse horse = start.getWorld().spawn(horseLocation, SkeletonHorse.class);
            Slime dummy = start.getWorld().spawn(horseLocation.clone().add(direction.clone().setY(0).multiply(25)), Slime.class);
            horse.setInvulnerable(true);
            horse.setCollidable(false);
            horse.setTarget(dummy);

            dummy.setSize(1);
            dummy.setInvisible(true);
            dummy.setInvulnerable(true);
            dummy.setAI(false);

            dummyTargets.add(dummy);
            horses.add(horse);
        }

        int taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(PLUGIN, () -> horses.forEach(h -> {
            Location location = h.getLocation().add(direction.clone().setY(0)).setDirection(direction);
            h.teleport(location);
            location.getNearbyEntities(1.5, 1.5, 1.5).forEach(e -> {
                if (e instanceof LivingEntity e1) {
                    damage(new DamageContext(h, e1));
                }
            });
            feetParticles(location.clone());

            // REALLY HACKY THING TO STOP HORSES FROM RANDOMLY GRAZING IN THE MIDDLE OF THE ABILITY
            h.setAI(false);
            h.setAI(true);
        }), 0L, 2L);

        Bukkit.getScheduler().scheduleSyncDelayedTask(PLUGIN, () -> {
            Bukkit.getScheduler().cancelTask(taskId);
            horses.forEach(Entity::remove);
            dummyTargets.forEach(Entity::remove);
        }, 50);
    }

    private void feetParticles(Location location) {
        location.getWorld().spawnParticle(Particle.FLAME, location, 9, 0.2, 0.2, 0.2, 0);
    }

    private Location buildHorseSpawnLocation(Location location, Vector facingDirection, double distance) {
        Location clone = location.clone();
        Vector perpendicular = facingDirection.clone().rotateAroundY(Math.toRadians(90)).setY(0).normalize();
        clone.add(perpendicular.multiply(distance));
        clone.setDirection(facingDirection);
        return clone;
    }
}
