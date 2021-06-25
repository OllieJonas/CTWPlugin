package me.ollie.capturethewool.core.world;

import org.bukkit.WeatherType;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;
import java.util.HashSet;
import java.util.function.Function;

public class ConstantWeather implements Listener {

    private final JavaPlugin plugin;

    private final Collection<World> weather;

    public ConstantWeather(JavaPlugin plugin) {
        this.plugin = plugin;
        this.weather = new HashSet<>();
    }

    public void start() {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public ConstantWeather add(World world) {
        weather.add(world);
        return this;
    }

    public ConstantWeather addAll() {
        plugin.getServer().getWorlds().forEach(this::add);
        return this;
    }

    @EventHandler
    public void weatherChange(WeatherChangeEvent event) {
        if (weather.contains(event.getWorld())) event.setCancelled(event.toWeatherState());
    }
}
