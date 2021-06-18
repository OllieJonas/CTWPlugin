package me.ollie.capturethewool.core.projectile;

import me.ollie.capturethewool.core.util.ItemStackBuilder;
import me.ollie.capturethewool.core.util.ItemStackUtil;
import net.md_5.bungee.api.ChatColor;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public record SpecialProjectile(EntityType type, String name, List<String> lore, Consumer<LivingEntity> onHit, Consumer<Location> onMiss) {

    public static SpecialProjectile potionEffect(EntityType entityType, PotionEffect potionEffect) {
        return new SpecialProjectile(entityType, ChatColor.WHITE + entityType.getName() + " of " + potionEffect.getType().getName(), Collections.emptyList(), e -> e.addPotionEffect(potionEffect), l -> {});
    }

    public ItemStack asItem() {
        return asItem(1);
    }

    public ItemStack asItem(int quantity) {
        return new ItemStackBuilder(ProjectileUtil.fromEntityType(type)).withName(name).withLore(lore).withAmount(quantity).build();
    }

    public Projectile toProjectile(Player player) {
        return player.launchProjectile(ProjectileUtil.toProjectile(type));
    }

    public String getMetadata() {
        return StringUtils.abbreviate(name, 16);
    }
}
