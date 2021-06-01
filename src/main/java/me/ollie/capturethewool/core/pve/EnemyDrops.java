package me.ollie.capturethewool.core.pve;

import me.ollie.capturethewool.CaptureTheWool;
import me.ollie.capturethewool.core.hologram.DroppedItemHologram;
import me.ollie.capturethewool.core.util.ItemStackUtil;
import me.ollie.capturethewool.core.util.ListUtil;
import me.ollie.capturethewool.items.ItemRarity;
import me.ollie.capturethewool.items.PowerfulItemRegistry;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public record EnemyDrops(Set<ChanceDrop> drops) {

    private static final Random RANDOM = new Random();

    private static final JavaPlugin PLUGIN = CaptureTheWool.getInstance();

    public List<DroppedItemHologram> drop(Entity entity) {
        return drop(entity, entity.getLocation().getNearbyPlayers(32));
    }

    public List<DroppedItemHologram> drop(Entity entity, Collection<? extends Player> audience) {
        return drops.stream()
                .filter(e -> passes(e.chance()))
                .map(ChanceDrop::drop)
                .map(e -> e.asHologram(entity, audience))
                .collect(Collectors.toList());
    }

    private boolean passes(float f) {
        return RANDOM.nextFloat() <= f;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final Set<ChanceDrop> drops;

        private Builder() {
            this.drops = new HashSet<>();
        }

        public Builder drop(float chance, Drop drop) {
            return drop(new ChanceDrop(chance, drop));
        }

        public Builder drop(ChanceDrop drop) {
            drops.add(drop);
            return this;
        }

        public Builder drops(Set<ChanceDrop> d) {
            drops.addAll(d);
            return this;
        }

        public Builder drops(EnemyDrops drops) {
            return drops(drops.drops());
        }

        public EnemyDrops build() {
            return new EnemyDrops(drops);
        }
    }

    public record ChanceDrop(float chance, Drop drop) {}

    sealed interface Drop permits Normal, Special, Unique {
        DroppedItemHologram asHologram(Entity entity, Collection<? extends Player> audience);

        ItemStack asItem();
    }

    public final record Normal(ItemStack item, int randomAmount) implements Drop {

        @Override
        public DroppedItemHologram asHologram(Entity entity, Collection<? extends Player> audience) {
            return DroppedItemHologram.entityDrop(PLUGIN, entity, item.asQuantity(1 + RANDOM.nextInt(randomAmount)), audience);
        }

        @Override
        public ItemStack asItem() {
            return item;
        }
    }

    public final record Special(Supplier<ItemStack> item, DroppedItemHologram.OnPickup onPickup) implements Drop {

        public static Special of(Supplier<ItemStack> item) {
            return new Special(item, DroppedItemHologram.OnPickup.IDENTITY);
        }

        @Override
        public DroppedItemHologram asHologram(Entity entity, Collection<? extends Player> audience) {
            return DroppedItemHologram.specialDrop(PLUGIN, entity, item.get(), audience, onPickup);
        }

        @Override
        public ItemStack asItem() {
            return item.get();
        }
    }

    public final record Unique(Supplier<ItemStack> item, DroppedItemHologram.OnPickup onPickup, DroppedItemHologram.OnDrop onDrop) implements Drop {

        public static Unique of(Supplier<ItemStack> item) {
            return new Unique(item, DroppedItemHologram.OnPickup.IDENTITY, DroppedItemHologram.OnDrop.IDENTITY);
        }

        public static Unique of(Supplier<ItemStack> item, DroppedItemHologram.OnPickup onPickup) {
            return new Unique(item, onPickup, DroppedItemHologram.OnDrop.IDENTITY);
        }

        public static Unique of(Supplier<ItemStack> item, DroppedItemHologram.OnDrop onDrop) {
            return new Unique(item, DroppedItemHologram.OnPickup.IDENTITY, onDrop);
        }

        @Override
        public DroppedItemHologram asHologram(Entity entity, Collection<? extends Player> potentialRecipients) {
            return DroppedItemHologram.uniqueDrop(PLUGIN, entity, item.get(), ListUtil.random(potentialRecipients), onPickup, onDrop);
        }

        @Override
        public ItemStack asItem() {
            return item.get();
        }
    }
}
