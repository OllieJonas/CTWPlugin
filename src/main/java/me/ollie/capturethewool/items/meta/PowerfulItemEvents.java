package me.ollie.capturethewool.items.meta;

import me.ollie.capturethewool.core.cooldown.CooldownManager;
import me.ollie.capturethewool.core.cooldown.CooldownType;
import me.ollie.capturethewool.items.PowerfulItemRegistry;
import me.ollie.capturethewool.items.types.PowerfulBow;
import me.ollie.capturethewool.items.types.PowerfulItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;

public class PowerfulItemEvents implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {

        if (event.isCancelled()) return;

        if (event.getItem() == null || event.getItem().getType() == Material.AIR || event.getItem().getType() == Material.BOW) return;
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            String name = ChatColor.stripColor(event.getItem().getItemMeta().getDisplayName());
            PowerfulItem item = PowerfulItemRegistry.get(name);

            if (item == null) return;
            Player player = event.getPlayer();

            item.onInteract(event);
            CooldownManager.getInstance().add(player, CooldownType.INTERACT, item.getItemStack());
        }
    }

    @EventHandler
    public void onShoot(EntityShootBowEvent event) {
        if (event.isCancelled()) return;

        LivingEntity entity = event.getEntity();

        if (!(entity instanceof Player)) return;

        Player player = (Player) event.getEntity();

        ItemStack bow = event.getBow();

        if (bow == null) return;

        String name = ChatColor.stripColor(event.getBow().getItemMeta().getDisplayName());

        PowerfulItem item = PowerfulItemRegistry.get(name);

        if (!(item instanceof PowerfulBow)) return;

        PowerfulBow powerfulBow = (PowerfulBow) item;

        powerfulBow.onShoot(event);

        CooldownManager.getInstance().add(player, CooldownType.SHOOT_BOW, item.getItemStack());
    }

    @EventHandler
    public void onCollide(ProjectileHitEvent event) {
        if (event.isCancelled()) return;
        ProjectileSource entity = event.getEntity().getShooter();

        if (!(entity instanceof Player)) return;

        Player player = (Player) event.getEntity().getShooter();

        if (!event.getEntity().hasMetadata("name")) return;

        String name = event.getEntity().getMetadata("name").get(0).asString();

        PowerfulItem item = PowerfulItemRegistry.get(name);

        if (!(item instanceof PowerfulBow)) return;

        PowerfulBow powerfulBow = (PowerfulBow) item;

        powerfulBow.onArrowHit(event);

        CooldownManager.getInstance().add(player, CooldownType.BOW_HIT, item.getItemStack());
    }

    @EventHandler
    public void onLaunch(ProjectileLaunchEvent event) {
        if (event.isCancelled()) return;

        ProjectileSource entity = event.getEntity().getShooter();
        if (!(entity instanceof Player)) return;

        
    }
}
