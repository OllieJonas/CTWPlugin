package me.ollie.capturethewool.core.world;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;
import java.util.HashSet;

public class ConstantHunger implements Listener {

    private final JavaPlugin plugin;

    private final Collection<String> worlds;

    public ConstantHunger(JavaPlugin plugin) {
        this.plugin = plugin;
        this.worlds = new HashSet<>();
    }

    public void add(World world) {
        add(world.getName());
    }

    public void add(String name) {
        worlds.add(name);
    }

    public void start() {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onHunger(FoodLevelChangeEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (worlds.contains(player.getWorld().getName())) {
                event.setCancelled(true);
                event.setFoodLevel(20);
            }
        }
    }

}
