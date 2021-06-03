package me.ollie.capturethewool.core.pve.boss;

import me.ollie.capturethewool.CaptureTheWool;
import me.ollie.capturethewool.core.GamesCore;
import me.ollie.capturethewool.core.ability.Ability;
import me.ollie.capturethewool.core.bossbar.MobBossBar;
import me.ollie.capturethewool.core.pve.Enemy;
import me.ollie.capturethewool.core.pve.Modifier;
import me.ollie.capturethewool.core.util.VectorCircle;
import me.ollie.capturethewool.core.util.stream.Permutations;
import me.ollie.capturethewool.enemy.DropsRegistry;
import net.kyori.adventure.bossbar.BossBar;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.Vector;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public abstract class Boss<T extends LivingEntity> extends Enemy<T> {

    private T entity;

    private BossBar.Color colour;

    private MobBossBar bossBar;

    private Set<VectorCircle> invincibleCircles;

    private Phase currPhase;

    private List<Phase> phases;

    public Boss(String name, Class<T> clazz, BossBar.Color colour, List<Modifier> modifiers, Location location) {
        super(clazz, name, DropsRegistry.BOSS_DROPS, modifiers);
        this.colour = colour;
        this.entity = create(location);
    }

    private T create(Location location) {
        T entity = super.spawn(location);
        bossBar = new MobBossBar(PLUGIN, entity, colour, location.getNearbyPlayers(32));
        entity.setGlowing(true);
        return entity;
    }

    private void changeGlowingColour(T entity, ChatColor colour) {
        Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
        Team team = board.registerNewTeam("blueTeam");
        team.setColor(colour);
        team.addEntry(entity.getUniqueId().toString());
    }

    protected void setInvincible(T entity, Location teleport) {
        entity.setInvulnerable(true);
        List<Double> doubles = new ArrayList<>();
        doubles.add(-0.3);
        doubles.add(-0.8);
        doubles.add(0.1);
        doubles.add(0.7);

        AtomicLong aLong = new AtomicLong(1);

        // List<VectorCircle> circles = IntStream.range(0, bees.size()).mapToObj(i -> new VectorCircle(player, axes.get(i), 0.1, 2.75, l -> bees.get(i).teleport(l))).toList();
        invincibleCircles = Permutations.of(doubles, 3)
                .map(p -> new Vector(p.get(0), p.get(1), p.get(2)))
                .map(v -> new VectorCircle(entity, v, 0.2, 2.75,
                        l -> l.getWorld().spawnParticle(Particle.REDSTONE, l, 1,
                                new Particle.DustOptions(Color.WHITE, 1))))
                .collect(Collectors.toSet());
        invincibleCircles.forEach(c -> c.runTaskTimer(CaptureTheWool.getInstance(), aLong.getAndAdd(2), 1L));
    }
}
