package me.ollie.capturethewool.items.items;

import me.ollie.capturethewool.CaptureTheWool;
import me.ollie.capturethewool.core.util.particles.ActionOnEnterVortex;
import me.ollie.capturethewool.core.util.particles.Circle;
import me.ollie.capturethewool.items.ItemRarity;
import me.ollie.capturethewool.items.meta.AbilityInformation;
import me.ollie.capturethewool.items.types.PowerfulItem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class WellOfMobility extends PowerfulItem {

    public WellOfMobility() {
        super("Well of Mobility", "Destiny 2", Material.RABBIT_FOOT, ItemRarity.LEGENDARY, AbilityInformation.of("Well of Mobility", "Create a pool that increases mobility around you!", 45F));
    }

    @Override
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Location location = player.getLocation();
        Well well = Well.builder()
                .circleRainbow(true)
                .vortexRainbow(true)
                .location(location)
                .circleParticle(Particle.VILLAGER_HAPPY)
                .vortexParticle(Particle.VILLAGER_HAPPY)
                .onEnter(p -> {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 10, 0));
                    p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20 * 10, 0));
                })
                .radius(5)
                .onEnterFrequency(2L)
                .duration(10)
                .build();

        well.run();
    }
}
