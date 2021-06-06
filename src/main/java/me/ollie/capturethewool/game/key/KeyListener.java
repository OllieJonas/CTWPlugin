package me.ollie.capturethewool.game.key;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Arrays;

public class KeyListener implements Listener {

        @EventHandler
        public void onInteract(PlayerInteractEvent event) {
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_BLOCK) {
                event.getPlayer().sendMessage("here1");
                Player player = event.getPlayer();
                Block block = event.getClickedBlock();

                if (block == null) return;
                if (player.getInventory().getItemInMainHand().getType() == Material.AIR) return;
                if (!KeyType.isAKey(player.getInventory().getItemInMainHand())) return;

                player.sendMessage("here");

                // only need to use material here bc we've already checked if its a key w KeyType.isAKey();
                KeyType key = KeyType.keyFrom(player.getInventory().getItemInMainHand().getType());

                Location location = block.getLocation();

                Arrays.stream(key.getCanOpen())
                        .map(LockType::getMaterial)
                        .filter(m -> block.getType() == m)
                        .findAny().ifPresent(p -> DungeonDoor.getDoorFrom(DungeonLock.getLockFrom(location)).unlock(player, key));

            }
        }
}
