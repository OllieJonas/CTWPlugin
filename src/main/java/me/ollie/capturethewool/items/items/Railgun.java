package me.ollie.capturethewool.items.items;

import me.ollie.capturethewool.CaptureTheWool;
import me.ollie.capturethewool.items.ItemRarity;
import me.ollie.capturethewool.items.meta.AbilityInformation;
import me.ollie.capturethewool.items.types.PowerfulItem;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.PistonMoveReaction;
import org.bukkit.entity.*;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Railgun extends PowerfulItem {

    // constants

    private static final int RANGE = 50;

    private static final double DAMAGE = 5;

    public Railgun() {
        super("Railgun", "a fun railgun", Material.DIAMOND_HOE, ItemRarity.RARE, AbilityInformation.of("Railgun Blast", "Right click to shoot a railgun blast!", 3.0F));
    }

    @Override
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 1, 1);
        List<Location> locations = player.getLineOfSight(null, RANGE).stream().map(Block::getLocation).collect(Collectors.toList()); // null = only check for air
        for (Location location : locations) {
            fireworkSpark(location);
            if (!location.getNearbyLivingEntities(1).contains(player))
                getBelow(location).ifPresent(b -> {
                    b.setType(Material.FIRE);
                });

            for (LivingEntity entity : location.getNearbyLivingEntities(1)) {
                entity.damage(DAMAGE);
            }
        }
    }

    private void fireworkSpark(Location location) {
        location.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, location, 2);
    }

    private Optional<Block> getBelow(Location location) {
        return IntStream.rangeClosed(1, 4).boxed().map(i -> location.clone().add(0, -i, 0)).map(l -> l.getWorld().getBlockAt(l)).filter(b -> b.getType() == Material.AIR).max(Comparator.comparingInt(Block::getY));
    }
}
