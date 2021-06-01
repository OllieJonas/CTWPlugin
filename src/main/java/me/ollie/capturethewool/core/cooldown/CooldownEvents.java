package me.ollie.capturethewool.core.cooldown;

import com.destroystokyo.paper.event.entity.ProjectileCollideEvent;
import me.ollie.capturethewool.core.GamesCore;
import me.ollie.capturethewool.items.PowerfulItemRegistry;
import me.ollie.capturethewool.items.types.PowerfulItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.projectiles.ProjectileSource;

import java.util.stream.Collectors;

public class CooldownEvents implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {

        if (event.getItem() == null || event.getItem().getType() == Material.BOW) return;

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            event.setCancelled(cooldown(event.getPlayer(), CooldownType.INTERACT, event.getItem()));
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();

        if (!(damager instanceof Player)) return;

        Player player = (Player) damager;

        event.setCancelled(cooldown(player, CooldownType.HIT, player.getInventory().getItemInMainHand()));
    }

    @EventHandler
    public void onShoot(EntityShootBowEvent event) {
        Entity shooter = event.getEntity();

        if (!(shooter instanceof Player)) return;


        Player player = (Player) shooter;

        ItemStack item = player.getInventory().getItemInMainHand();

        event.getProjectile().setMetadata("name", new FixedMetadataValue(GamesCore.getInstance().getPlugin(), ChatColor.stripColor(item.getItemMeta().getDisplayName())));

        boolean onCooldown = cooldown(player, CooldownType.SHOOT_BOW, item);

        if (onCooldown) {
            event.setCancelled(true);
            event.getProjectile().remove();
        }
    }

    @EventHandler
    public void onHit(ProjectileHitEvent event) {
        onBowHit(event.getEntity(), event);
    }

    @EventHandler
    public void onCollide(ProjectileCollideEvent event) {
        onBowHit(event.getEntity(), event);
    }

    private <T extends Cancellable> void onBowHit(Projectile projectile, T event) {

        if (!(projectile.getShooter() instanceof Player)) return;

        Player player = (Player) projectile.getShooter();

        if (!projectile.hasMetadata("name")) return;

        String name = projectile.getMetadata("name").get(0).asString();

        PowerfulItem item = PowerfulItemRegistry.get(name);

        if (item == null) return;

        if (item.cooldownType() != CooldownType.BOW_HIT) return;

        event.setCancelled(cooldown(player, CooldownType.BOW_HIT, item.getItemStack()));
    }

    @EventHandler
    public void onThrow(ProjectileLaunchEvent event) {
        ProjectileSource source = event.getEntity().getShooter();

        if (!(source instanceof Player)) return;

        if (event.getEntity() instanceof Arrow) return;

        Player player = (Player) source;

        event.setCancelled(cooldown(player, CooldownType.THROW_PROJECTILE, player.getInventory().getItemInMainHand()));
    }

    private boolean cooldown(Player player, CooldownType type, ItemStack item) {

        if (item == null || item.getType() == Material.AIR) return false;

        CooldownManager manager = CooldownManager.getInstance();

        if (!manager.isRegistered(item)) return false;
        if (manager.itemIsInCooldown(player, item)) {
            player.sendMessage(ChatColor.RED + "This is currently on cooldown!");
            if (type == CooldownType.THROW_PROJECTILE) {
                player.getInventory().setItemInMainHand(item);
            }
            return true;
        } else {
            manager.add(player, type, item);
            return false;
        }
    }
}
