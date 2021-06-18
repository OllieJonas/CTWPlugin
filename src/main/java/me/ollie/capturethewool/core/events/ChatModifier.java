package me.ollie.capturethewool.core.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatModifier implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        ChatColor name = (player.isOp() ? ChatColor.RED : ChatColor.GRAY);
        event.setFormat(name + "%s" + ChatColor.DARK_GRAY + " > " + ChatColor.WHITE + "%s");
    }
}
