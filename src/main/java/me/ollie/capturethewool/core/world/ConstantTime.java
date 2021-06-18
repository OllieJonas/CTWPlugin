package me.ollie.capturethewool.core.world;

import lombok.Getter;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class ConstantTime {

    public enum Time {
        MORNING(0L),
        MIDDAY(6000L),
        EVENING(12000L),
        NIGHT(18000L);

        @Getter
        private final long time;

        Time(long time) {
            this.time = time;
        }
    }

    private final JavaPlugin plugin;

    private final Map<World, Long> times;

    private Task task;

    public ConstantTime(JavaPlugin plugin) {
        this.plugin = plugin;
        this.times = new HashMap<>();
    }

    public ConstantTime set(World world, Time time) {
        return set(world, time.getTime());
    }

    public ConstantTime set(World world, long time) {
        times.put(world, time);
        return this;
    }

    public void start() {
        this.task = new Task();
        task.runTaskTimer(plugin, 0L, 10L);
    }

    public void cancel() {
        task.cancel();
    }

    private class Task extends BukkitRunnable {

        @Override
        public void run() {
            times.forEach(World::setTime);
        }
    }
}
