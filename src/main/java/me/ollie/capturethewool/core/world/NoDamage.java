package me.ollie.capturethewool.core.world;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class NoDamage extends NoWorldUtility {

    public NoDamage(JavaPlugin plugin) {
        super(plugin);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (worlds.contains(player.getWorld().getName())) {
                event.setCancelled(true);
                event.setDamage(0);
            }
        }
    }
}
