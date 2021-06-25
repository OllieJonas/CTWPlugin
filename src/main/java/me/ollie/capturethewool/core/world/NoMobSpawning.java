package me.ollie.capturethewool.core.world;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class NoMobSpawning extends NoWorldUtility {

    public NoMobSpawning(JavaPlugin plugin) {
        super(plugin);
    }

    @EventHandler
    public void onSpawn(CreatureSpawnEvent event) {

        if (event.getEntity() instanceof Player) return;

        if (worlds.contains(event.getEntity().getWorld().getName()) && event.getSpawnReason() != CreatureSpawnEvent.SpawnReason.CUSTOM) {

            event.getEntity().remove();
            event.setCancelled(true);
        }
    }
}
