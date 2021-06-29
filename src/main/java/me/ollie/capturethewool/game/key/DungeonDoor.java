package me.ollie.capturethewool.game.key;

import me.ollie.capturethewool.Main;
import me.ollie.capturethewool.core.util.control.Pair;
import me.ollie.capturethewool.core.util.region.Region;
import me.ollie.capturethewool.core.util.region.RegionUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.*;
import java.util.stream.Collectors;

public class DungeonDoor {

    private static final Map<DungeonLock, Collection<DungeonDoor>> DOORS = new HashMap<>();

    public static Collection<DungeonDoor> getDoorFrom(DungeonLock lock) {
        return DOORS.get(lock);
    }

    public static void remove(DungeonLock lock) {
        DOORS.remove(lock);
    }

    private final String name;

    private final Region region;

    private final DungeonLock dungeonLock;

    public DungeonDoor(String name, Region region, Location lockLocation, LockType type) {
        this.name = name;
        this.region = region;
        region.getBlocks().forEach(b -> b.setType(Material.DIRT));
        this.dungeonLock = new DungeonLock(lockLocation, type);
    }

    public void init() {
        dungeonLock.init();
        DOORS.putIfAbsent(dungeonLock, new HashSet<>());
        DOORS.get(dungeonLock).add(this);
    }

    public void unlock(Player player, KeyType key) {
        Bukkit.getOnlinePlayers().forEach(p -> p.sendTitle(name + ChatColor.DARK_AQUA + "" + ChatColor.BOLD  + " Unlocked!", ChatColor.AQUA + "Go check it out or smth idk lmao"));
        KeyType.removeFromInventory(player, key);

        playOpeningEffect();
    }

    private void playOpeningEffect() {
        Pair<Location, Location> minLocs = minLocations();
        List<Block> floor = RegionUtil.getBlocks(minLocs.getVal1(), minLocs.getVal2());
        Map<Location, Material> floorMaterials = floor.stream().collect(Collectors.toUnmodifiableMap(Block::getLocation, Block::getType));
        floor.forEach(b -> b.setType(Material.AIR));

        List<FallingBlock> fallingBlocks = region.getBlocks().stream()
                .map(b -> b.getLocation().getWorld().spawnFallingBlock(b.getLocation(), b.getBlockData()))
                .peek(fb -> fb.setHurtEntities(false))
                .peek(fb -> fb.setDropItem(false))
                .collect(Collectors.toList());

        region.getBlocks().forEach(b -> b.setType(Material.AIR));
        Vector down = new Vector(0, -1, 0).multiply(0.1);

        int taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), () -> fallingBlocks.forEach(fb -> fb.setVelocity(down)), 0L, 1L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
            Bukkit.getScheduler().cancelTask(taskId);
            fallingBlocks.forEach(Entity::remove);
            floorMaterials.forEach((k, v) -> k.getWorld().getBlockAt(k).setType(v));
        }, 40L);
    }

    private Pair<Location, Location> minLocations() {
        double minY = Math.min(region.getFirst().getY(), region.getSecond().getY()) - 1;

        Location loc1 = region.getFirst().clone();
        Location loc2 = region.getSecond().clone();

        loc1.setY(minY);
        loc2.setY(minY);
        return new Pair<>(loc1, loc2);
    }
}
