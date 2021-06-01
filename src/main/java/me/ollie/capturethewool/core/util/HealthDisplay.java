package me.ollie.capturethewool.core.util;

import lombok.Getter;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("RegExpDuplicateCharacterInClass")
public class HealthDisplay {

    private JavaPlugin plugin;

    private static final Pattern MATCH_PATTERN = Pattern.compile("([^(\\d+/\\d+)]*)");

    private final Map<Integer, LivingEntity> entities;

    @Getter
    private static HealthDisplay instance;

    public HealthDisplay(JavaPlugin plugin) {
        this.plugin = plugin;
        this.entities = new HashMap<>();

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

            if (!isTarget(entity)) return;

            String originalName = entity.getName();
            Matcher matcher = MATCH_PATTERN.matcher(originalName);

            if (!matcher.find() || matcher.groupCount() != 1) return;

            String name = StringUtils.strip(matcher.group(0));

            entity.setCustomName(getDisplayName(name, entity, damage));

        }

        @EventHandler(priority = EventPriority.MONITOR)
        public void onSpawn(EntitySpawnEvent event) {
            if (!(event.getEntity() instanceof LivingEntity)) return;
            LivingEntity entity = (LivingEntity) event.getEntity();
            if (!isTarget(entity)) return;

            String name = entity.getCustomName() == null ? entity.getName() : entity.getCustomName();
            entity.setCustomName(getDisplayName(name, entity, 0));
            entity.setCustomNameVisible(true);
        }
    }
}
