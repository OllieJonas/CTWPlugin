package me.ollie.capturethewool.boss.pirateboss;

import me.ollie.capturethewool.core.pve.Modifier;
import me.ollie.capturethewool.core.pve.animation.RiseFromGroundSpawnAnimation;
import me.ollie.capturethewool.core.pve.boss.Boss;
import me.ollie.capturethewool.core.pve.boss.Colour;
import me.ollie.capturethewool.core.pve.boss.InvinciblePhase;
import me.ollie.capturethewool.core.pve.boss.PhaseList;
import me.ollie.capturethewool.enemy.DropsRegistry;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Skeleton;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TheCaptain extends Boss<Skeleton> {

    public static final Location SPAWN = new Location(Bukkit.getWorld("coast"), 1264.5, 122.5, 1564.5);

    public TheCaptain() {
        super(new Enemy(), Colour.YELLOW);
        PLUGIN.getServer().getPluginManager().registerEvents(new Listener(), PLUGIN);
    }

    @Override
    public Supplier<PhaseList> phases() {
        return () -> new PhaseList()
                .add(new CaptainAbilityPhase())
                .add(new InvinciblePhase(this, IntStream.range(0, 3).boxed().map(i -> new PirateCrewEnemy()).collect(Collectors.toList()), new RiseFromGroundSpawnAnimation()))
                .add(new CaptainAbilityPhase());
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

    private static class Listener implements org.bukkit.event.Listener {

    }
}
