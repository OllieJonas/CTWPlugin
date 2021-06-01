package me.ollie.capturethewool.core.lobby;

import lombok.Getter;
import me.ollie.capturethewool.core.game.AbstractGame;
import me.ollie.capturethewool.core.map.AbstractGameMap;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class LobbyManager {

    @Getter
    private static LobbyManager instance;

    @Getter
    private final Map<Integer, Lobby> lobbies;

    private Map<UUID, Integer> players;

    private int noLobbies;

    private final JavaPlugin plugin;

    public LobbyManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.lobbies = new HashMap<>();
        this.players = new HashMap<>();

        instance = this;
    }

    public void createLobby(AbstractGame game, AbstractGameMap map, boolean requiresForceStart) {
        lobbies.put(noLobbies++, new Lobby(plugin, game, map, requiresForceStart));
    }

    public void removeLobby(int lobby) {
        lobbies.remove(lobby);
        this.players = players.entrySet().stream().filter(e -> e.getValue() != lobby).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public void joinLobby(Player player, int id) {

        if (isInLobby(player))
            leaveLobby(player);

        System.out.println("Join Lobby ID: " + id);
        players.put(player.getUniqueId(), id);
        Lobby lobby = lobbies.get(id);

        if (lobby == null) {
            player.sendMessage(ChatColor.RED + "Can't find lobby! Attempted Lobby ID: " + id + ". Lobby IDs: " + lobbies.keySet().stream().map(String::valueOf).collect(Collectors.joining(", ")));
            return;
        }

        lobby.addPlayer(player);
    }

    public void leaveLobby(Player player) {

        int id = players.get(player.getUniqueId());
        Lobby lobby = lobbies.get(id);

        if (lobby == null) {
            player.sendMessage(ChatColor.RED + "Can't find lobby!");
            return;
        }

        players.remove(player.getUniqueId());

        lobby.removePlayer(player);
    }

    public Lobby getLobbyFor(Player player) {
        return lobbies.get(players.get(player.getUniqueId()));
    }

    public int getLobbyIdFor(Player player) {
        return players.get(player.getUniqueId());
    }

    public boolean isInLobby(Player player) {
        return players.containsKey(player.getUniqueId()) && !getLobbyFor(player).getState().isInGame();
    }

    public boolean isInGame(Player player) {
        return players.containsKey(player.getUniqueId()) && getLobbyFor(player).getState().isInGame();
    }
}
