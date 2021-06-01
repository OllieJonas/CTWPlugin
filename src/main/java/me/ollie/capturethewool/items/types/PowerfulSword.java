package me.ollie.capturethewool.items.types;

import me.ollie.capturethewool.core.cooldown.CooldownType;
import me.ollie.capturethewool.items.meta.AbilityInformation;
import me.ollie.capturethewool.items.ItemRarity;
import org.bukkit.Material;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public abstract class PowerfulSword extends PowerfulItem {

    public PowerfulSword(String name, String backstory, Material material, ItemRarity rarity, AbilityInformation information) {
        super(name, backstory, material, rarity, information);
    }

    @Override
    public CooldownType cooldownType() {
        return CooldownType.HIT;
    }

    public void onDamage(EntityDamageByEntityEvent event) {

    }
}
