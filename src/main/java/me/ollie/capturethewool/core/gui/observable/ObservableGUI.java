package me.ollie.capturethewool.core.gui.observable;

import me.ollie.capturethewool.core.gui.GUI;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class ObservableGUI extends GUI {

    private final Set<Player> observers;

    public ObservableGUI(Player player, String name, int size, boolean hasBorder) {
        super(player, name, size, hasBorder);
        this.observers = new HashSet<>();
    }

    @Override
    public void open(Player player) {
        subscribe(player);
        super.open(player);
    }

    @Override
    public void close(Player player) {
        unsubscribe(player);
        super.close(player);
    }

    private void subscribe(Player player) {
        observers.add(player);
    }

    private void unsubscribe(Player player) {
        observers.remove(player);
    }

    public <T> void notifyAllObservers(T context) {
        observers.forEach(o -> redraw());
    }

    @Override
    public void addItems() {

    }
}
