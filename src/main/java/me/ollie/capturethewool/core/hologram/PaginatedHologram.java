package me.ollie.capturethewool.core.hologram;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import me.ollie.capturethewool.core.hologram.meta.HologramBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class PaginatedHologram {

    private final Map<Player, Integer> currPage;

    private final String title;

    private final JavaPlugin plugin;

    private final Location pagesLocation;

    private final List<Hologram> holograms;

    private final Hologram prevButton;

    private final Hologram nextButton;

    private final int noPages;

    private final int startingPage = 1;

    public PaginatedHologram(String title, JavaPlugin plugin, Location pagesLocation, List<Hologram> holograms, Location prevButton, Location nextButton) {
        this.title = title;
        this.plugin = plugin;
        this.pagesLocation = pagesLocation;
        this.holograms = transform(holograms);
        this.prevButton = prev(prevButton);
        this.nextButton = next(nextButton);

        this.currPage = initCurrPage();
        this.noPages = holograms.size();

        init();
        plugin.getServer().getPluginManager().registerEvents(new Listener(), plugin);
    }

    public void init() {
        currPage.forEach(this::setCurrPage);
    }

    private Map<Player, Integer> initCurrPage() {
        return Bukkit.getOnlinePlayers().stream().collect(Collectors.toMap(p -> p, p -> startingPage));
    }

    private Hologram prev(Location loc) {
        return new HologramBuilder(plugin, loc).text(ChatColor.AQUA + "← Previous", (p, l) -> prev(p)).build();
    }

    private Hologram next(Location loc) {
        return new HologramBuilder(plugin, loc).text(ChatColor.AQUA + "Next →", (p, l) -> next(p)).build();
    }

    public void next(Player player) {
        setCurrPage(player, Math.min(noPages, currPage.get(player) + 1));
    }

    public void prev(Player player) {
        setCurrPage(player, Math.max(1, currPage.get(player) - 1));
    }

    private void setCurrPage(Player player, int newPage) {
        if (!currPage.containsKey(player)) currPage.put(player, startingPage);

        int curr = currPage.get(player);

        prevButton.getVisibilityManager().showTo(player);
        nextButton.getVisibilityManager().showTo(player);

        if (newPage == 1) prevButton.getVisibilityManager().hideTo(player);
        if (newPage == noPages) nextButton.getVisibilityManager().hideTo(player);

        holograms.get(curr - 1).getVisibilityManager().hideTo(player);
        holograms.get(newPage - 1).getVisibilityManager().showTo(player);
        currPage.put(player, newPage);
    }

    private List<Hologram> transform(List<Hologram> holograms) {
        AtomicInteger count = new AtomicInteger(1);
        int size = holograms.size();
        return holograms.stream()
                .peek(h -> h.insertTextLine(0, title + ChatColor.DARK_GRAY + " (" + ChatColor.AQUA + count.getAndIncrement() + ChatColor.GRAY + " / " + ChatColor.AQUA + size + ChatColor.DARK_GRAY + ")"))
                .peek(h -> h.teleport(pagesLocation))
                .peek(h -> h.getVisibilityManager().setVisibleByDefault(false))
                .collect(Collectors.toList());
    }

    private class Listener implements org.bukkit.event.Listener {

        @EventHandler
        public void onJoin(PlayerJoinEvent event) {
            setCurrPage(event.getPlayer(), startingPage);
        }

        @EventHandler
        public void onLeave(PlayerQuitEvent event) {
            leave(event.getPlayer());
        }

        @EventHandler
        public void onKick(PlayerKickEvent event) {
            leave(event.getPlayer());
        }

        private void leave(Player player) {
            Integer curr = currPage.get(player);
            if (curr == null) return;

            holograms.get(curr).getVisibilityManager().hideTo(player);
            currPage.remove(player);
        }
    }
}
