package me.ollie.capturethewool.boss.pirateboss;

import me.ollie.capturethewool.core.ability.Ability;
import me.ollie.capturethewool.core.pve.Enemy;
import me.ollie.capturethewool.core.pve.animation.RiseFromGroundSpawnAnimation;
import me.ollie.capturethewool.core.util.LocationUtil;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Skeleton;

import java.util.stream.IntStream;

public class PirateCrewAbility extends Ability {

    @Override
    public void power(LivingEntity user) {
        IntStream.range(0, 10).forEach(i -> {

            Enemy<Skeleton> pirate = new PirateCrewEnemy();
            pirate.spawn(LocationUtil.randomLocationAround(user.getLocation(), 10), new RiseFromGroundSpawnAnimation());
        });
    }
}
