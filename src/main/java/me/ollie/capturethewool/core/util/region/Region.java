package me.ollie.capturethewool.core.util.region;

import org.bukkit.Location;
import org.bukkit.Material;

public class Region {

    private Location first;

    private Location second;

    public Region(Location first, Location second) {
        this.first = first;
        this.second = second;
    }

    public static Region expand(Location location, float xzRadius, float yHeight) {
        return new Region(location.clone().add(xzRadius, 0, xzRadius), location.clone().add(-xzRadius, yHeight, -xzRadius));
    }

    public static Region expand(Location location, float radius) {
        float radiusHalved = radius / 2;
        return new Region(location.clone().add(radiusHalved, radiusHalved, radiusHalved), location.clone().add(-radiusHalved, -radiusHalved, -radiusHalved));
    }

    public void draw() {
        RegionUtil.getOutlines(first, second).forEach(l -> first.getWorld().getBlockAt(l).setType(Material.LIME_WOOL));
    }

    public boolean withinRange(Location location, int range) {
        return doIncludes(first.clone().add(range, range, range), second.clone().add(-range, -range, -range), location);
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

    public Location getFirst() {
        return this.first;
    }

    public void setFirst(Location first) {
        this.first = first;
    }

    public Location getSecond() {
        return this.second;
    }

    public void setSecond(Location second) {
        this.second = second;
    }
}
