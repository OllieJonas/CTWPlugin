package me.ollie.capturethewool.core.util;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public class HolographicDamageListener implements Listener {

    private static final Random RANDOM = new Random();

    private final JavaPlugin plugin;

    private boolean isActive;

    public HolographicDamageListener(JavaPlugin plugin) {
        this.plugin = plugin;
        this.isActive = false;
    }

    public void toggle() {
        isActive = !isActive;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onDamage(EntityDamageEvent event) {
        if (!isActive) return;

        Entity target = event.getEntity();

        Location hologramLocation = randomOffset(target.getLocation().clone(), target.getHeight());
        Hologram hologram = HologramsAPI.createHologram(plugin, hologramLocation);
        hologram.appendTextLine(ChatColor.WHITE + String.valueOf(MathsUtil.round(event.getDamage(), 1)) + " " + ChatColor.RED + "✦");

        if (target instanceof Player) {
            hologram.getVisibilityManager().hideTo((Player) target);
        }
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, hologram::delete, 15L);
    }

    private Location randomOffset(Location targetLocation, double targetHeight) {
        return targetLocation.add(1 - (2 * RANDOM.nextDouble()), targetHeight / 1.1, 1 - (2 * RANDOM.nextDouble()));
    }
}
