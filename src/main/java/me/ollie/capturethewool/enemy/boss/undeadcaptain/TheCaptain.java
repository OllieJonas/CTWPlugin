package me.ollie.capturethewool.enemy.boss.undeadcaptain;

import me.ollie.capturethewool.core.pve.Modifier;
import me.ollie.capturethewool.core.pve.boss.Boss;
import me.ollie.capturethewool.core.util.ItemStackBuilder;
import net.kyori.adventure.bossbar.BossBar;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class TheCaptain extends Boss<Skeleton> {

    private static final Location SPAWN = new Location(Bukkit.getWorld("coast"), 1264.5, 122.5, 1564.5);

    public TheCaptain() {
        super(ChatColor.YELLOW + "" + ChatColor.BOLD + "The Captain", Skeleton.class, BossBar.Color.YELLOW, List.of(
                new Modifier(Attribute.GENERIC_MAX_HEALTH, AttributeModifier.Operation.ADD_NUMBER, 130),
                new Modifier(Attribute.GENERIC_ARMOR, AttributeModifier.Operation.MULTIPLY_SCALAR_1, -0.2),
                new Modifier(Attribute.GENERIC_ATTACK_DAMAGE, AttributeModifier.Operation.MULTIPLY_SCALAR_1, 0.5),
                new Modifier(Attribute.GENERIC_FOLLOW_RANGE, AttributeModifier.Operation.MULTIPLY_SCALAR_1, 1.0),
                new Modifier(Attribute.GENERIC_MOVEMENT_SPEED, AttributeModifier.Operation.MULTIPLY_SCALAR_1, 0.05),
                new Modifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, AttributeModifier.Operation.MULTIPLY_SCALAR_1, 100)
        ), SPAWN);
    }

    @Override
    public void transformer(Skeleton enemy) {
        EntityEquipment equipment = enemy.getEquipment();
        if (equipment == null) return;

        equipment.setItemInMainHand(new ItemStack(Material.IRON_SWORD));
        equipment.setItemInMainHandDropChance(0);

        equipment.setHelmet(new ItemStack(Material.IRON_HELMET));
        equipment.setHelmetDropChance(0);

        equipment.setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
        equipment.setChestplateDropChance(0);

        equipment.setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
        equipment.setLeggingsDropChance(0);

        equipment.setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
        equipment.setBootsDropChance(0);
    }
}
