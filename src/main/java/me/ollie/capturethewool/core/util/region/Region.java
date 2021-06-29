package me.ollie.capturethewool.core.util.region;

import lombok.Getter;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.jooq.lambda.tuple.Tuple2;
import org.jooq.lambda.tuple.Tuple6;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class Region implements Cloneable {

    private Location first;

    private Location second;

    private int xSize;

    private int ySize;

    private int zSize;

    public Region(Location first, Location second) {
        this.first = first;
        this.second = second;

        updateSizes();

        System.out.println("x: " + xSize + "y: " + ySize + "z: " + zSize);
    }

    public static Region expand(Location location, float xzRadius, float yHeight) {
        return new Region(location.clone().add(xzRadius, 0, xzRadius), location.clone().add(-xzRadius, yHeight, -xzRadius));
    }

    public static Region expand(Location location, float radius) {
        float radiusHalved = radius / 2;
        return new Region(location.clone().add(radiusHalved, radiusHalved, radiusHalved), location.clone().add(-radiusHalved, -radiusHalved, -radiusHalved));
    }

    public void draw() {
        getOutlines().forEach(l -> first.getWorld().getBlockAt(l).setType(Material.LIME_WOOL));
    }

    public boolean withinRange(Location location, int range) {
        return doIncludes(first.clone().add(range, range, range), second.clone().add(-range, -range, -range), location);
    }

    public Location midpoint() {
        return new Location(Bukkit.getWorld("world"),
                first.getX() + second.getX() / 2,
                first.getY() + second.getY() / 2,
                first.getZ() + second.getZ() / 2);
    }

    public List<Location> getFaces() {
        Assertions.assertThat(first.getWorld()).isEqualTo(first.getWorld());

        List<Location> locations = new ArrayList<>();

        Tuple6<Integer, Integer, Integer, Integer, Integer, Integer> ctx = getContext(first, second);

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
                        locations.add(new Location(first.getWorld(), x, y, z));
                    }
                    if (y == bottomBlockY || y == topBlockY) {
                        locations.add(new Location(first.getWorld(), x, y, z));
                    }
                    if (z == bottomBlockZ || z == topBlockZ) {
                        locations.add(new Location(first.getWorld(), x, y, z));
                    }

                }
            }
        }
        return locations;
    }

    public List<Location> getOutlines() {
        Assertions.assertThat(first.getWorld()).isEqualTo(second.getWorld());

        List<Location> locations = new ArrayList<>();

        Tuple6<Integer, Integer, Integer, Integer, Integer, Integer> ctx = getContext(first, second);

        int bottomBlockX = ctx.v1();
        int topBlockX = ctx.v2();

        int topBlockY = ctx.v3();
        int bottomBlockY = ctx.v4();

        int topBlockZ = ctx.v5();
        int bottomBlockZ = ctx.v6();

        for (int x = bottomBlockX; x <= topBlockX; x++) {
            for (int y = bottomBlockY; y <= topBlockY; y++) {
                for (int z = bottomBlockZ; z <= topBlockZ; z++) {

                    fun(first.getWorld(), locations, topBlockX, bottomBlockX, bottomBlockY, topBlockZ, bottomBlockZ, x, y, z);

                    fun(first.getWorld(), locations, topBlockX, bottomBlockX, topBlockY, topBlockZ, bottomBlockZ, x, y, z);

                    if ((x == bottomBlockX && z == bottomBlockZ) || (x == topBlockX && z == topBlockZ) || (x == bottomBlockX && z == topBlockZ) || (x == topBlockX && z == bottomBlockZ)) {
                        locations.add(new Location(first.getWorld(), x, y, z));
                    }
                }
            }
        }
        return locations;
    }

    public List<Block> getBlocks(Material type) {
        return getBlocks().stream().filter(b -> b.getType() == type).toList();
    }

    public List<Block> getBlocks() {
        List<Block> blocks = new ArrayList<>();

        Tuple6<Integer, Integer, Integer, Integer, Integer, Integer> ctx = getContext(first, second);

        int bottomBlockX = ctx.v1();
        int topBlockX = ctx.v2();

        int topBlockY = ctx.v3();
        int bottomBlockY = ctx.v4();

        int topBlockZ = ctx.v5();
        int bottomBlockZ = ctx.v6();

        for (int x = bottomBlockX; x <= topBlockX; x++) {
            for (int y = bottomBlockY; y <= topBlockY; y++) {
                for (int z = bottomBlockZ; z <= topBlockZ; z++) {

                    Block block = first.getWorld().getBlockAt(x, y, z);
                    blocks.add(block);

                }
            }
        }
        return Collections.unmodifiableList(blocks);
    }

    private static void fun(World world, List<Location> blocks, int topBlockX, int bottomBlockX, int bottomBlockY, int topBlockZ, int bottomBlockZ, int x, int y, int z) {
        if ((x == bottomBlockX && y == bottomBlockY) || (x == topBlockX && y == bottomBlockY)) {
            blocks.add(new Location(world, x, y, z));
        }

        if ((z == bottomBlockZ && y == bottomBlockY) || (z == topBlockZ && y == bottomBlockY)) {
            blocks.add(new Location(world, x, y, z));
        }
    }

    public static Tuple6<Integer, Integer, Integer, Integer, Integer, Integer> getContext(Location l1, Location l2) {
        return new Tuple6<>(
                Math.min(l1.getBlockX(), l2.getBlockX()),
                Math.max(l1.getBlockX(), l2.getBlockX()),

                Math.max(l1.getBlockY(), l2.getBlockY()),
                Math.min(l1.getBlockY(), l2.getBlockY()),

                Math.max(l1.getBlockZ(), l2.getBlockZ()),
                Math.min(l1.getBlockZ(), l2.getBlockZ()));
    }

    public boolean includes(Location location) {
        return doIncludes(first, second, location);
    }

    private boolean doIncludes(Location first, Location second, Location location) {
        if (location.getWorld().getUID() != this.first.getWorld().getUID() || location.getWorld().getUID() != this.second.getWorld().getUID()) {
            return false;
        }
        final int x1 = first.getBlockX();
        final int x2 = second.getBlockX();
        final int y1 = first.getBlockY();
        final int y2 = second.getBlockY();
        final int z1 = first.getBlockZ();
        final int z2 = second.getBlockZ();
        return between(location.getBlockX(), x1, x2) && between(location.getBlockY(), y1, y2) && between(location.getBlockZ(), z1, z2);
    }

    private boolean between(int target, int i, int j) {
        return (target < i && target > j) || (target > i && target < j);
    }

    public Tuple2<Location, Location> getLocations() {
        return new Tuple2<>(first, second);
    }

    public World getWorld() {
        return first.getWorld();
    }

    public double getMinX() {
        return Math.min(first.getX(), second.getX());
    }

    public double getMinY() {
        return Math.min(first.getY(), second.getY());
    }

    public double getMinZ() {
        return Math.min(first.getZ(), second.getZ());
    }

    public double getMaxX() {
        return Math.max(first.getX(), second.getX());
    }

    public double getMaxY() {
        return Math.max(first.getY(), second.getY());
    }

    public double getMaxZ() {
        return Math.max(first.getZ(), second.getZ());
    }

    public void setFirst(Location first) {
        this.first = first;
        updateSizes();
    }

    public void setSecond(Location second) {
        this.second = second;
        updateSizes();
    }

    @SuppressWarnings("MethodDoesntCallSuperMethod")
    public Region clone() {
        return new Region(first.clone(), second.clone());
    }


    public Region shiftX(float amount) {
        return new Region(first.clone().add(amount, 0, 0), second.clone().add(amount, 0, 0));
    }

    public Region shiftY(float amount) {
        return new Region(first.clone().add(0, amount, 0), second.clone().add(0, amount, 0));
    }

    public Region shiftZ(float amount) {
        return new Region(first.clone().add(0, 0, amount), second.clone().add(0, 0, amount));
    }

    public void updateSizes() {
        updateXSize();
        updateYSize();
        updateZSize();
    }

    private void updateXSize() {
        this.xSize = (int) Math.abs(first.getX() - second.getX());
    }

    private void updateYSize() {
        this.ySize = (int) Math.abs(first.getY() - second.getY());
    }

    private void updateZSize() {
        this.zSize = (int) Math.abs(first.getZ() - second.getZ());
    }
}
