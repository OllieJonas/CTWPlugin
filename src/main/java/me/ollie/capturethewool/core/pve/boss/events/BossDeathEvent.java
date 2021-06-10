package me.ollie.capturethewool.core.pve.boss.events;

import lombok.Getter;
import me.ollie.capturethewool.core.pve.boss.Boss;
import org.bukkit.event.entity.EntityDeathEvent;

public class BossDeathEvent extends BossEvent {

    @Getter
    private final EntityDeathEvent event;

    public BossDeathEvent(Boss<?> boss, EntityDeathEvent event) {
        super(boss);
        this.event = event;
    }
}
