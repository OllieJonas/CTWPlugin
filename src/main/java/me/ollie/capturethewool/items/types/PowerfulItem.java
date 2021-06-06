package me.ollie.capturethewool.items.types;

import lombok.Getter;
import me.ollie.capturethewool.core.cooldown.CooldownType;
import me.ollie.capturethewool.core.cooldown.ItemCooldownContext;
import me.ollie.capturethewool.items.ItemRarity;
import me.ollie.capturethewool.items.meta.AbilityInformation;
import me.ollie.capturethewool.items.meta.ItemsUtil;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Map;

@Getter
public abstract class PowerfulItem implements Comparable<PowerfulItem> {

    private final String name;

    private final ItemRarity rarity;

    private final ItemStack itemStack;

    private final AbilityInformation abilityInformation;

    private final ItemCooldownContext context;

    public PowerfulItem(String name, String backstory, Material material, ItemRarity rarity, AbilityInformation information) {
        this.name = name;
        this.rarity = rarity;
        this.abilityInformation = information;
        this.context = new ItemCooldownContext(name, information.cooldownDuration(), cooldownType(), true);

        this.itemStack = ItemsUtil.buildFrom(name, material, backstory, rarity,
                abilityInformation.title(),
                abilityInformation.description(),
                abilityInformation.cooldownDuration(), enchantments());
    }

    public Map<Enchantment, Integer> enchantments() {
        return Collections.emptyMap();
    }

    public CooldownType cooldownType() {
        return CooldownType.INTERACT;
    }

    public void onInteract(PlayerInteractEvent event) {

    }

    @Override
    public int compareTo(@NotNull PowerfulItem o) {
        int rarityCmp = rarity.compareTo(o.getRarity());
        if (rarityCmp != 0) return rarityCmp;
        return name.compareTo(o.getName());
    }
}
