package me.ollie.capturethewool.core.pve.boss;

import lombok.Getter;
import me.ollie.capturethewool.core.GamesCore;
import me.ollie.capturethewool.core.bossbar.MobBossBar;
import me.ollie.capturethewool.core.pve.Enemy;
import me.ollie.capturethewool.core.pve.animation.SpawnAnimation;
import me.ollie.capturethewool.core.pve.boss.events.BossDamageByPlayerEvent;
import me.ollie.capturethewool.core.pve.boss.events.BossDeathEvent;
import me.ollie.capturethewool.core.pve.boss.events.BossEventsAdapter;
import me.ollie.capturethewool.core.pve.boss.events.BossMinionDeathEvent;
import me.ollie.capturethewool.core.pve.boss.phase.EndCondition;
import me.ollie.capturethewool.core.pve.boss.phase.Phase;
import me.ollie.capturethewool.core.util.HealthDisplay;
import me.ollie.capturethewool.enemy.DropsRegistry;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.*;
import java.util.function.Supplier;

@Getter
public abstract class Boss<T extends LivingEntity>  {

    protected static final JavaPlugin PLUGIN = GamesCore.getInstance().getPlugin();

    protected final Enemy<T> enemy;

    protected final Colour colour;

    protected MobBossBar bossBar;

    protected final PhaseList phases;

    protected Phase currPhase;

    public Boss(Enemy<T> enemy, Colour colour) {
        this.enemy = enemy;
        this.colour = colour;
        this.phases = phases().get();
    }

    public T spawn(Location location) {
        return spawn(location, null);
    }

    public T spawn(Location location, SpawnAnimation animation) {
        enemy.setDrops(DropsRegistry.BOSS_DROPS);
        T entity = enemy.spawn(location, animation);
        bossBar = new MobBossBar(PLUGIN, entity, colour.getBossBarColour(), location.getNearbyPlayers(32));
        setGlowing(entity, colour);
        this.currPhase = phases.peek();
        BossManager.getInstance().put(entity, this);

        return entity;
    }

    public <E extends Enemy<?>> E spawnMinion(E enemy, Location location, SpawnAnimation animation) {
        enemy.spawn(location, animation);
        BossManager.getInstance().registerAssociated(enemy, this);
        return enemy;
    }

    public abstract Supplier<PhaseList> phases();

    public void nextPhase() {
        currPhase.onFinish();
        Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage("curr: " + currPhase.getClass().getSimpleName()));

        Phase next = phases.next();
        next.onStart();
        Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage("next: " + next.getClass().getSimpleName()));

        currPhase = next;
    }

    private void setGlowing(T entity, Colour colour) {
        entity.setGlowing(true);
        Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
        Team team = board.registerNewTeam(colour.getTeamName(entity.getCustomName()));
        team.setColor(colour.getChatColour());
        team.addEntry(entity.getUniqueId().toString());
    }

//    protected void setInvincible(T entity, Location teleport) {
//        entity.setInvulnerable(true);
//        List<Double> doubles = new ArrayList<>();
//        doubles.add(-0.3);
//        doubles.add(-0.8);
//        doubles.add(0.1);
//        doubles.add(0.7);
//
//        AtomicLong aLong = new AtomicLong(1);
//
//        // List<VectorCircle> circles = IntStream.range(0, bees.size()).mapToObj(i -> new VectorCircle(player, axes.get(i), 0.1, 2.75, l -> bees.get(i).teleport(l))).toList();
//        invincibleCircles = Permutations.of(doubles, 3)
//                .map(p -> new Vector(p.get(0), p.get(1), p.get(2)))
//                .map(v -> new VectorCircle(entity, v, 0.2, 2.75,
//                        l -> l.getWorld().spawnParticle(Particle.REDSTONE, l, 1,
//                                new Particle.DustOptions(Color.WHITE, 1))))
//                .collect(Collectors.toSet());
//        invincibleCircles.forEach(c -> c.runTaskTimer(CaptureTheWool.getInstance(), aLong.getAndAdd(2), 1L));
//
//        if (teleport != null) entity.teleport(teleport);
//    }
}
