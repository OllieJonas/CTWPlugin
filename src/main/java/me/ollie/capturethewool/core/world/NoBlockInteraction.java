package me.ollie.capturethewool.core.world;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class NoBlockInteraction extends NoWorldUtility {


    public NoBlockInteraction(JavaPlugin plugin) {
        super(plugin);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (worlds.contains(player.getWorld().getName()) && !player.isOp()) {
            event.getBlock().setBlockData(event.getBlock().getBlockData());
            player.sendMessage(ChatColor.RED + "You can't break / place blocks here!");
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (worlds.contains(player.getWorld().getName()) && !player.isOp()) {
            player.sendMessage(ChatColor.RED + "You can't break / place blocks here!");
            event.setCancelled(true);
        }
    }
}
