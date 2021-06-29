package me.ollie.capturethewool.dungeon.shortcut;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import me.ollie.capturethewool.core.hologram.meta.HologramBuilder;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

public class HologramShortcutKey implements ShortcutKey {

    private JavaPlugin plugin;

    public HologramShortcutKey(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void spawn(Shortcut shortcut, Location location) {
        Hologram hologram = new HologramBuilder(plugin, location).line("Click Here to Unlock Shortcut!", (player, line) -> {}).build();
    }
}
