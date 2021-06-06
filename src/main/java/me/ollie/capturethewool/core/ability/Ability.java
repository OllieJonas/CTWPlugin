package me.ollie.capturethewool.core.ability;

import me.ollie.capturethewool.CaptureTheWool;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class Ability {

    public interface DamageContext {

        LivingEntity target();

        double damage();
    }

    public record SimpleDamageContext(LivingEntity target, double damage) implements DamageContext {}

    protected static final JavaPlugin PLUGIN = CaptureTheWool.getInstance();

    private String name;

    public abstract void damage(DamageContext context);

    public abstract void power(LivingEntity user);
}
