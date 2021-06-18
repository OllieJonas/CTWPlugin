package me.ollie.capturethewool.core.pve.boss;

import lombok.Getter;
import me.ollie.capturethewool.core.GamesCore;
import me.ollie.capturethewool.core.pve.Enemy;
import me.ollie.capturethewool.core.pve.boss.events.BossDamageByPlayerEvent;
import me.ollie.capturethewool.core.pve.boss.events.BossDeathEvent;
import me.ollie.capturethewool.core.pve.boss.events.BossEventsAdapter;
import me.ollie.capturethewool.core.pve.boss.events.BossMinionDeathEvent;
import me.ollie.capturethewool.core.pve.boss.phase.EndCondition;
import me.ollie.capturethewool.core.util.HealthDisplay;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Team;
import org.checkerframework.checker.units.qual.A;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * god i hate singleton managers (and yes, this is shade thrown at the majority of MC plugins lmao), but the static stuff
 * in Boss was getting messy, and hence this exists. This is dog shit, but it is what it is lmao
 */
public class BossManager {

    private final Map<Integer, Boss<?>> BOSS_MAP = new HashMap<>();

    private final Map<Integer, Boss<?>> ASSOCIATED_ENEMIES_MAP = new HashMap<>();

    private final BossEventsAdapter adapter;

    private final Listener bossListeners;

    @Getter
    private static BossManager instance;

    public BossManager(JavaPlugin plugin) {
        adapter = new BossEventsAdapter();
        bossListeners = new Listener();

        Bukkit.getPluginManager().registerEvents(adapter, plugin);
        Bukkit.getPluginManager().registerEvents(bossListeners, plugin);

        instance = this;
    }


    public void put(LivingEntity entity, Boss<?> boss) {
        BOSS_MAP.put(entity.getEntityId(), boss);
    }

    public void remove(LivingEntity entity) {
        BOSS_MAP.remove(entity.getEntityId());
    }

    public Boss<?> get(LivingEntity entity) {
        return BOSS_MAP.get(entity.getEntityId());
    }

    public Boss<?> getFromAssociated(Enemy<?> enemy) {
        return ASSOCIATED_ENEMIES_MAP.get(enemy.getEntity().getEntityId());
    }

    public Boss<?> getFromAssociated(LivingEntity entity) {
        return ASSOCIATED_ENEMIES_MAP.get(entity.getEntityId());
    }

    public void registerAssociated(Enemy<?> enemy, Boss<?> boss) {
        registerAssociated(enemy.getEntity(), boss);
    }

    public void registerAssociated(LivingEntity entity, Boss<?> boss) {
        ASSOCIATED_ENEMIES_MAP.put(entity.getEntityId(), boss);

    }

    private static class Listener implements org.bukkit.event.Listener {
        @EventHandler
        public void healthNextPhase(BossDamageByPlayerEvent event) {

            if (!(event.getBoss().getCurrPhase().endCondition() instanceof EndCondition.HealthReduction)) return;

            double health = event.getNewHealth();
            double maxHealth = event.getBoss().getEnemy().getMaxHealth();
            long healthPhasesSize = event.getBoss().getPhases().sizeOfHealthBasedPhases();
            int currHealthPhase = event.getBoss().getPhases().getCurrHealthPhase();

            if (health <= (maxHealth / healthPhasesSize) * (healthPhasesSize - (currHealthPhase + 1))) {
                event.getBoss().nextPhase();
            }
        }

        @EventHandler(priority = EventPriority.MONITOR)
        public void cleanup(BossDeathEvent event) {
            event.getBoss().getCurrPhase().onFinish();
            BossManager.getInstance().remove(event.getBoss().getEnemy().getEntity());
        }

        @EventHandler(priority = EventPriority.MONITOR)
        public void allEntitiesDead(BossMinionDeathEvent event) {
            EndCondition condition = event.getBoss().getCurrPhase().endCondition();

            if (!(condition instanceof EndCondition.EnemiesDead enemiesDead)) return;

            Collection<? extends Enemy<?>> enemies = enemiesDead.entities();

            enemies.stream().map(e -> HealthDisplay.removeHealth(e.getEntity().getCustomName())).forEach(e -> Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage(e)));
            enemies.remove(event.getEnemy());

            if (enemies.size() <= 0) {
                event.getBoss().nextPhase();
            }
        }

        @EventHandler
        public void removeTeam(BossDeathEvent event) {
            Team team = Bukkit.getScoreboardManager().getMainScoreboard()
                    .getTeam(event.getBoss().getColour().getTeamName(event.getBoss()));
            if (team != null) team.unregister();
        }
    }
}
