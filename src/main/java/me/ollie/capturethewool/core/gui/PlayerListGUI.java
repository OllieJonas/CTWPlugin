package me.ollie.capturethewool.core.gui;

import lombok.Getter;
import me.ollie.capturethewool.core.gui.helper.ChestGUIUtils;
import me.ollie.capturethewool.core.util.ItemStackBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class PlayerListGUI {

    private final String title;

    private final Function<Player, ItemStack> playerItemStackFunction;

    private final BiConsumer<Player, Player> action;

    private final Supplier<Collection<? extends Player>> playerSupplier;

    @Getter
    private final GUI gui;

    public PlayerListGUI(String title, BiConsumer<Player, Player> action) {
        this(title, Bukkit::getOnlinePlayers, p -> new String[0], action);
    }

    public PlayerListGUI(String title, Function<Player, String[]> loreFunction, BiConsumer<Player, Player> action) {
        this(title, Bukkit::getOnlinePlayers, loreFunction, action);
    }

    public PlayerListGUI(String title, Supplier<Collection<? extends Player>> playerSupplier, Function<Player, String[]> loreFunction, BiConsumer<Player, Player> action) {
        this.title = title;
        this.playerSupplier = playerSupplier;
        this.action = action;
        this.playerItemStackFunction = initPlayerItemStackFunction(loreFunction);
        this.gui = initGui();
    }

    private GUI initGui() {
        List<Player> players = new ArrayList<>(playerSupplier.get());
        return new GUI(title, ChestGUIUtils.calculateInventorySize(players.size())) {
            @Override
            public void addItems() {
                for (int i = 0; i < players.size(); i++) {
                    Player player = players.get(i);
                    add(i, GUIItem.of(playerItemStackFunction.apply(player), (p, item) -> action.accept(p, player), true));
                }
            }
        };
    }

    private Function<Player, ItemStack> initPlayerItemStackFunction(Function<Player, String[]> loreFunction) {
        return player -> new ItemStackBuilder()
                .withName("&b" + player.getName())
                .withLore(loreFunction.apply(player))
                .toSkullBuilder()
                .withOwner(player.getName())
                .buildSkull();
    }

    public void open(Player player) {
        gui.open(player);
    }

    public void close(Player player) {
        gui.close(player);
    }
}
