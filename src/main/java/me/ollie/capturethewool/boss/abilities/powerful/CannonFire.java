package me.ollie.capturethewool.boss.abilities.powerful;

import me.ollie.capturethewool.CaptureTheWool;
import me.ollie.capturethewool.core.ability.Ability;
import me.ollie.capturethewool.core.explosion.Explosion;
import me.ollie.capturethewool.core.util.EntityUtil;
import me.ollie.capturethewool.core.util.ListUtil;
import me.ollie.capturethewool.core.util.LocationUtil;
import me.ollie.capturethewool.core.util.VectorUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.List;

public class CannonFire extends Ability {

    private final List<Location> locations;

    public CannonFire(List<Location> locations) {
        this.locations = locations;
    }

    @Override
    public void damage(DamageContext context) {

    }

    @Override
    public void power(LivingEntity user) {
        Location random = ListUtil.random(locations);
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
        double speed = 0.1;
        long time = Math.round(distance / speed);
        Vector trajectory = VectorUtil.trajectory(from.toVector(), to.toVector());

        primed.setVelocity(trajectory.multiply(speed));
        Bukkit.getScheduler().scheduleSyncDelayedTask(CaptureTheWool.getInstance(), () -> {
            Explosion explosion = new Explosion(to, 3);
            explosion.explode();
            primed.remove();
        }, time);
        // time = distance / speed
    }
}
