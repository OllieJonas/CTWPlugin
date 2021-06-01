package me.ollie.capturethewool.core.hologram;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import lombok.Builder;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class PaginatedHologramBuilder {

    private String title;

    private JavaPlugin plugin;

    private Location pagesLocation;

    private List<Hologram> holograms;

    private Location prevButton;

    private Location nextButton;

    public PaginatedHologramBuilder(JavaPlugin plugin) {
        this.plugin = plugin;
        this.holograms = new ArrayList<>();
    }

    public PaginatedHologramBuilder title(String title) {
        this.title = title;
        return this;
    }

    public PaginatedHologramBuilder pagesLocation(Location location) {
        this.pagesLocation = location;
        return this;
    }

    public PaginatedHologramBuilder prevButton(Location prevButton) {
        this.prevButton = prevButton;
        return this;
    }

    public PaginatedHologramBuilder nextButton(Location next) {
        this.nextButton = next;
        return this;
    }

    public PaginatedHologramBuilder page(Hologram page) {
        holograms.add(page);
        return this;
    }

    public PaginatedHologramBuilder pages(List<Hologram> page) {
        holograms.addAll(page);
        return this;
    }

    public PaginatedHologram build() {
        return new PaginatedHologram(title, plugin, pagesLocation, holograms, prevButton, nextButton);
    }
}
