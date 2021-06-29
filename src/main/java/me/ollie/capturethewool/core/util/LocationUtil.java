package me.ollie.capturethewool.core.util;

import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.Random;

public class LocationUtil {

    private static final Random RANDOM = new Random();

    public static Location behind(Player player, int amount) {
        return player.getLocation().clone().add(player.getLocation().getDirection().normalize().multiply(-1 * amount));
    }

    public static Location randomLocationAround(Location location, int radius) {
        return location.clone().add(RANDOM.nextInt(radius) - (float) (radius / 2), 0, RANDOM.nextInt(radius) - (float) (radius / 2));
    }

    public static Location lower(Location location) {
        return lower(location, 3, 10D);
    }

    public static Location lower(Location location, int startingAdd, double maxDistance) {
        RayTraceResult rayTraceResult = location.getWorld().rayTraceBlocks(location.clone().add(0, startingAdd, 0), new Vector(0, -1, 0), maxDistance, FluidCollisionMode.NEVER, false);

        if (rayTraceResult == null) return location.subtract(0, maxDistance, 0);

        Vector block = rayTraceResult.getHitPosition();

        System.out.println(block);

        return location;
    }

    public static boolean isAdjacent(Location loc1, Location loc2) {
        BlockFace face = loc1.getBlock().getFace(loc2.getBlock());
        return face != null && face.isCartesian() && (face != BlockFace.UP && face != BlockFace.DOWN);
    }

    public static double distanceSquared(Location loc1, Location loc2) {
        return loc1.distanceSquared(loc2);
    }

    public static double distance(Location loc1, Location loc2) {
        return loc1.distance(loc2);
    }

    public static boolean veryWeakEquals(Location loc1, Location loc2) {
        if (loc1.getWorld() != loc2.getWorld()) return false;

        if (loc1.getBlockX() != loc2.getBlockX()) return false;

        if (loc2.getBlockY() != loc2.getBlockY()) return false;

        return loc2.getBlockZ() == loc2.getBlockZ();
    }

    public static boolean veryWeakEqualsIgnoringY(Location loc1, Location loc2) {
        if (loc1.getWorld() != loc2.getWorld()) return false;

        if (loc1.getBlockX() != loc2.getBlockX()) return false;

        return loc2.getBlockZ() == loc2.getBlockZ();
    }

    public static Location floor(Location loc) {
        Location clone = loc.clone();
        return new Location(clone.getWorld(), Math.floor(clone.getX()), Math.floor(clone.getY()), Math.floor(clone.getZ()));
    }

    public static boolean weakEqualsIgnoringY(Location loc1, Location loc2) {
        if (loc1.getWorld() != loc2.getWorld()) return false;

        if (loc1.getX() != loc2.getX()) return false;

        return loc2.getZ() == loc2.getZ();
    }

    public static boolean weakEquals(Location loc1, Location loc2) {
        if (loc1.getWorld() != loc2.getWorld()) return false;

        if (loc1.getX() != loc2.getX()) return false;

        if (loc2.getY() != loc2.getY()) return false;

        return loc2.getZ() == loc2.getZ();
    }
}
