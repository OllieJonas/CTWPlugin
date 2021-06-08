package me.ollie.capturethewool.boss.pirateboss;

import me.ollie.capturethewool.core.pve.Enemy;
import me.ollie.capturethewool.core.pve.EnemyDrops;
import me.ollie.capturethewool.core.pve.Modifier;
import me.ollie.capturethewool.core.util.CollectionUtil;
import me.ollie.capturethewool.enemy.DropsRegistry;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Skeleton;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public class PirateCrewEnemy extends Enemy<Skeleton> {

    private static final List<String> PIRATE_NAMES = List.of(
            ChatColor.RED + "Plantagenet 'The Wall' Sagar",
            ChatColor.RED + "Holt 'Plank Walker' Tidus",
            ChatColor.RED + "Sewell 'Silver-Eye' Vail",
            ChatColor.RED + "Hanford 'Gentle Heart' Synth",
            ChatColor.RED + "Elmer 'Renegade' Earle",
            ChatColor.RED + "Mitford 'Grisly' Peddle",
            ChatColor.RED + "Orton 'Salty Dog' Prysm",
            ChatColor.RED + "Ray 'White Hair' Reyson",
            ChatColor.RED + "Monckton 'The Idiot' Hayhurst",
            ChatColor.RED + "Fulton 'Four-Eyes' Merton"
    );

    private static final Supplier<String> RANDOM_NAME = () -> CollectionUtil.random(PIRATE_NAMES);

    public PirateCrewEnemy() {
        super(Skeleton.class, RANDOM_NAME, DropsRegistry.NONE, Collections.emptyList());
    }

    @Override
    public void transformer(Skeleton enemy) {
        enemy.setShouldBurnInDay(false);

        if (enemy.getEquipment() == null) return;

        enemy.getEquipment().setItemInMainHand(new ItemStack(Material.IRON_SWORD));
        enemy.getEquipment().setItemInMainHandDropChance(0);
    }
}
