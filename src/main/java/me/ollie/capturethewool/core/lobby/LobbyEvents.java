package me.ollie.capturethewool.core.lobby;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.util.Vector;

public class LobbyEvents implements Listener {

    private final LobbyManager lobbyManager;

    public LobbyEvents(LobbyManager lobbyManager) {
        this.lobbyManager = lobbyManager;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (!lobbyManager.isInLobby(player)) return;

        Lobby lobby = lobbyManager.getLobbyFor(player);
        lobby.removePlayer(player);
    }


    // prevent people taking damage
    @EventHandler
    public void onVoidDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        if (!(entity instanceof Player player)) return;

        if (!lobbyManager.isInLobby(player)) return;

        Lobby lobby = lobbyManager.getLobbyFor(player);

        event.setCancelled(true);

        if (event.getCause() == EntityDamageEvent.DamageCause.VOID) {
            player.teleport(lobby.getSpawnPoint()); // prevent people from just yeeting off the edge
        }
    }

    @EventHandler
    public void onHunger(FoodLevelChangeEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;

        if (!lobbyManager.isInLobby(player)) return;

        event.setCancelled(true);
    }

    // ----------- DOUBLE JUMP --------------- //
    @EventHandler
    public void doubleJumpToggleFlight(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();
        if (lobbyManager.isInLobby(player)) {
            if (player.getGameMode() != GameMode.CREATIVE) {
                event.setCancelled(true);
                player.setAllowFlight(false);
                player.setFlying(false);
                player.setVelocity(player.getLocation().getDirection().multiply(1.5).add(new Vector(0, 1, 0)));
            }
        }
    }

    @EventHandler
    public void doubleJumpMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (lobbyManager.isInLobby(player)) {
            if (player.getGameMode() != GameMode.CREATIVE && player.getLocation().subtract(0, 1, 0).getBlock().getType() != Material.AIR && !player.isFlying())
                player.setAllowFlight(true);
        }
    }
}
