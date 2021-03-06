package me.ollie.capturethewool.core.util;

import lombok.Getter;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.*;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("RegExpDuplicateCharacterInClass")
public record HealthDisplay(JavaPlugin plugin) {

    public static final Pattern MATCH_PATTERN = Pattern.compile("([^(\\d+/\\d+)]*)");

    @Getter
    private static HealthDisplay instance;

    public HealthDisplay(JavaPlugin plugin) {
        this.plugin = plugin;

        plugin.getServer().getPluginManager().registerEvents(new Listener(), plugin);

        instance = this;
    }

    public Entity attach(LivingEntity entity) {
        entity.setMetadata("healthdisplay", new FixedMetadataValue(plugin, true));
        String name = entity.getCustomName() == null ? entity.getName() : entity.getCustomName();
        entity.setCustomName(getDisplayName(name, entity, 0));
        entity.setCustomNameVisible(true);
        return entity;
    }

    public boolean isTarget(Entity entity) {
        return entity.hasMetadata("healthdisplay");
    }

    public String getDisplayName(String original, LivingEntity entity, double damage) {
        double maxHealth = MathsUtil.roundHalf(Objects.requireNonNull(entity.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue());
        double health = Math.max(0, MathsUtil.roundHalf(entity.getHealth() - damage));

        return original + (health == maxHealth ? ChatColor.GREEN : health != 0.0 ? ChatColor.YELLOW : ChatColor.RED) + " " + health + ChatColor.GRAY + " / " + ChatColor.GREEN + maxHealth;
    }

    public static String removeHealth(String name) {
        Matcher matcher = MATCH_PATTERN.matcher(name);

        if (!matcher.find() || matcher.groupCount() != 1) return name;

        return StringUtils.strip(matcher.group(0));
    }


    private class Listener implements org.bukkit.event.Listener {

        @EventHandler(priority = EventPriority.MONITOR)
        public void onDamage(EntityDamageEvent event) {
            onDamage(event.getEntity(), event.getDamage());
        }


        @EventHandler(priority = EventPriority.MONITOR)
        public void onDamage(EntityDamageByEntityEvent event) {
            onDamage(event.getEntity(), event.getDamage());
        }


        @EventHandler(priority = EventPriority.MONITOR)
        public void onDamage(EntityDamageByBlockEvent event) {
            onDamage(event.getEntity(), event.getDamage());
        }

        private void onDamage(Entity e, double damage) {
            if (!(e instanceof LivingEntity)) return;

            LivingEntity entity = (LivingEntity) e;

            String name = removeHealth(entity.getName());

            if (!isTarget(entity)) return;

            entity.setCustomName(getDisplayName(name, entity, damage));

        }

        @EventHandler(priority = EventPriority.MONITOR)
        public void onSpawn(EntitySpawnEvent event) {
            if (!(event.getEntity() instanceof LivingEntity entity)) return;
            if (!isTarget(entity)) return;

            String name = entity.getCustomName() == null ? entity.getName() : entity.getCustomName();
            entity.setCustomName(getDisplayName(name, entity, 0));
            entity.setCustomNameVisible(true);
        }
    }
}
