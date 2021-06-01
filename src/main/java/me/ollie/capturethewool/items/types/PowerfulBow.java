package me.ollie.capturethewool.items.types;

import me.ollie.capturethewool.core.cooldown.CooldownType;
import me.ollie.capturethewool.items.meta.AbilityInformation;
import me.ollie.capturethewool.items.ItemRarity;
import org.bukkit.Material;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

public abstract class PowerfulBow extends PowerfulItem {

    public PowerfulBow(String name, String backstory, ItemRarity rarity, AbilityInformation information) {
        super(name, backstory, Material.BOW, rarity, information);
    }

    @Override
    public CooldownType cooldownType() {
        return CooldownType.SHOOT_BOW;
    }

    public void onShoot(EntityShootBowEvent event) {

    }

    public void onArrowHit(ProjectileHitEvent event) {

    }
}
