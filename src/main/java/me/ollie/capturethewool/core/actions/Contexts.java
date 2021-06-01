package me.ollie.capturethewool.core.actions;

import me.ollie.capturethewool.core.lobby.Lobby;
import org.bukkit.entity.Player;

public sealed interface Contexts permits Contexts.Join, Contexts.Leave {

    final record Join(Player player, Lobby lobby) implements Contexts {}

    final record Leave(Player player) implements Contexts {}
}
