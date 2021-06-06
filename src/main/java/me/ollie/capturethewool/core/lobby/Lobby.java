package me.ollie.capturethewool.core.lobby;

import lombok.Getter;
import me.ollie.capturethewool.core.game.AbstractGame;
import me.ollie.capturethewool.core.map.AbstractGameMap;
import me.ollie.capturethewool.core.bossbar.Countdown;
import me.ollie.capturethewool.core.bossbar.GameBossBar;
import me.ollie.capturethewool.core.util.PlayerUtil;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

@Getter
public class Lobby {

    private static final Location GAMES_LOBBY_LOCATION = new Location(Bukkit.getWorld("lobby"), 68.5, 212.5, 61.5, 180, 0);

    private final JavaPlugin plugin;

    private final AbstractGame game;

    private final AbstractGameMap map;

    private final Consumer<Player> onLeaveAction; // in case of potential expansion to multiple servers

    private final boolean requireForceStart;

    private final GameBossBar waitingForPlayers;

    private final Location spawnPoint;

    private final Set<Player> players;

    private Countdown countdown;

    private State state;

    public Lobby(JavaPlugin plugin, AbstractGame game, AbstractGameMap map, boolean requireForceStart) {
        this(plugin, game, map, GAMES_LOBBY_LOCATION, __ -> {}, requireForceStart);
    }

    public Lobby(JavaPlugin plugin, AbstractGame game, AbstractGameMap map, Consumer<Player> onLeaveAction, boolean requiresForceStart) {
        this(plugin, game, map, GAMES_LOBBY_LOCATION, onLeaveAction, requiresForceStart);
    }
    
    public Lobby(JavaPlugin plugin, AbstractGame game, AbstractGameMap map, Location lobbyLocation, Consumer<Player> onLeaveAction, boolean requireForceStart) {
        this.plugin = plugin;
        this.game = game;
        this.map = map;
        this.onLeaveAction = onLeaveAction;
        this.spawnPoint = lobbyLocation;
        this.requireForceStart = requireForceStart;

        this.state = State.WAITING;
        this.players = new HashSet<>();
        this.countdown = new Countdown(plugin, "", new HashSet<>(), 1);
        this.waitingForPlayers = new GameBossBar(plugin,
                BossBar.bossBar(Component.text(ChatColor.AQUA + "Waiting for players."), 1F, BossBar.Color.BLUE, BossBar.Overlay.NOTCHED_20),
                BossBar.bossBar(Component.text(ChatColor.AQUA + "Waiting for players.."), 1F, BossBar.Color.BLUE, BossBar.Overlay.NOTCHED_20),
                BossBar.bossBar(Component.text(ChatColor.AQUA + "Waiting for players..."), 1F, BossBar.Color.BLUE, BossBar.Overlay.NOTCHED_20));

        waitingForPlayers.run();
    }

    public void addPlayer(Player player) {
        players.add(player);
        // hidePlayers(player);

        player.teleport(spawnPoint);

        countdown.addPlayer(player);

        waitingForPlayers.showTo(player);

        LobbyItems.addItems(player, game.isKitGame());
        sendMessageToAll(ChatColor.AQUA + player.getName() + ChatColor.GRAY + " has joined! (" + ChatColor.AQUA + players.size() + ChatColor.GRAY + " / " + ChatColor.AQUA + game.getConfiguration().maxPlayers() + ChatColor.GRAY + ")");

        if (!requireForceStart && players.size() >= game.getConfiguration().minPlayersToStart())
            beginStartingGame();
    }

    public void removePlayer(Player player) {
        players.remove(player);
        // showPlayers(player); // show all players in hub

        PlayerUtil.reset(player);

        if (state.hasCountdown())
            countdown.removePlayer(player);

        waitingForPlayers.hideFrom(player);

        sendMessageToAll(ChatColor.AQUA + player.getName() + ChatColor.GRAY + " has left! (" + ChatColor.AQUA + players.size() + ChatColor.GRAY + " / " + ChatColor.AQUA + game.getConfiguration().maxPlayers() + ChatColor.GRAY + ")");
        player.sendMessage(ChatColor.GRAY + "You left the lobby!");
        onLeaveAction.accept(player);
    }

    public void beginStartingGame() {
        game.load(map);
        gameStarting();
    }

    public void gameStarting() {
        this.state = State.STARTING;
        this.countdown = new Countdown(plugin, "Teleporting to map in ", this.players, 15, this::startGame).setDisplaySubtitle(false).start();
    }

    public void startGame() {
        this.state = State.IN_GAME;
        game.startGame(players);
    }

    public void sendMessageToAll(String message) {
        players.forEach(p -> p.sendMessage(titleMessage(message)));
    }

    public String titleMessage(String message) {
        return ChatColor.DARK_AQUA + "" + ChatColor.BOLD + game.getConfiguration().name() + ChatColor.DARK_GRAY + " | " + ChatColor.GRAY + message;
    }

    public enum State {

        WAITING(ChatColor.GREEN + "Waiting for players...", Material.EMERALD_BLOCK),
        STARTING(ChatColor.YELLOW + "Game is starting soon...", Material.GOLD_BLOCK),
        IN_GAME(ChatColor.RED + "Game has already started!", Material.REDSTONE_BLOCK);

        @Getter
        private final String message;

        @Getter
        private final Material material;

        State(String message, Material material) {
            this.message = message;
            this.material = material;
        }

        public boolean hasCountdown() {
            return this == STARTING;
        }

        public boolean isInGame() {
            return this == IN_GAME;
        }
    }

}
