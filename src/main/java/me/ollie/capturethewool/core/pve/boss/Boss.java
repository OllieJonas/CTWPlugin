package me.ollie.capturethewool.core.pve.boss;

import me.ollie.capturethewool.core.GamesCore;
import me.ollie.capturethewool.core.bossbar.MobBossBar;
import me.ollie.capturethewool.core.pve.Enemy;
import me.ollie.capturethewool.enemy.DropsRegistry;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.function.Supplier;

public abstract class Boss<T extends LivingEntity>  {

    protected static final JavaPlugin PLUGIN = GamesCore.getInstance().getPlugin();

    protected Enemy<T> enemy;

    protected T entity;

    protected final Colour colour;

    protected MobBossBar bossBar;

    protected final PhaseList phases;

    public Boss(Enemy<T> enemy, Colour colour, Location location) {
        this.enemy = enemy;
        this.colour = colour;

        this.entity = create(location);
        this.phases = phases().get();
    }

    private T create(Location location) {
        enemy.setDrops(DropsRegistry.BOSS_DROPS);
        T entity = enemy.spawn(location);
        bossBar = new MobBossBar(PLUGIN, entity, colour.getBossBarColour(), location.getNearbyPlayers(32));
        setGlowing(entity, colour.getChatColour());
        return entity;
    }

    public abstract Supplier<PhaseList> phases();

    private void setGlowing(T entity, ChatColor colour) {
        entity.setGlowing(true);
        Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
        Team team = board.registerNewTeam(colour.name());
        team.setColor(colour);
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
