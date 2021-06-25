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

    private final RarityItem item;

    private final AbilityInformation abilityInformation;

    private final ItemCooldownContext context;

    public PowerfulItem(String name, String backstory, Material material, ItemRarity rarity, AbilityInformation information) {
        this.item = new RarityItem(name, rarity, ItemsUtil.buildPowerfulItemFrom(name, material, backstory, rarity,
                information.title(),
                information.description(),
                information.cooldownDuration(), enchantments()));
        this.abilityInformation = information;
        this.context = new ItemCooldownContext(name, information.cooldownDuration(), cooldownType(), true);
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
        int rarityCmp = item.rarity().compareTo(o.getItem().rarity());
        if (rarityCmp != 0) return rarityCmp;
        return item.name().compareTo(o.getItem().name());
    }

    public String getName() {
        return item.name();
    }

    public ItemRarity getRarity() {
        return item.rarity();
    }

    public ItemStack getItemStack() {
        return item.item();
    }
}
