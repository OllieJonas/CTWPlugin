package me.ollie.capturethewool.core.ability;

import me.ollie.capturethewool.CaptureTheWool;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class Ability {

    protected static final JavaPlugin PLUGIN = CaptureTheWool.getInstance();

    private String name;

    public abstract void power(LivingEntity user);
}
