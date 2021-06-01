package me.ollie.capturethewool.core.util.region;

import lombok.experimental.UtilityClass;
import org.assertj.core.api.Assertions;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.jooq.lambda.Seq;
import org.jooq.lambda.tuple.Tuple2;
import org.jooq.lambda.tuple.Tuple3;
import org.jooq.lambda.tuple.Tuple6;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@SuppressWarnings("DuplicatedCode")
@UtilityClass
public class LocationUtil {

    public static List<Location> getFaces(Location l1, Location l2) {
        Assertions.assertThat(l1.getWorld()).isEqualTo(l2.getWorld());

        List<Location> locations = new ArrayList<>();

        Tuple6<Integer, Integer, Integer, Integer, Integer, Integer> ctx = getContext(l1, l2);

        int bottomBlockX = ctx.v1();
        int topBlockX = ctx.v2();

        int topBlockY = ctx.v3();
        int bottomBlockY = ctx.v4();

        int topBlockZ = ctx.v5();
        int bottomBlockZ = ctx.v6();

        for (int x = bottomBlockX; x <= topBlockX; x++) {
            for (int y = bottomBlockY; y <= topBlockY; y++) {
                for (int z = bottomBlockZ; z <= topBlockZ; z++) {
                    if (x == bottomBlockX || x == topBlockX) {
                        locations.add(new Location(l1.getWorld(), x, y, z));
                    }
                    if (y == bottomBlockY || y == topBlockY) {
                        locations.add(new Location(l1.getWorld(), x, y, z));
                    }
                    if (z == bottomBlockZ || z == topBlockZ) {
                        locations.add(new Location(l1.getWorld(), x, y, z));
                    }

                }
            }
        }
        return locations;
    }

    public static List<Location> getOutlines(Location l1, Location l2) {
        Assertions.assertThat(l1.getWorld()).isEqualTo(l2.getWorld());

        List<Location> locations = new ArrayList<>();

        Tuple6<Integer, Integer, Integer, Integer, Integer, Integer> ctx = getContext(l1, l2);

        int bottomBlockX = ctx.v1();
        int topBlockX = ctx.v2();

        int topBlockY = ctx.v3();
        int bottomBlockY = ctx.v4();

        int topBlockZ = ctx.v5();
        int bottomBlockZ = ctx.v6();

        for (int x = bottomBlockX; x <= topBlockX; x++) {
            for (int y = bottomBlockY; y <= topBlockY; y++) {
                for (int z = bottomBlockZ; z <= topBlockZ; z++) {

                    fun(l1.getWorld(), locations, topBlockX, bottomBlockX, bottomBlockY, topBlockZ, bottomBlockZ, x, y, z);

                    fun(l1.getWorld(), locations, topBlockX, bottomBlockX, topBlockY, topBlockZ, bottomBlockZ, x, y, z);

                    if ((x == bottomBlockX && z == bottomBlockZ) || (x == topBlockX && z == topBlockZ) || (x == bottomBlockX && z == topBlockZ) || (x == topBlockX && z == bottomBlockZ)) {
                        locations.add(new Location(l1.getWorld(), x, y, z));
                    }
                }
            }
        }
        return locations;
    }

    private static void fun(World world, List<Location> blocks, int topBlockX, int bottomBlockX, int bottomBlockY, int topBlockZ, int bottomBlockZ, int x, int y, int z) {
        if ((x == bottomBlockX && y == bottomBlockY) || (x == topBlockX && y == bottomBlockY)) {
            blocks.add(new Location(world, x, y, z));
        }

        if ((z == bottomBlockZ && y == bottomBlockY) || (z == topBlockZ && y == bottomBlockY)) {
            blocks.add(new Location(world, x, y, z));
        }
    }

    private static Tuple6<Integer, Integer, Integer, Integer, Integer, Integer> getContext(Location l1, Location l2) {
        return new Tuple6<>(
                Math.min(l1.getBlockX(), l2.getBlockX()),
                Math.max(l1.getBlockX(), l2.getBlockX()),

                Math.max(l1.getBlockY(), l2.getBlockY()),
                Math.min(l1.getBlockY(), l2.getBlockY()),

                Math.max(l1.getBlockZ(), l2.getBlockZ()),
                Math.min(l1.getBlockZ(), l2.getBlockZ()));
    }

    public static List<Block> getBlocksBetweenPoints(Location l1, Location l2) {
        List<Block> blocks = new ArrayList<>();

        Tuple6<Integer, Integer, Integer, Integer, Integer, Integer> ctx = getContext(l1, l2);

        int bottomBlockX = ctx.v1();
        int topBlockX = ctx.v2();

        int topBlockY = ctx.v3();
        int bottomBlockY = ctx.v4();

        int topBlockZ = ctx.v5();
        int bottomBlockZ = ctx.v6();

        for (int x = bottomBlockX; x <= topBlockX; x++) {
            for (int y = bottomBlockY; y <= topBlockY; y++) {
                for (int z = bottomBlockZ; z <= topBlockZ; z++) {

                    Block block = l1.getWorld().getBlockAt(x, y, z);
                    blocks.add(block);

                }
            }
        }
        return blocks;
    }

    public static boolean isSafe(Location location) {
        try {
            Block feet = location.getBlock();
            if (!feet.getType().isTransparent() && !feet.getLocation().add(0, 1, 0).getBlock().getType().isTransparent()) {
                return false; // not transparent (will suffocate)
            }
            Block head = feet.getRelative(BlockFace.UP);
            if (!head.getType().isTransparent()) {
                return false; // not transparent (will suffocate)
            }
            Block ground = feet.getRelative(BlockFace.DOWN);
            // returns if the ground is solid or not.
            return ground.getType().isSolid();
        } catch (Exception ignored) {

        }
        return false;
    }

    public static boolean veryWeakEquals(Location loc1, Location loc2) {
        if (loc1.getWorld() != loc2.getWorld()) return false;

        if (loc1.getBlockX() != loc2.getBlockX()) return false;

        if (loc2.getBlockY() != loc2.getBlockY()) return false;

        if (loc2.getBlockZ() != loc2.getBlockZ()) return false;

        return true;
    }
    public static boolean weakEquals(Location loc1, Location loc2) {
        if (loc1.getWorld() != loc2.getWorld()) return false;

        if (loc1.getX() != loc2.getX()) return false;

        if (loc2.getY() != loc2.getY()) return false;

        if (loc2.getZ() != loc2.getZ()) return false;

        return true;
    }
}
