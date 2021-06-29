package me.ollie.capturethewool.core.pve.miniboss;

import me.ollie.capturethewool.core.pve.Enemy;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class MiniBossManager implements Listener {

    private final JavaPlugin plugin;

    private final Map<Integer, MiniBoss> enemies;

    public MiniBossManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.enemies = new HashMap<>();
    }

    public void init() {
        plugin.getServer().getPluginManager().registerEvents(new Listener(), plugin);
    }

    public void register(MiniBoss boss) {
        boss.getEnemies().stream().map(Enemy::getEntity).map(Entity::getEntityId).forEach(i -> enemies.put(i, boss));
    }

    private class Listener implements org.bukkit.event.Listener {

        @EventHandler
        public void onDeath(EntityDeathEvent event) {
            int id = event.getEntity().getEntityId();
            MiniBoss miniBoss = enemies.get(id);
            if (miniBoss == null) return;

            Enemy<?> enemy = Enemy.get(id);
            if (enemy == null) throw new IllegalStateException("something seriously fucked up if you're seeing this (miniboss isnt null but enemy is)");

            miniBoss.getEnemies().remove(enemy);

            if (miniBoss.getEnemies().isEmpty()) {
                miniBoss.onFinish().run();
            }
        }
    }
}
