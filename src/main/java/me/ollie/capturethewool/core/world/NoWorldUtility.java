package me.ollie.capturethewool.core.world;

import org.bukkit.World;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

public abstract class NoWorldUtility implements Listener {

    protected final JavaPlugin plugin;

    protected final Collection<String> worlds;

    public NoWorldUtility(JavaPlugin plugin) {
        this.plugin = plugin;
        this.worlds = new HashSet<>();
    }

    public void add(Collection<String> names) {
        worlds.addAll(names);
    }

    public void addWorlds(Collection<World> ws) {
        worlds.addAll(ws.stream().map(World::getName).toList());
    }

    public void add(World... worlds) {
        addWorlds(Arrays.asList(worlds));
    }

    public void add(String... names) {
        add(Arrays.asList(names));
    }

    public void start() {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
}
