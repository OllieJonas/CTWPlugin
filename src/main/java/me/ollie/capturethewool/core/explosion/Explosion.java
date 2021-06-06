package me.ollie.capturethewool.core.explosion;

import lombok.Getter;
import me.ollie.capturethewool.core.util.BlockUtil;
import me.ollie.capturethewool.core.util.VectorUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.FallingBlock;
import org.bukkit.scheduler.BukkitRunnable;
import org.jooq.lambda.Seq;
import org.jooq.lambda.tuple.Tuple3;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class Explosion {

    @Getter(lazy = true)
    private static final Janitor janitor = new Janitor();

    private final Location location;

    private final int radius;

    private boolean dealDamage = false;

    public Explosion(Location location, int radius) {
        this.location = location;
        this.radius = radius;
    }

    public void explode() {
        List<FallingBlock> fallingBlocks = BlockUtil.getInRadius(location, radius).keySet().stream()
                .peek(b -> getJanitor().mark(b.getLocation().clone(), b.getType()))
                .map(b -> location.getWorld().spawnFallingBlock(b.getLocation(), b.getBlockData()))
                .peek(fb -> fb.setDropItem(false))
                .collect(Collectors.toList());

        Seq.of(fallingBlocks.toArray(new FallingBlock[0]))
                .zip(fallingBlocks.stream()
                        .map(fb -> VectorUtil.trajectory(location.toVector(), fb.getLocation().toVector()))
                        .map(v -> v.setY(Math.abs(v.getY())))
                        .collect(Collectors.toList()))
                .peek(fb -> getJanitor().mark(fb.v1()))
                .forEach(v -> VectorUtil.velocity(v.v1(),
                        v.v2(),
                        0.5 + 0.25 * Math.random(),
                        false,
                        0,
                        0.4 + 0.20 * Math.random(),
                        10,
                        false));
    }


    public static class Janitor {

        private final Set<FallingBlock> fallingBlocks;

        private final Map<Location, Material> blockData;

        public Janitor() {
            this.fallingBlocks = Collections.newSetFromMap(new ConcurrentHashMap<>());
            this.blockData = new ConcurrentHashMap<>();
        }

        public void mark(FallingBlock block) {
            fallingBlocks.add(block);
        }

        public void mark(Location loc, Material data) {
            blockData.put(loc, data);
        }

        public void cleanup() {
            fallingBlocks.forEach(fb -> fb.getLocation().getBlock().setType(Material.AIR));
            blockData.forEach((k, v) -> k.getBlock().setType(v));
        }
    }
}
