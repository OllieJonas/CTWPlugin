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
import org.bukkit.util.Vector;
import org.jooq.lambda.Seq;
import org.jooq.lambda.tuple.Tuple2;
import org.jooq.lambda.tuple.Tuple3;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    // this is practically unreadable but hey its fun to do one-liners right? :D
    public void explode() {
        Seq.seq(BlockUtil.getInRadius(location, radius).keySet())
                .peek(b -> getJanitor().mark(b.getLocation().clone(), b.getType())) // mark block for cleanup
                .map(b -> location.getWorld().spawnFallingBlock(b.getLocation(), b.getBlockData())) // spawn falling block
                .peek(fb -> fb.setDropItem(false)) // cant drop item
                .duplicate() // duplicate stream of falling blocks
                .map((fb1, fb2) -> new Tuple2<>(fb1, fb2
                        .map(fb -> VectorUtil.trajectory(location.toVector(), fb.getLocation().toVector()))
                        .map(v -> v.setY(Math.abs(v.getY())))))  // map second sequence to vectors for falling blocks
                .map((fallingBlocks, vectors) -> Seq.zip(fallingBlocks, vectors)) // zip them
                .peek(fb -> getJanitor().mark(fb.v1())) // mark new falling block
                .forEach(v -> VectorUtil.velocity(v.v1(), // set velocity of falling block to vector
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
