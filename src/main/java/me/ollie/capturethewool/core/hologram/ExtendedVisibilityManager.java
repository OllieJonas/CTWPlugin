package me.ollie.capturethewool.core.hologram;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.VisibilityManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

public class ExtendedVisibilityManager {

    private JavaPlugin plugin;

    private Hologram hologram;

    private VisibilityManager manager;

    private Set<Player> audience;

    private Map<RefreshTrigger, Set<Predicate<Player>>> conditions;

    public ExtendedVisibilityManager(JavaPlugin plugin, Hologram hologram) {
        this.plugin = plugin;
        this.hologram = hologram;
        this.manager = hologram.getVisibilityManager();
        this.audience = new HashSet<>();
        this.conditions = new HashMap<>();
    }

    public void refresh(RefreshTrigger trigger) {
        audience.forEach(this::hideFrom);
        Bukkit.getOnlinePlayers().stream()
                .filter(p -> conditions.get(trigger).stream()
                        .map(predicate -> predicate.test(p))
                        .allMatch(r -> r.equals(true)))
                .forEach(this::showTo);
    }

    private void showTo(Player player) {
        manager.showTo(player);
    }

    private void hideFrom(Player player) {
        manager.hideTo(player);
        audience.remove(player);
    }
    public enum RefreshTrigger {
        ON_STARTUP,
        ON_PLAYER_JOIN
    }
}
