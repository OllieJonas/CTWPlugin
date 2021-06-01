package me.ollie.capturethewool.core.util.particles;

import lombok.Getter;
import lombok.Setter;
import me.ollie.capturethewool.core.util.region.Region;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public class ActionOnEnterVortex {

    private JavaPlugin plugin;

    private Particle particle;

    private Location location;

    private int height;

    private int radius;

    private int noSpirals;

    private long onEnterFrequency;

    @Getter
    private Consumer<LivingEntity> whilstInside;

    @Getter
    private List<Player> inside;

    private Region region;

    private Listener listener;

    private Vortex vortex;

    private ActionTask task;

    @Setter
    private boolean rainbow = false;

    @Setter
    private boolean descending = false;

    public ActionOnEnterVortex(JavaPlugin plugin, Particle particle, Location location, int height, int radius, int noSpirals, Consumer<LivingEntity> whilstInside) {
        this(plugin, particle, location, height, radius, noSpirals, whilstInside, 1);
    }
    public ActionOnEnterVortex(JavaPlugin plugin, Particle particle, Location location, int height, int radius, int noSpirals, Consumer<LivingEntity> whilstInside, long onEnterFrequency) {
        this.plugin = plugin;
        this.location = location.clone();
        this.particle = particle;
        this.radius = radius;
        this.height = height;
        this.noSpirals = noSpirals;
        this.whilstInside = whilstInside;
        this.onEnterFrequency = onEnterFrequency;
        this.region = Region.expand(location, radius, height);
        this.listener = new Listener(this);
        this.inside = new CopyOnWriteArrayList<>();
        this.task = new ActionTask(this);
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
    }

    public void run() {
        vortex = new Vortex(location, particle, height, radius, noSpirals, descending);
        vortex.rainbow(rainbow);
        vortex.runTaskTimer(plugin, 0L, 1L);
        task.runTaskTimer(plugin, 0L, onEnterFrequency);
    }

    public void destroy() {
        vortex.destroy();
        task.cancel();
        PlayerMoveEvent.getHandlerList().unregister(listener);
        this.plugin = null;
        this.location = null;
        this.particle = null;
        this.whilstInside = null;
        this.region = null;
        this.listener = null;
        this.height = 0;
        this.noSpirals = 0;
        this.radius = 0;
        this.inside = null;
        this.onEnterFrequency = 0;
        this.vortex = null;
        this.task = null;
    }

    public boolean isInside(Player player) {
        return region.includes(player.getLocation());
    }

    private static class Listener implements org.bukkit.event.Listener {
        private final ActionOnEnterVortex actionOnEnterVortex;

        public Listener(ActionOnEnterVortex actionOnEnterVortex) {
            this.actionOnEnterVortex = actionOnEnterVortex;
        }

        @EventHandler
        public void onMove(PlayerMoveEvent event) {
            Player player = event.getPlayer();
            if (actionOnEnterVortex.getInside().contains(player) && !actionOnEnterVortex.isInside(player)) {
                actionOnEnterVortex.getInside().remove(player);
                return;
            }

            if (actionOnEnterVortex.isInside(player) && !actionOnEnterVortex.getInside().contains(player)) {
                actionOnEnterVortex.getInside().add(player);
            }
        }
    }

    private static class ActionTask extends BukkitRunnable {

        private final ActionOnEnterVortex vortex;

        public ActionTask(ActionOnEnterVortex vortex) {
            this.vortex = vortex;
        }

        @Override
        public void run() {
            vortex.getInside().forEach(p -> vortex.getWhilstInside().accept(p));
        }
    }
}
