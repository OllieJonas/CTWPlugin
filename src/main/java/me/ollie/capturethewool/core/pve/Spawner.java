package me.ollie.capturethewool.core.pve;

import me.ollie.capturethewool.core.util.region.LocationUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class Spawner<T extends Enemy<?>> {

    private static final Random RANDOM = new Random();

    private static int SPAWNER_COUNT = 0;

    private final int id;

    private final JavaPlugin plugin;

    private final Supplier<T> enemy;

    private final Location location;

    private final int range;

    private final int maxMobs;


    private boolean isActive;

    private int activeMobs;

    private final BukkitRunnable task;

    public Spawner(JavaPlugin plugin, Location location, Supplier<T> enemy, int range, int maxMobs) {
        this.location = location;
        this.plugin = plugin;
        this.enemy = enemy;
        this.range = range;
        this.maxMobs = maxMobs;

        this.id = SPAWNER_COUNT++;
        this.isActive = true;
        this.activeMobs = 0;
        this.task = new Task();

        plugin.getServer().getPluginManager().registerEvents(new Listener(), plugin);
    }

    public void place() {
        location.getWorld().getBlockAt(location).setType(Material.SPAWNER);
        task.runTaskTimer(plugin, 0L, 100L);
    }

     private void spawnMobs(int noSpawns) {
        List<Location> spawns = new ArrayList<>();
        int successfulSpawns = 0;
        int rangeRounded = Math.round((float) range / 2);

        while (successfulSpawns < noSpawns) {
            Location randomLocation = location.clone().add(rangeRounded - RANDOM.nextInt(range), (float) RANDOM.nextInt(range) / 2, rangeRounded - RANDOM.nextInt(range));
            spawns.add(randomLocation);
            successfulSpawns++;
        }

        activeMobs += noSpawns;

        spawns.forEach(l -> {
            LivingEntity entity = enemy.get().spawn(l);
            entity.setMetadata("mobSpawner", new FixedMetadataValue(plugin, id));
            l.getWorld().spawnParticle(Particle.FLAME, l, 3, 1, 1, 1, 0);
        });
    }

    private class Listener implements org.bukkit.event.Listener {

        @EventHandler
        public void onBreak(BlockBreakEvent event) {
            Location loc = event.getBlock().getLocation();
            if (LocationUtil.veryWeakEquals(loc, location)) {
                isActive = false;
            }
        }

        @EventHandler
        public void onDeath(EntityDeathEvent event) {
            Entity entity = event.getEntity();
            if (!entity.hasMetadata("mobSpawner")) return;
            int mobId = entity.getMetadata("mobSpawner").get(0).asInt();
            if (mobId == id) {
                activeMobs--;
            }
        }
    }
    private class Task extends BukkitRunnable {

        @Override
        public void run() {
            if (!isActive) {
                cancel();
                return;
            }

            if (activeMobs >= maxMobs) return;

            int noSpawns = RANDOM.nextInt(Math.round((float) maxMobs / 2)) + 1;

            if (noSpawns + activeMobs >= maxMobs) noSpawns = maxMobs - activeMobs;

            spawnMobs(noSpawns);
        }
    }
}
