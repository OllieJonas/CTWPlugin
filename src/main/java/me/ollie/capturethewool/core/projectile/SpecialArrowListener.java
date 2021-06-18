package me.ollie.capturethewool.core.projectile;

import com.destroystokyo.paper.event.entity.ProjectileCollideEvent;
import me.ollie.capturethewool.core.util.ReducedItemStack;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.projectiles.ProjectileSource;

public class SpecialArrowListener implements Listener {

    private static final String METADATA_KEY = "specialprojectile";

    private final JavaPlugin plugin;

    private final SpecialProjectileRegistry registry;

    private final ProjectileSelectionStrategy strategy;

    public SpecialArrowListener(JavaPlugin plugin, SpecialProjectileRegistry registry, ProjectileSelectionStrategy strategy) {
        this.plugin = plugin;
        this.registry = registry;
        this.strategy = strategy;
    }

    @EventHandler
    public void onHit(ProjectileHitEvent event) {

        if (!(event.getEntity().getShooter() instanceof Player)) return;

        Entity entity = event.getHitEntity();
        Projectile projectile = event.getEntity();

        SpecialProjectile specialProjectile = null;

        if (!projectile.hasMetadata(METADATA_KEY)) return;

        String metadata = projectile.getMetadata(METADATA_KEY).get(0).asString();

        if (projectile instanceof ThrowableProjectile throwableProjectile) {
//             specialProjectile = registry.get(event.getEntityType(), throwableProjectile.getMetadata());
        }
        else if (projectile instanceof Arrow arrow) {
            System.out.println("here2");
            System.out.println(arrow.getItemStack().getItemMeta().getDisplayName());

            specialProjectile = registry.get(event.getEntityType(), metadata);
        }

        if (specialProjectile == null) return;

        System.out.println("here3");

        if (entity instanceof LivingEntity livingEntity) {
            specialProjectile.onHit().accept(livingEntity);
        } else if (event.getHitBlock() != null) {
            specialProjectile.onMiss().accept(event.getHitBlock().getLocation());
        } else {
            specialProjectile.onMiss().accept(entity.getLocation());
        }
    }

    @EventHandler
    public void onLaunch(EntityShootBowEvent event) {
        LivingEntity shooter = event.getEntity();

        if (shooter instanceof Player player) {
            player.sendMessage("here launch");
            strategy.projectile(event.getEntityType(), player).ifPresent(specialProjectile -> {
                player.sendMessage(event.getEntityType().name());
                Class<? extends Projectile> projectileClazz = ProjectileUtil.toProjectile(event.getEntityType());

                player.sendMessage("here2 launch");

                if (projectileClazz == null) return;

                Projectile projectile = player.launchProjectile(projectileClazz);
                projectile.setVelocity(event.getProjectile().getVelocity());
                projectile.setShooter(player);
                projectile.setMetadata(METADATA_KEY, new FixedMetadataValue(plugin, specialProjectile.getMetadata()));
                event.setProjectile(projectile);
            });
        }
    }
}
