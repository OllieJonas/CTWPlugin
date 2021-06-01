package me.ollie.capturethewool.core.cooldown.progress;

import lombok.Getter;
import me.ollie.capturethewool.core.GamesCore;
import me.ollie.capturethewool.core.cooldown.CooldownManager;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class ProgressBarManager {

    @Getter(lazy = true)
    private static final ProgressBarManager instance = new ProgressBarManager();

    private final Map<Player, ProgressBar> activeProgressBar;

    private final Map<Player, Map<String, ProgressBarState>> states;

    private ProgressBarManager() {
        this.activeProgressBar = new HashMap<>();
        this.states = new HashMap<>();
    }

    public void remove(Player player, String label) {
        if (activeProgressBar.get(player) != null && activeProgressBar.get(player).getLabel().equals(label)) {
            activeProgressBar.remove(player);
        }

        states.get(player).remove(label);
    }

    public void hide(Player player) {
        shutdownCurrentActiveBar(player);
    }

    public void removeActive(Player player) {
        ProgressBar active = activeProgressBar.get(player);
        if (active != null) {
            String label = activeProgressBar.get(player).getLabel();
            activeProgressBar.remove(player);
            states.get(player).remove(label);
        }
    }

    public void show(Player player, String label) {
        if (activeProgressBar.get(player) != null && activeProgressBar.get(player).getLabel().equals(label))
            return;


        ProgressBarState newState = states.get(player).get(label);
        if (!shutdownCurrentActiveBar(player)) return;

        if (newState == null)
            return;

        ProgressBar bar = newState.toProgressBar(GamesCore.getInstance().getPlugin(), player, CooldownManager.getInstance().getCounter());
        activeProgressBar.put(player, bar);
        bar.run();
    }

    public void add(Player player, ProgressBar progressBar) {
        if (!states.containsKey(player))
            states.put(player, new HashMap<>());

        if (states.get(player).get(progressBar.getLabel()) != null)
            return;

        states.get(player).put(progressBar.getLabel(), ProgressBarState.from(progressBar, CooldownManager.getInstance().getCounter()));
    }

    public void addAndShow(Player player, ProgressBar progressBar) {
        add(player, progressBar);
        show(player, progressBar.getLabel());
    }

    public void remove(Player player) {
        activeProgressBar.remove(player);
        states.remove(player);
    }

    public boolean shutdownCurrentActiveBar(Player player) {
        ProgressBar bar = activeProgressBar.get(player);

        if (bar == null) return true;

        if (bar.isOverride()) return false;

        bar.cancel();
        states.get(player).put(bar.getLabel(), ProgressBarState.from(bar, CooldownManager.getInstance().getCounter()));
        return true;
    }
}
