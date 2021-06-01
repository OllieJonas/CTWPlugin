package me.ollie.capturethewool.core.hologram;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import lombok.Builder;
import me.ollie.capturethewool.core.hologram.meta.HologramBuilder;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class DroppedItemHologramBuilder {

    private final JavaPlugin plugin;

    private final Location location;

    private ItemStack item;

    private final Set<Player> audience;

    private DroppedItemHologram.OnPickup onPickup;

    private boolean displayTitle = true;

    private DroppedItemHologramBuilder(JavaPlugin plugin, Location location) {
        this.plugin = plugin;
        this.location = location;
        this.audience = new HashSet<>();
    }

    public static DroppedItemHologramBuilder create(JavaPlugin plugin, Location location) {
        return new DroppedItemHologramBuilder(plugin, location);
    }

    public DroppedItemHologramBuilder item(ItemStack item) {
        this.item = item;
        return this;
    }


    public DroppedItemHologramBuilder audience(Collection<? extends Player> audience) {
        this.audience.addAll(audience);
        return this;
    }

    public DroppedItemHologramBuilder audience(Player player) {
        this.audience.add(player);
        return this;
    }

    public DroppedItemHologramBuilder displayTitle(boolean displayTitle) {
        this.displayTitle = displayTitle;
        return this;
    }

    public DroppedItemHologramBuilder onPickup(DroppedItemHologram.OnPickup pickup) {
        this.onPickup = pickup;
        return this;
    }

    public DroppedItemHologram build() {
        return new DroppedItemHologram(plugin, location, displayTitle, item, audience, onPickup);
    }


}
