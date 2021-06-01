package me.ollie.capturethewool.core.cooldown.progress;

import me.ollie.capturethewool.core.cooldown.CooldownManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

public class ItemProgressBarEvent implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChange(PlayerItemHeldEvent event) {

        Player player = event.getPlayer();

        if (!CooldownManager.getInstance().hasActiveCooldowns(player)) return;

        ItemStack curr = player.getInventory().getItem(event.getNewSlot());

        if (curr == null || curr.getType() == Material.AIR) return;

        if (CooldownManager.getInstance().itemIsInCooldown(player, curr)) {
            ProgressBarManager.getInstance().show(player, ChatColor.stripColor(curr.getItemMeta().getDisplayName()));
        }


    }
}
