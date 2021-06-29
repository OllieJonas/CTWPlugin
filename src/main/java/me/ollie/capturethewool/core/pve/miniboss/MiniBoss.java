package me.ollie.capturethewool.core.pve.miniboss;

import me.ollie.capturethewool.core.pve.Enemy;

import java.util.Collection;
import java.util.HashSet;

public class MiniBoss {

    private final MiniBossManager miniBossManager;

    private final Runnable onFinish;

    private final Collection<Enemy<?>> enemies;

    public MiniBoss(MiniBossManager miniBossManager) {
        this(miniBossManager, () -> {});
    }

    public MiniBoss(MiniBossManager miniBossManager, Runnable onFinish) {
        this(miniBossManager, onFinish, new HashSet<>());
    }


    public MiniBoss(MiniBossManager miniBossManager, Runnable onFinish, Collection<Enemy<?>> enemies) {
        this.miniBossManager = miniBossManager;
        this.onFinish = onFinish;
        this.enemies = enemies;
    }

    public MiniBoss addEnemy(Enemy<?> enemy) {
        enemies.add(enemy);
        return this;
    }

    public void init() {
        miniBossManager.register(this);
    }

    public Runnable onFinish() {
        return onFinish;
    }

    public Collection<Enemy<?>> getEnemies() {
        return enemies;
    }
}
