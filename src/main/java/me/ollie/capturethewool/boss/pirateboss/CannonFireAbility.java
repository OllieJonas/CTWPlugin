package me.ollie.capturethewool.boss.pirateboss;

import me.ollie.capturethewool.Main;
import me.ollie.capturethewool.core.ability.Ability;
import me.ollie.capturethewool.core.ability.Damage;
import me.ollie.capturethewool.core.explosion.Explosion;
import me.ollie.capturethewool.core.util.EntityUtil;
import me.ollie.capturethewool.core.util.CollectionUtil;
import me.ollie.capturethewool.core.util.LocationUtil;
import me.ollie.capturethewool.core.util.VectorUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.util.Vector;

import java.util.List;

public class CannonFireAbility extends Ability {

    private static final World COAST = Bukkit.getWorld("coast");

    public static final List<Location> CANNON_LOCATIONS = List.of(
            new Location(COAST, 1279.5, 128, 1587.5),
            new Location(COAST, 1275.5, 127, 1587.5),
            new Location(COAST, 1271.5, 127, 1586.5),
            new Location(COAST, 1267.5, 127, 1585.5),
            new Location(COAST, 1263.5, 127, 1585.5),
            new Location(COAST, 1259.5, 127, 1585.5),
            new Location(COAST, 1255.5, 127, 1585.5),
            new Location(COAST, 1251.5, 127, 1586.5)
    );

    private final List<Location> locations;

    public CannonFireAbility(List<Location> locations) {
        this.locations = locations;
    }

    @Override
    public void power(LivingEntity user) {
        Location random = CollectionUtil.random(locations);
        EntityUtil.getClosest(user, Player.class)
                .ifPresentOrElse(
                        t -> cannonFire(random, t.v1().getLocation()),
                        () -> cannonFire(random, LocationUtil.randomLocationAround(user.getLocation(), 10)));
    }

    public void cannonFire(Location from, Location to) {
        TNTPrimed primed = from.getWorld().spawn(from, TNTPrimed.class);
        primed.setGravity(false);
        primed.setFuseTicks(1_000_000);

        double distance = to.distance(from);
        double speed = 1.0;
        long time = Math.round(distance / speed);
        Vector trajectory = VectorUtil.trajectory(from.toVector(), to.toVector());

        int taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), () -> {
            primed.setVelocity(trajectory.multiply(speed));
            primed.getLocation().getWorld().spawnParticle(Particle.SMOKE_NORMAL, primed.getLocation(), 5, 1, 1, 1, 0);
        }, 0L, 1L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
            Bukkit.getScheduler().cancelTask(taskId);
            Explosion explosion = new Explosion(to, 2);
            explosion.explode();
            // insert damage here

            Damage.RADIUS.inflict(Damage.Radius.Context.of(to, 3, 8D, List.of(Skeleton.class)));

            primed.remove();
        }, time);
    }
}
