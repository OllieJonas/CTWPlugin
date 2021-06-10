package me.ollie.capturethewool.core.pve.boss.events;

import lombok.Getter;
import me.ollie.capturethewool.core.pve.boss.Boss;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class BossDamageByPlayerEvent extends BossEvent {

    @Getter
    private final EntityDamageByEntityEvent event;

    public BossDamageByPlayerEvent(Boss<?> boss, EntityDamageByEntityEvent event) {
        super(boss);
        this.event = event;
    }

    public double getNewHealth() {
        return boss.getEnemy().getEntity().getHealth() - event.getDamage();
    }
}
