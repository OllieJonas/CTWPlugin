package me.ollie.capturethewool.core.pve.boss.events;

import me.ollie.capturethewool.core.pve.Enemy;
import me.ollie.capturethewool.core.pve.boss.Boss;
import me.ollie.capturethewool.core.pve.boss.BossManager;
import me.ollie.capturethewool.core.pve.boss.phase.EndCondition;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class BossEventsAdapter implements Listener {

    @EventHandler
    public void onDeath(EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();
        Boss<?> boss = BossManager.getInstance().get(entity);
        if (boss != null) {
            Bukkit.getPluginManager().callEvent(new BossDeathEvent(boss, event));
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof LivingEntity livingEntity) {
            if (!(event.getDamager() instanceof Player)) return;

            Boss<?> boss = BossManager.getInstance().get(livingEntity);
            if (boss != null) {
                Bukkit.getPluginManager().callEvent(new BossDamageByPlayerEvent(boss, event));
            }
        }
    }

    @EventHandler
    public void onMinionDeath(EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();

        Boss<?> boss = BossManager.getInstance().getFromAssociated(entity);

        if (boss == null) return;

        EndCondition condition = boss.getCurrPhase().endCondition();
        if (!(condition instanceof EndCondition.EnemiesDead enemiesDead)) return;

        if (enemiesDead.isEnemy(entity)) {
            Bukkit.getPluginManager().callEvent(new BossMinionDeathEvent(boss, event, Enemy.get(entity)));
        }
    }
}
