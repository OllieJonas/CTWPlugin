package me.ollie.capturethewool.core.pve;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.ollie.capturethewool.core.util.HealthDisplay;
import me.ollie.capturethewool.shop.CurrencyRegistry;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@SuppressWarnings("UnusedReturnValue")
public abstract class Enemy<T extends LivingEntity> {

    protected static final EnemyDrops NORMAL_DROPS = EnemyDrops.builder().drop(1.0F, CurrencyRegistry.IRON_BUCKS.getItemRepresentation(), 3).build();

    protected final Class<T> entityClazz;

    protected final String displayName;

    protected final List<Modifier> modifiers;

    protected final EnemyDrops drops;

    public Enemy(Class<T> entityClazz, EnemyDrops drops) {
        this(entityClazz, entityClazz.getSimpleName(), drops, Collections.emptyList());
    }
    public Enemy(Class<T> entityClazz, String displayName, EnemyDrops drops, List<Modifier> modifiers) {
        this.entityClazz = entityClazz;
        this.displayName = displayName;
        this.drops = drops;
        this.modifiers = modifiers;
    }

    public T spawn(Location location) {
        return spawn(location, CreatureSpawnEvent.SpawnReason.NATURAL);
    }

    public T spawn(Location location, CreatureSpawnEvent.SpawnReason spawnReason) {
        T enemy = location.getWorld().spawn(location, entityClazz, spawnReason);
        enemy.setCustomName(displayName);
        enemy.setCustomNameVisible(true);
        modifiers.forEach(m -> Optional.ofNullable(enemy.getAttribute(m.getAttribute())).ifPresent(a -> a.addModifier(m.toModifier())));
        enemy.setHealth(Objects.requireNonNull(enemy.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue());
        HealthDisplay.getInstance().attach(enemy);
        transformer(enemy);
        return enemy;
    }

    public abstract void transformer(T enemy);

    public static Modifier modifier(Attribute attribute, AttributeModifier.Operation operation, double amount) {
        return Modifier.of(attribute, operation, amount);
    }

    @Getter
    @AllArgsConstructor(staticName = "of")
    public static class Modifier {
        private final Attribute attribute;
        private final AttributeModifier.Operation operation;
        private final double amount;

        public AttributeModifier toModifier() {
            return new AttributeModifier(attribute.getKey().asString(), amount, operation);
        }
    }
}
