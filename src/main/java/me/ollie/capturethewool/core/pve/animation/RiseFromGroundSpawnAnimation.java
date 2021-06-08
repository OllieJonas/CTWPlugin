package me.ollie.capturethewool.core.pve.animation;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;

public class RiseFromGroundSpawnAnimation implements SpawnAnimation {

    private static final double speed = 0.1;

    private static final double distance = 2.5D;

    private Location particles;

    private Location location;

    @Override
    public Location location(Location location) {
        this.particles = location.clone().add(0, 0.5, 0);
        this.location = location.clone().subtract(0, distance, 0);
        return this.location;
    }

    @Override
    public void tick(LivingEntity entity) {
        location.add(0, speed, 0);

        entity.teleport(location);
        location.getWorld().spawnParticle(Particle.BLOCK_DUST, particles, 10, 0.1, 0.1, 0.1, Material.BONE_BLOCK.createBlockData());
    }

    @Override
    public void onFinish(LivingEntity entity) {
        entity.setInvulnerable(false);
    }

    @Override
    public long animationLength() {
        return Math.round(distance / speed);
    }
}
