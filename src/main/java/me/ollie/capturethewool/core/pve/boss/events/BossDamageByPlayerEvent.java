package me.ollie.capturethewool.core.pve.boss.events;

import lombok.Getter;
import me.ollie.capturethewool.core.pve.boss.Boss;
import me.ollie.capturethewool.core.pve.boss.phase.Phase;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Collection;

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

    public Phase getPhase() {
        return boss.getCurrPhase();
    }
}
