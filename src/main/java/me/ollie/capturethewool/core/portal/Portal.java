package me.ollie.capturethewool.core.portal;

import me.ollie.capturethewool.core.GamesCore;
import me.ollie.capturethewool.core.util.region.Region;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

public record Portal(Region region, Location to, Material material) {

    private static final JavaPlugin PLUGIN = GamesCore.getInstance().getPlugin();

    public void init() {
        region.getBlocks().forEach(b -> b.setType(material));
        PLUGIN.getServer().getPluginManager().registerEvents(new Listener(), PLUGIN);
    }

    private class Listener implements org.bukkit.event.Listener {

        @EventHandler
        public void onMove(PlayerMoveEvent event) {
            if (region.includes(event.getPlayer().getLocation())) {
                event.getPlayer().teleport(to);
            }
        }
    }
}
