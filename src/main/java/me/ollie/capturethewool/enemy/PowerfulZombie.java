package me.ollie.capturethewool.enemy;

import me.ollie.capturethewool.core.pve.Enemy;
import me.ollie.capturethewool.core.util.ItemStackBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class PowerfulZombie extends Enemy<Zombie> {

    public PowerfulZombie() {
        super(Zombie.class, ChatColor.RED + "Powerful Zombie", NORMAL_DROPS, Arrays.asList(
                modifier(Attribute.GENERIC_MAX_HEALTH, AttributeModifier.Operation.MULTIPLY_SCALAR_1, 0.5),
                modifier(Attribute.GENERIC_MOVEMENT_SPEED, AttributeModifier.Operation.MULTIPLY_SCALAR_1, 0.1),
                modifier(Attribute.GENERIC_ATTACK_DAMAGE, AttributeModifier.Operation.MULTIPLY_SCALAR_1, 0.1)));
    }

    @Override
    public void transformer(Zombie enemy) {
        EntityEquipment equipment = enemy.getEquipment();
        if (equipment == null) return;

        enemy.setAdult();

        equipment.setItemInMainHand(new ItemStack(Material.AIR));

        equipment.setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
        equipment.setHelmetDropChance(0);

        equipment.setChestplate(new ItemStackBuilder.LeatherArmourBuilder(Material.LEATHER_CHESTPLATE, new ItemStackBuilder()).colour(Color.RED).buildLeather());
        equipment.setChestplateDropChance(0);

        equipment.setLeggings(new ItemStackBuilder.LeatherArmourBuilder(Material.LEATHER_LEGGINGS, new ItemStackBuilder()).colour(Color.YELLOW).buildLeather());
        equipment.setLeggingsDropChance(0);

        equipment.setBoots(new ItemStackBuilder.LeatherArmourBuilder(Material.LEATHER_BOOTS, new ItemStackBuilder()).colour(Color.RED).buildLeather());
        equipment.setBootsDropChance(0);
    }
}
