package me.ollie.capturethewool.core.lobby;

import lombok.Getter;
import me.ollie.capturethewool.core.game.AbstractGame;
import me.ollie.capturethewool.core.map.AbstractGameMap;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.stream.Collectors;

public class LobbyManager {

    public record LobbyInfo(Class<? extends AbstractGame> game, int lobby) {}

    @Getter
    private static LobbyManager instance;

    private Map<UUID, LobbyInfo> players;

    private final Map<Class<? extends AbstractGame>, Map<Integer, Lobby>> lobbies;

    private int noLobbies;

    private final JavaPlugin plugin;

    public LobbyManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.players = new HashMap<>();
        this.lobbies = new HashMap<>();

        instance = this;
    }

    public Map<Integer, Lobby> getAllLobbies(Class<? extends AbstractGame> clazz) {
        return lobbies.get(clazz);
    }

    public void createLobby(AbstractGame clazz, AbstractGameMap map, Location lobbyLocation) {
        lobbies.computeIfAbsent(clazz.getClass(), k -> new HashMap<>());
        lobbies.get(clazz.getClass()).put(noLobbies++, new Lobby(plugin, clazz, map, lobbyLocation, false));
    }

    public void removeLobby(Class<? extends AbstractGame> clazz, int lobby) {
        lobbies.get(clazz).remove(lobby);

        this.players = players.entrySet().stream()
                .filter(e -> e.getValue().game() != clazz)
                .filter(e -> e.getValue().lobby() != lobby)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public void joinLobby(Class<? extends AbstractGame> clazz, Player player, int id) {
        if (isInLobby(player))
            leaveLobby(player);

        Lobby lobby = lobbies.get(clazz).get(id);

        if (lobby == null) {
            player.sendMessage(ChatColor.RED + "Can't find lobby! Attempted Lobby ID: " + id + ". Lobby IDs: " + lobbies.keySet().stream().map(String::valueOf).collect(Collectors.joining(", ")));
            return;
        }

        players.put(player.getUniqueId(), new LobbyInfo(clazz, id));
        lobby.addPlayer(player);

        if (players.size() > lobby.getGame().getConfiguration().minPlayersToStart()) {
            lobby.gameStarting();
        }
    }


    public void leaveLobby(Player player) {

        LobbyInfo id = players.get(player.getUniqueId());
        Lobby lobby = lobbies.get(id.game()).get(id.lobby());

        if (lobby == null) {
            player.sendMessage(ChatColor.RED + "Can't find lobby!");
            return;
        }

        players.remove(player.getUniqueId());

        lobby.removePlayer(player);
    }

    public LobbyInfo getLobbyInfoFor(Player player) {
        return players.get(player.getUniqueId());
    }

    public Lobby getLobbyFor(Player player) {
        LobbyInfo info = getLobbyInfoFor(player);
        return lobbies.get(info.game()).get(info.lobby());
    }

    public boolean isInLobby(Player player) {
        return players.containsKey(player.getUniqueId()) && !getLobbyFor(player).getState().isInGame();
    }

    public boolean isInGame(Player player) {
        return players.containsKey(player.getUniqueId()) && getLobbyFor(player).getState().isInGame();
    }
}
