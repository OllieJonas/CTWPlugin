package me.ollie.capturethewool.core.util;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Particle;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class HomingArrow {

    private final JavaPlugin plugin;

    private final Arrow arrow;

    private final Set<Class<? extends LivingEntity>> blacklistedTypes;

    private final Set<Player> blacklistedPlayers;

    private HomingArrow(JavaPlugin plugin, Arrow arrow) {
        this.plugin = plugin;
        this.arrow = arrow;
        this.blacklistedTypes = new HashSet<>();
        this.blacklistedPlayers = new HashSet<>();
    }

    public static HomingArrow decorate(JavaPlugin plugin, Arrow arrow) {
        return new HomingArrow(plugin, arrow);
    }

    public HomingArrow excludedPlayers(Player... players) {
        blacklistedPlayers.addAll(Arrays.asList(players));
        return this;
    }

    public HomingArrow excludedTypes(Class<? extends LivingEntity>... classes) {
        blacklistedTypes.addAll(Arrays.asList(classes));
        return this;
    }

    public void shoot(Player player) {
        shoot(player, 0L);
    }

    public void shoot(Player player, long delay) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> arrow.getNearbyEntities(64.0D, 64.0D, 64.0D).stream()
                    .filter(Objects::nonNull)
                    .filter(e -> !e.equals(player))
                    .filter(player::hasLineOfSight)
                    .filter(e -> e instanceof LivingEntity)
                    .filter(e -> !e.isDead())
                    .filter(e -> !blacklistedTypes.contains(e.getClass()))
                    .filter(e -> !(e instanceof Player) && !blacklistedPlayers.contains(e))
                    .min((e1, e2) ->
                            Float.compare(
                                    arrow.getVelocity().angle(e1.getLocation().toVector().clone().subtract(player.getLocation().toVector())),
                                    arrow.getVelocity().angle(e2.getLocation().toVector().clone().subtract(player.getLocation().toVector()))))
                    .ifPresent(minEntity -> new HomingTask(arrow, (LivingEntity) minEntity).runTaskTimer(plugin, 1L, 1L)), delay);
//        double minAngle = 6.283185307179586D;
    }


    private static class HomingTask extends BukkitRunnable {

        private static final double maxRotationAngle = 0.12D;
        private static final double targetSpeed = 1.4D;

        Arrow arrow;
        LivingEntity target;

        public HomingTask(Arrow arrow, LivingEntity target) {
            this.arrow = arrow;
            this.target = target;
        }

        public void run() {
            double speed = this.arrow.getVelocity().length();
            if ((this.arrow.isOnGround()) || (this.arrow.isDead()) || (this.target.isDead())) {
                cancel();
                return;
            }
            Vector toTarget = this.target.getLocation().clone().add(new Vector(0.0D, 0.5D, 0.0D)).subtract(this.arrow.getLocation()).toVector();

            Vector dirVelocity = this.arrow.getVelocity().clone().normalize();
            Vector dirToTarget = toTarget.clone().normalize();
            double angle = dirVelocity.angle(dirToTarget);


            double newSpeed = 0.9D * speed + 0.14D;
            if (((this.target instanceof Player)) && (this.arrow.getLocation().distance(this.target.getLocation()) < 8.0D)) {
                Player player = (Player) this.target;

                if (player.isBlocking()) {
                    newSpeed = speed * 0.6D;
                }
            }

            Vector newVelocity;
            if (angle < 0.12D) {
                newVelocity = dirVelocity.clone().multiply(newSpeed);
            } else {
                Vector newDir = dirVelocity.clone().multiply((angle - 0.12D) / angle).add(dirToTarget.clone().multiply(0.12D / angle));
                newDir.normalize();
                newVelocity = newDir.clone().multiply(newSpeed);
            }
            this.arrow.setVelocity(newVelocity.add(new Vector(0.0D, 0.03D, 0.0D)));
            this.arrow.getWorld().spawnParticle(Particle.WHITE_ASH, this.arrow.getLocation(), 1);
        }
    }
}
