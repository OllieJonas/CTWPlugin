package me.ollie.capturethewool.core.bossbar;

import me.ollie.capturethewool.core.util.HealthDisplay;
import me.ollie.capturethewool.core.util.MathsUtil;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class MobBossBar {

    private static final Map<LivingEntity, MobBossBar> bossMap = new HashMap<>();

    private static Listener listener;

    private final JavaPlugin plugin;

    private final LivingEntity entity;

    private final BossBar.Color colour;

    private final double maxHealth;

    private final Collection<Player> audience;

    private final BossBar bossBar;

    private double currentHealth;

    public MobBossBar(JavaPlugin plugin, LivingEntity entity, BossBar.Color colour) {
        this(plugin, entity, colour, new HashSet<>());
    }

    public MobBossBar(JavaPlugin plugin, LivingEntity entity, BossBar.Color colour, Collection<Player> audience) {
        this.plugin = plugin;
        this.entity = entity;
        this.colour = colour;
        this.audience = audience;
        this.maxHealth = Objects.requireNonNull(entity.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue();
        this.currentHealth = entity.getHealth();

        this.bossBar = buildBossBar();

        if (listener == null) {
            listener = new Listener();
            plugin.getServer().getPluginManager().registerEvents(new Listener(), plugin);
        }

        audience.forEach(p -> p.showBossBar(bossBar));

        init();
    }

    public void showTo(Player player) {
        audience.add(player);
        player.showBossBar(bossBar);
    }

    public void hideFrom(Player player) {
        audience.remove(player);
        player.hideBossBar(bossBar);
    }

    private BossBar buildBossBar() {
        return BossBar.bossBar(Component.text(HealthDisplay.removeHealth(entity.getName())), 1.0F, colour, BossBar.Overlay.PROGRESS);
    }

    public void init() {
        bossBar.addFlag(BossBar.Flag.CREATE_WORLD_FOG);

        bossMap.put(entity, this);
    }

    public void update(double damage) {
        if (entity.isDead())
            destroy();



        this.currentHealth = MathsUtil.round(entity.getHealth() - damage, 1);

        bossBar.progress(Math.max(0, (float) (currentHealth / maxHealth)));
    }

    public void destroy() {
        bossMap.remove(entity);
        audience.forEach(p -> p.hideBossBar(bossBar));
        audience.clear();
    }


    private static class Listener implements org.bukkit.event.Listener {

        @EventHandler
        public void onDamage(EntityDamageEvent event) {
            if (event.getEntity() instanceof LivingEntity entity) {
                MobBossBar bar = bossMap.get(entity);
                if (bar != null) bar.update(event.getDamage());
            }
        }

        @EventHandler
        public void onDeath(EntityDeathEvent event) {
            MobBossBar bar = bossMap.get(event.getEntity());
            if (bar != null) bar.destroy();
        }
    }


}
