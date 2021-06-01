package me.ollie.capturethewool.items.bows;

import me.ollie.capturethewool.core.cooldown.CooldownType;
import me.ollie.capturethewool.items.ItemRarity;
import me.ollie.capturethewool.items.meta.AbilityInformation;
import me.ollie.capturethewool.items.types.PowerfulBow;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.ProjectileHitEvent;

public class ExplosionBow extends PowerfulBow {

    public ExplosionBow() {
        super("Exploding Bow", "Kaboom!", ItemRarity.UNCOMMON, AbilityInformation.of("Explosions", "explosions!", 5.0F));
    }

    @Override
    public void onArrowHit(ProjectileHitEvent event) {
        Player player = (Player) event.getEntity().getShooter();

        if (player == null) return;

        player.sendMessage("hihihi!");
    }

    @Override
    public CooldownType cooldownType() {
        return CooldownType.BOW_HIT;
    }
}
