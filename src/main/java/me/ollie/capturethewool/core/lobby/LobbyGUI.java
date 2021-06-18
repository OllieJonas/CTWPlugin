package me.ollie.capturethewool.core.lobby;

import me.ollie.capturethewool.core.game.AbstractGame;
import me.ollie.capturethewool.core.gui.GUI;
import me.ollie.capturethewool.core.gui.GUIItem;
import me.ollie.capturethewool.core.util.ItemStackBuilder;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jooq.lambda.tuple.Tuple2;

import java.util.function.BiFunction;

public class LobbyGUI extends GUI {

    private final BiFunction<Lobby, Integer, ItemStack> lobbyItemStackFunction = (l, i) -> new ItemStackBuilder(l.getState().getMaterial())
            .withName(ChatColor.YELLOW + "" + ChatColor.BOLD + l.getGame().getConfiguration().name())
            .withLore(
                    " ",
                    ChatColor.GRAY + "State: " + l.getState().getMessage(),
                    " ",
                    ChatColor.GRAY + "Player Count: " + ChatColor.AQUA + l.getPlayers().size() + ChatColor.GRAY + " / " + ChatColor.AQUA + l.getGame().getConfiguration().maxPlayers(),
                    " ",
                    ChatColor.GRAY + "Lobby: " + ChatColor.AQUA + i
            )
            .build();

    private final LobbyManager manager;

    private final Class<? extends AbstractGame> clazz;

    public LobbyGUI(LobbyManager manager, Player player, Class<? extends AbstractGame> clazz) {
        super(player, clazz.getSimpleName() + " - Press ESC to Exit!", 54);
        this.manager = manager;
        this.clazz = clazz;
    }

    @Override
    public void addItems() {
        manager.getAllLobbies(clazz).entrySet().stream()
                .map(e -> new Tuple2<>(e.getKey(), lobbyItemStackFunction.apply(e.getValue(), e.getKey())))
                .forEach(t -> add(t.v1(), new GUIItem(t.v2(), (p, ind, item) -> manager.joinLobby(clazz, p, ind), true)));
    }
}
