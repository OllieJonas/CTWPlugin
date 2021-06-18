package me.ollie.capturethewool.core.pve.boss;

import lombok.Getter;
import me.ollie.capturethewool.core.GamesCore;
import me.ollie.capturethewool.core.bossbar.MobBossBar;
import me.ollie.capturethewool.core.pve.Enemy;
import me.ollie.capturethewool.core.pve.animation.SpawnAnimation;
import me.ollie.capturethewool.core.pve.boss.events.BossDamageByPlayerEvent;
import me.ollie.capturethewool.core.pve.boss.events.BossDeathEvent;
import me.ollie.capturethewool.core.pve.boss.phase.Phase;
import me.ollie.capturethewool.core.util.CollectionUtil;
import me.ollie.capturethewool.enemy.DropsRegistry;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Collection;
import java.util.function.Supplier;

@Getter
public abstract class Boss<T extends LivingEntity>  {

    protected static final JavaPlugin PLUGIN = GamesCore.getInstance().getPlugin();

    protected final Enemy<T> enemy;

    protected final Colour colour;

    protected MobBossBar bossBar;

    protected final PhaseList phases;

    protected Phase currPhase;

    private final Task task;

    public Boss(Enemy<T> enemy, Colour colour) {
        this(enemy, colour, 0L, 20L);
    }

    public Boss(Enemy<T> enemy, Colour colour, long delayTask, long repeatTaskTimer) {
        this.enemy = enemy;
        this.colour = colour;
        this.phases = phases().get();
        this.task = new Task();
        task.runTaskTimer(GamesCore.getInstance().getPlugin(), delayTask, repeatTaskTimer);
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

        if (next != null) {
            next.onStart();
            Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage("next: " + next.getClass().getSimpleName()));

            currPhase = next;
        }
    }

    private void setGlowing(T entity, Colour colour) {
        Team prevTeam = Bukkit.getScoreboardManager().getMainScoreboard().getTeam(colour.getTeamName(this));
        if (prevTeam != null) prevTeam.unregister();

        entity.setGlowing(true);
        Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
        Team team = board.registerNewTeam(colour.getTeamName(entity.getCustomName()));
        team.setColor(colour.getChatColour());
        team.addEntry(entity.getUniqueId().toString());
    }

    public Collection<? extends Player> getSurroundingPlayers() {
        return enemy.getEntity().getLocation().getNearbyPlayers(64);
    }

    private void callAbility(AbilityTriggerReason reason) {

        CollectionUtil.random(currPhase.abilitySet().get(reason), BossAbility::isUsable)
                .ifPresent(a -> a.call(getSurroundingPlayers(), this));
    }

    private class Task extends BukkitRunnable {

        @Override
        public void run() {
            callAbility(AbilityTriggerReason.RANDOM_DURATION);
        }
    }

    private class Listener implements org.bukkit.event.Listener {

        @EventHandler
        public void onHurt(BossDamageByPlayerEvent event) {
            event.getBoss().callAbility(AbilityTriggerReason.HIT);
        }

        @EventHandler
        public void onDeath(BossDeathEvent event) {
            task.cancel();
        }
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
