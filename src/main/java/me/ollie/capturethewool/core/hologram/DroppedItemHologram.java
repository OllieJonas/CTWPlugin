package me.ollie.capturethewool.core.hologram;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.line.TextLine;
import me.ollie.capturethewool.core.GamesCore;
import me.ollie.capturethewool.core.hologram.meta.HologramBuilder;
import me.ollie.capturethewool.core.util.ItemStackUtil;
import me.ollie.capturethewool.core.util.LocationUtil;
import me.ollie.capturethewool.items.ItemRarity;
import me.ollie.capturethewool.items.PowerfulItemRegistry;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Collections;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class DroppedItemHologram {

    private static final int DEFAULT_DROP_RADIUS = 3;

    @FunctionalInterface
    public interface OnPickup {
        OnPickup IDENTITY = (p, l) -> {};

        void onPickup(Player player, ItemStack item);

        default OnPickup andThen(OnPickup after) {
            return (p, l) -> {
                onPickup(p, l);
                after.onPickup(p, l);
            };
        }

        default HologramBuilder.OnPickup toHologramPickup() {
            return ((player, line) -> onPickup(player, line.getItemStack()));
        }
    }

    public interface OnDrop {
        OnDrop IDENTITY = (p, i) -> {};

        void onDrop(Collection<? extends Player> players, ItemStack itemStack);
    }

    private final JavaPlugin plugin;

    private final Location location;

    private final ItemStack item;

    private Collection<? extends Player> audience;

    private final OnPickup onPickup;

    private final boolean displayTitle;

    private Hologram itemHologram;

    public static DroppedItemHologram uniqueDrop(JavaPlugin plugin, Entity entity, ItemStack item, Player player, OnPickup onPickup, OnDrop onDrop) {
        return entityDrop(plugin, entity, item, DEFAULT_DROP_RADIUS, true, Collections.singleton(player), onPickup, onDrop);
    }

    public static DroppedItemHologram entityDrop(JavaPlugin plugin, Entity entity, ItemStack item) {
        return entityDrop(plugin, entity, item, DEFAULT_DROP_RADIUS, true, Bukkit.getOnlinePlayers(), OnPickup.IDENTITY);
    }

    public static DroppedItemHologram entityDrop(JavaPlugin plugin, Entity entity, ItemStack item, Collection<? extends Player> audience) {
        return entityDrop(plugin, entity, item, DEFAULT_DROP_RADIUS, true, audience, OnPickup.IDENTITY);
    }

    public static DroppedItemHologram specialDrop(JavaPlugin plugin, Entity entity, ItemStack item, Collection<? extends Player> players, OnPickup onPickup) {
        return entityDrop(plugin, entity, item, DEFAULT_DROP_RADIUS, true, players, onPickup);
    }

    public static DroppedItemHologram entityDrop(JavaPlugin plugin, Entity entity, ItemStack item, int dropRadius, boolean displayTitle, Collection<? extends Player> audience, OnPickup onPickup) {
        return entityDrop(plugin, entity, item, dropRadius, displayTitle, audience, onPickup, OnDrop.IDENTITY);
    }

    public static DroppedItemHologram entityDrop(JavaPlugin plugin, Entity entity, ItemStack item, int dropRadius, boolean displayTitle, Collection<? extends Player> audience, OnPickup onPickup, OnDrop onDrop) {
        Location location = LocationUtil.randomLocationAround(entity.getLocation(), dropRadius).add(0, 1, 0);
        return new DroppedItemHologram(plugin, location, displayTitle, item, audience, onPickup, onDrop);
    }

    public DroppedItemHologram(JavaPlugin plugin, Location location, boolean displayTitle, ItemStack item, Collection<? extends Player> audience, OnPickup onPickup) {
        this(plugin, location, displayTitle, item, audience, onPickup, (ignore1, ignore2) -> {});
    }
    public DroppedItemHologram(JavaPlugin plugin, Location location, boolean displayTitle, ItemStack item, Collection<? extends Player> audience, OnPickup onPickup, OnDrop onCreation) {
        this.plugin = plugin;
        this.location = location;
        this.displayTitle = displayTitle;
        this.item = item;
        this.audience = audience;
        this.onPickup = onPickup;

        buildHolograms();
        onCreation.onDrop(audience, item);
    }

    private void buildHolograms() {
        this.itemHologram = buildPickupHologram();
    }

    private Hologram buildPickupHologram() {
        return new HologramBuilder(plugin, location)
                .isVisibleByDefault(false)
                .visibleTo(audience)
                .line(displayTitle, ItemStackUtil.getNameAndAmount(item))
                .itemP(item, onPickup.andThen((player, i) -> {
                    if (player.getInventory().firstEmpty() == -1) return;

                    addItem(player, i);
                    destroy();
        }).toHologramPickup()).build();
    }

    public void destroy() {
        this.itemHologram.delete();
        this.itemHologram = null;
        this.audience = null;
    }

    private void addItem(Player player, ItemStack item) {
        player.getInventory().addItem(item);
        ProtocolManager manager = GamesCore.getInstance().getProtocolManager();
        PacketContainer container = new PacketContainer(PacketType.Play.Server.COLLECT);
        Collection<Entity> nearbyEntities = itemHologram.getLocation().getNearbyEntities(1, 1, 1);
        int itemId = nearbyEntities.stream()
                .filter(e -> equals(e.getType(), EntityType.DROPPED_ITEM))
                .map(Entity::getEntityId)
                .findFirst()
                .orElse(-1);

        if (itemId == -1) return;

        container.getIntegers()
                .write(0, itemId)
                .write(1, player.getEntityId())
                .write(2, 10);

        try {
            manager.sendServerPacket(player, container);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private boolean equals(EntityType e1, EntityType e2) {
        return e1.name().equals(e2.name());
    }

}
