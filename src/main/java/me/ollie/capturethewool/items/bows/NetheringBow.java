package me.ollie.capturethewool.items.bows;

import me.ollie.capturethewool.CaptureTheWool;
import me.ollie.capturethewool.core.cooldown.CooldownType;
import me.ollie.capturethewool.core.util.particles.ActionOnEnterVortex;
import me.ollie.capturethewool.core.util.particles.Circle;
import me.ollie.capturethewool.items.ItemRarity;
import me.ollie.capturethewool.items.meta.AbilityInformation;
import me.ollie.capturethewool.items.types.PowerfulBow;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;

public class NetheringBow extends PowerfulBow {

    public NetheringBow() {
        super("Netherbow", "", ItemRarity.EXOTIC, AbilityInformation.of("Well of Demise", "Applies withering to entities within a 5 block radius of where the arrow lands. Then, creates a Well of Demise, dealing 1/2 heart of damage every 0.5 seconds.", 60F));
    }

    @Override
    public CooldownType cooldownType() {
        return CooldownType.BOW_HIT;
    }

    @Override
    public void onArrowHit(ProjectileHitEvent event) {
        Projectile arrow = event.getEntity();

        arrow.getNearbyEntities(5, 5, 5).stream().filter(e -> e instanceof LivingEntity).map(e -> (LivingEntity) e).forEach(e -> e.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 7 * 20, 0)));
        Location location = arrow.getLocation();

        ActionOnEnterVortex vortex = new ActionOnEnterVortex(CaptureTheWool.getInstance(),
                Particle.PORTAL,
                location.clone().subtract(0, 1, 0),
                5,
                4,
                3,
                p -> p.setHealth(p.getHealth() - 0.5),
                10L);

        vortex.setDescending(true);
        vortex.run();
        Circle circle = new Circle(Particle.PORTAL, location, 5, 0.5F);
        circle.runTaskTimer(CaptureTheWool.getInstance(), 0L, 10L);

        Bukkit.getScheduler().scheduleSyncDelayedTask(CaptureTheWool.getInstance(), () -> {
            vortex.destroy();
            circle.cancel();
        }, 10 * 20);
    }
}
