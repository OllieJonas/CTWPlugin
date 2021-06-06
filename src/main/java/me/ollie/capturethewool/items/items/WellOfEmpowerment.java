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

public class WellOfEmpowerment extends PowerfulItem {

    public WellOfEmpowerment() {
        super("Well of Empowerment", "Destiny 2", Material.BONE, ItemRarity.LEGENDARY, AbilityInformation.of("Well of Empowerment", "Create a strength pool around you!", 45F));
    }

    @Override
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Location location = player.getLocation();
        Well well = Well.builder()
                .location(location)
                .circleParticle(Particle.FLAME)
                .vortexParticle(Particle.FLAME)
                .onEnter(p -> p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 5, 0)))
                .radius(5)
                .onEnterFrequency(2L)
                .duration(10)
                .build();

        well.run();
    }
}
