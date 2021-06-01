package me.ollie.capturethewool.items.swords;

import me.ollie.capturethewool.core.cooldown.CooldownType;
import me.ollie.capturethewool.items.meta.AbilityInformation;
import me.ollie.capturethewool.items.ItemRarity;
import me.ollie.capturethewool.items.types.PowerfulSword;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class RogueSword extends PowerfulSword {

    public RogueSword() {
        super("Rogue Sword", "", Material.WOODEN_SWORD, ItemRarity.COMMON,
                AbilityInformation.of("Speed Boost", "Right Click to get a small speed boost!", 10.0F));
    }

    @Override
    public void onInteract(PlayerInteractEvent event) {
        event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 5 * 20, 0));
    }

    @Override
    public CooldownType cooldownType() {
        return CooldownType.INTERACT;
    }
}
