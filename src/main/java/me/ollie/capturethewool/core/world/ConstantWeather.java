package me.ollie.capturethewool.core.world;

import org.bukkit.WeatherType;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class ConstantWeather implements Listener {

    private final JavaPlugin plugin;

    private final Map<World, Function<World, World>> weather;

    public ConstantWeather(JavaPlugin plugin) {
        this.plugin = plugin;
        this.weather = new HashMap<>();
    }

    public void start() {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public ConstantWeather set(World world, WeatherType type) {
        weather.put(world, action(type));
        return this;
    }

    public ConstantWeather setAll(WeatherType type) {
        plugin.getServer().getWorlds().forEach(w -> set(w, type));
        return this;
    }

    @EventHandler
    public void weatherChange(WeatherChangeEvent event) {
        weather.computeIfPresent(event.getWorld(), (__, f) -> f);
    }

    private static Function<World, World> action(WeatherType type) {
        return switch (type) {
            case CLEAR -> world -> {
                world.setStorm(false);
                world.setClearWeatherDuration(Integer.MAX_VALUE);
                return world;
            };

            case DOWNFALL -> world -> {
                world.setStorm(true);
                world.setWeatherDuration(Integer.MAX_VALUE);
                return world;
            };
        };
    }


}
