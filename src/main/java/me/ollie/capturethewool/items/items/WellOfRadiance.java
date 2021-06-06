package me.ollie.capturethewool.items.items;

import me.ollie.capturethewool.items.ItemRarity;
import me.ollie.capturethewool.items.meta.AbilityInformation;
import me.ollie.capturethewool.items.types.PowerfulItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class WellOfRadiance extends PowerfulItem {

    public WellOfRadiance() {
        super("Well of Radiance", "Destiny 2", Material.GOLDEN_APPLE, ItemRarity.EXOTIC, AbilityInformation.of("Well of Radiance", "Create a healing pool around you!", 75F));
    }

    @Override
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Location location = player.getLocation();

        Well well = Well.builder()
                .location(location)
                .circleParticle(Particle.FIREWORKS_SPARK)
                .vortexParticle(Particle.HEART)
                .onEnter(p -> {
                    if (!(p instanceof Player)) return;

                    p.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 20 * 45, 4));
                    p.setHealth(Math.min(p.getHealth() + 0.5, p.getMaxHealth()));
                })
                .radius(5)
                .onEnterFrequency(2L)
                .duration(10)
                .build();

        well.run();
    }
}
