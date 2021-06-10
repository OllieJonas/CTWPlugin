package me.ollie.capturethewool.core.pve.animation;

import me.ollie.capturethewool.core.GamesCore;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

public interface SpawnAnimation {

    Location location(Location location);

    void tick(LivingEntity entity);

    void onFinish(LivingEntity entity);

    long animationLength();

    default void play(LivingEntity entity) {
        entity.setInvulnerable(true);
        int taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(GamesCore.getInstance().getPlugin(), () -> tick(entity), 0L, 1L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(GamesCore.getInstance().getPlugin(), () -> {
            onFinish(entity);
            entity.setInvulnerable(false);
            Bukkit.getScheduler().cancelTask(taskId);
        }, animationLength());
    }
}
