package me.ollie.capturethewool.core.pve;

import lombok.Getter;
import lombok.Setter;
import me.ollie.capturethewool.core.GamesCore;
import me.ollie.capturethewool.core.pve.animation.SpawnAnimation;
import me.ollie.capturethewool.core.util.HealthDisplay;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.function.Supplier;

@SuppressWarnings("UnusedReturnValue")
@Getter
public abstract class Enemy<T extends LivingEntity> {

    private static final Location IDENTITY = new Location(Bukkit.getWorld("world"), 0, 5, 0);

//    protected static final EnemyDrops NORMAL_DROPS = EnemyDrops.builder().drop(1.0F, CurrencyRegistry.IRON_BUCKS.itemRepresentation(), 3).build();

    private static final Map<Integer, Enemy<?>> idToEnemyMap = new HashMap<>();

    protected static final JavaPlugin PLUGIN = GamesCore.getInstance().getPlugin();

    protected final Class<T> entityClazz;

    @Getter
    protected T entity;

    protected final String displayName;

    protected final List<Modifier> modifiers;

    @Setter
    protected EnemyDrops drops;

    private static org.bukkit.event.Listener listener;

    public Enemy(Class<T> entityClazz, EnemyDrops drops) {
        this(entityClazz, entityClazz.getSimpleName(), drops, Collections.emptyList());
    }

    public Enemy(Class<T> clazz, Supplier<String> displayName, EnemyDrops drops, List<Modifier> modifiers) {
        this(clazz, displayName.get(), drops, modifiers);
    }

    public Enemy(Class<T> entityClazz, String displayName, EnemyDrops drops, List<Modifier> modifiers) {
        this.entityClazz = entityClazz;
        this.displayName = displayName;
        this.drops = drops;
        this.modifiers = modifiers;

        if (listener == null) {
            listener = new Listener();
            PLUGIN.getServer().getPluginManager().registerEvents(new Listener(), PLUGIN);
        }
    }

    public T spawn(Location location) {
        return spawn(location, CreatureSpawnEvent.SpawnReason.NATURAL, null);
    }

    public T spawn(Location location, SpawnAnimation animation) {
        return spawn(location, CreatureSpawnEvent.SpawnReason.NATURAL, animation);
    }

    public T spawn(Location location, CreatureSpawnEvent.SpawnReason spawnReason, SpawnAnimation animation) {
        if (this.entity == null) {
            T entity = location.getWorld().spawn(animation != null ? animation.location(location) : location, entityClazz, spawnReason);
            entity.setCustomName(displayName);
            entity.setCustomNameVisible(true);
            modifiers.forEach(m -> Optional.ofNullable(entity.getAttribute(m.attribute())).ifPresent(a -> a.addModifier(m.toModifier())));
            entity.setHealth(Objects.requireNonNull(entity.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue());
            HealthDisplay.getInstance().attach(entity);
            idToEnemyMap.put(entity.getEntityId(), this);
            transformer(entity);

            this.entity = entity;

            if (animation != null) animation.play(entity);
        }
        return entity;
    }

    public abstract void transformer(T enemy);

    public static Modifier modifier(Attribute attribute, AttributeModifier.Operation operation, double amount) {
        return new Modifier(attribute, operation, amount);
    }

    private static class Listener implements org.bukkit.event.Listener {

        @EventHandler
        public void onDeath(EntityDeathEvent event) {
            if (event.getEntity() instanceof Player) return;
            LivingEntity entity = event.getEntity();
            Enemy<?> enemy = idToEnemyMap.get(entity.getEntityId());
            if (enemy == null) return;
            event.getDrops().clear();
            EnemyDrops drops = enemy.getDrops();
            drops.drop(entity);
        }
    }
}
