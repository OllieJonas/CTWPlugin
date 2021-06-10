package me.ollie.capturethewool.core.pve.boss.events;

import lombok.Getter;
import me.ollie.capturethewool.core.pve.Enemy;
import me.ollie.capturethewool.core.pve.boss.Boss;
import org.bukkit.event.entity.EntityDeathEvent;

@Getter
public class BossMinionDeathEvent extends BossEvent {

    private final EntityDeathEvent event;

    private final Enemy<?> enemy;

    public BossMinionDeathEvent(Boss<?> boss, EntityDeathEvent event, Enemy<?> enemy) {
        super(boss);
        this.event = event;
        this.enemy = enemy;
    }
}
