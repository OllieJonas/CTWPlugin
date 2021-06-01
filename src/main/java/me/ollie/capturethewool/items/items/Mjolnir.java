package me.ollie.capturethewool.items.items;

import me.ollie.capturethewool.items.ItemRarity;
import me.ollie.capturethewool.items.meta.AbilityInformation;
import me.ollie.capturethewool.items.types.PowerfulItem;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public class Mjolnir extends PowerfulItem {

    public Mjolnir() {
        super("Mjolnir", "Thor's hammer uwu hit me daddy", Material.STONE_AXE, ItemRarity.EXOTIC, AbilityInformation.of("Lightning Strike", "woopsie", 30F));
    }

    @Override
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        player.getNearbyEntities(32, 32, 32).stream()
                .filter(e -> e instanceof LivingEntity)
                .map(e -> (LivingEntity) e)
                .filter(player::hasLineOfSight)
                .filter(e -> !e.isDead()).forEach(e -> {
            player.getWorld().strikeLightningEffect(e.getLocation());
            e.damage(12.5D);
        });
    }
}
