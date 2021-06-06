package me.ollie.capturethewool.boss.pirateboss;

import me.ollie.capturethewool.core.pve.Enemy;
import me.ollie.capturethewool.core.pve.EnemyDrops;
import me.ollie.capturethewool.core.pve.Modifier;
import me.ollie.capturethewool.core.pve.boss.Boss;
import me.ollie.capturethewool.core.pve.boss.Colour;
import me.ollie.capturethewool.core.pve.boss.PhaseList;
import me.ollie.capturethewool.core.pve.boss.phase.Phase;
import me.ollie.capturethewool.enemy.DropsRegistry;
import net.kyori.adventure.bossbar.BossBar;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Skeleton;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.function.Supplier;

public class TheCaptain extends Boss<Skeleton> {

    private static final Location SPAWN = new Location(Bukkit.getWorld("coast"), 1264.5, 122.5, 1564.5);

    public TheCaptain() {
        super(new Enemy(), Colour.YELLOW, SPAWN);
    }

    @Override
    public Supplier<PhaseList> phases() {
        return null;
    }


    private static class Enemy extends me.ollie.capturethewool.core.pve.Enemy<Skeleton> {

        public Enemy() {
            super(Skeleton.class, ChatColor.YELLOW + "" + ChatColor.BOLD + "The Captain",
                    DropsRegistry.BOSS_DROPS, List.of(
                            new Modifier(Attribute.GENERIC_MAX_HEALTH, AttributeModifier.Operation.ADD_NUMBER, 130D)
                    ));
        }

        @Override
        public void transformer(Skeleton enemy) {
            enemy.setShouldBurnInDay(false);
        }
    }
}
