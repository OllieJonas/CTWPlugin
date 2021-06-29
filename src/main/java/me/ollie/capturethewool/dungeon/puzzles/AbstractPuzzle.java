package me.ollie.capturethewool.dungeon.puzzles;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import lombok.Getter;
import lombok.Setter;
import me.ollie.capturethewool.core.hologram.meta.HologramBuilder;
import me.ollie.capturethewool.core.util.region.Region;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.function.BiFunction;

public abstract class AbstractPuzzle {

    protected final BiFunction<JavaPlugin, Location, Hologram> RESET_HOLOGRAM = (plugin, location) ->
            new HologramBuilder(plugin, location)
                    .line(ChatColor.WHITE + "Click Here to Reset Puzzle", (__, ___) -> reset())
                    .build();

    @Getter
    @Setter
    protected boolean finished = false;

    protected Region region;

    public AbstractPuzzle(Region region) {
        this.region = region;
    }

    public abstract void onFinish();

    public abstract void destroy();

    public abstract void reset();
}
