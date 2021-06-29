package me.ollie.capturethewool.dungeon.puzzles.push.entity;

import lombok.Getter;
import me.ollie.capturethewool.core.util.LocationUtil;
import me.ollie.capturethewool.core.util.VectorUtil;
import me.ollie.capturethewool.dungeon.puzzles.push.lock.PushLock;
import me.ollie.capturethewool.dungeon.puzzles.push.lock.SimplePushLock;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.FallingBlock;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Optional;

public class FallingBlockPushEntity extends PushEntity {

    private final Material material;

    private FallingBlock block;

    public FallingBlockPushEntity(JavaPlugin plugin, Material material) {
        this(plugin, material, new SimplePushLock());
    }

    public FallingBlockPushEntity(JavaPlugin plugin, Material material, PushLock lock) {
        super(plugin, lock);
        this.material = material;
    }

    public FallingBlock setFallingBlock() {
        currLocation.getWorld().getBlockAt(currLocation).setType(Material.AIR);
        FallingBlock block = currLocation.getWorld().spawnFallingBlock(currLocation, material, (byte) 0);
        block.setDropItem(false);
        block.setHurtEntities(false);
        block.setGlowing(true);
        block.setTicksLived(Integer.MAX_VALUE);
        block.setGravity(false);
        return block;
    }

    @Override
    public void spawn() {
        this.block = setFallingBlock();
    }

    @Override
    public void destroy() {
        block.remove();
    }

    @Override
    public Location push(Direction direction) {

        if (lock.isLocked()) return currLocation;

        Optional<Location> locationOptional = raytrace(direction);

        if (locationOptional.isEmpty()) return currLocation;


        Location location = locationOptional.get();
        lock.lock();

        Animation animation = new Animation(plugin, block, location, direction, () -> {
            lock.unlock();
        });
        animation.play(0.3D);



        this.currLocation = location;

        return location;
    }

    private static class Animation {

        private final JavaPlugin plugin;

        private final FallingBlock block;

        private final Location targetLocation;

        private final Runnable onFinish;

        private final double distance;

        private final Vector direction;

        public Animation(JavaPlugin plugin, FallingBlock block, Location targetLocation, Direction direction, Runnable onFinish) {
            this.plugin = plugin;
            this.block = block;
            this.targetLocation = targetLocation;
            this.onFinish = onFinish;

            this.distance = block.getLocation().distance(targetLocation);
            this.direction = direction.getVector();
        }

        public void play(double speed) {
            long time = (long) Math.ceil(distance / speed);
            block.setVelocity(direction.multiply(speed));
            Cancel cancel = new Cancel();
            cancel.runTaskLater(plugin, time);
        }

        private class Cancel extends BukkitRunnable {

            @Override
            public void run() {
                block.teleport(targetLocation);
                block.setVelocity(new Vector(0, 0, 0));
                onFinish.run();
            }
        }
    }
}
