package me.ollie.capturethewool.core.world;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class WorldUtilities {

    private final JavaPlugin plugin;

    private final ConstantTime constantTime;

    private final ConstantWeather constantWeather;

    private final ConstantHunger constantHunger;

    private final NoDamage noDamage;

    private final NoBlockInteraction noBlockInteraction;

    private final NoMobSpawning noMobSpawning;

    public WorldUtilities(JavaPlugin plugin) {
        this.plugin = plugin;
        this.constantTime = new ConstantTime(plugin);
        this.constantWeather = new ConstantWeather(plugin);
        this.constantHunger = new ConstantHunger(plugin);
        this.noDamage = new NoDamage(plugin);
        this.noBlockInteraction = new NoBlockInteraction(plugin);
        this.noMobSpawning = new NoMobSpawning(plugin);
    }

    public void init() {
        constantTime.start();
        constantWeather.start();
        constantHunger.start();
        noDamage.start();
        noBlockInteraction.start();
        noMobSpawning.start();
    }

    public void cancel() {
        constantTime.cancel();
    }


}
