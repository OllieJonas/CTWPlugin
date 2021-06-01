package me.ollie.capturethewool.core.pve;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class EnemyDrops {

    private static final Random RANDOM = new Random();

    // supplier to allow for random common / uncommon / rare drops.
    @Getter
    private final Map<Float, Drop> drops;

    private EnemyDrops(Map<Float, Drop> drops) {
        this.drops = drops;
    }

    public EnemyDrops addAll(EnemyDrops drops) {
        this.drops.putAll(drops.getDrops());
        return this;
    }

    public List<ItemStack> randomDrops() {
        return drops.entrySet().stream()
                .filter(e -> RANDOM.nextFloat() <= e.getKey())
                .map(e -> e.getValue().getItem())
                .collect(Collectors.toList());
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final Map<Float, Drop> drops;

        public Builder() {
            this.drops = new HashMap<>();
        }

        public Builder drop(Float chance, ItemStack item) {
            drops.put(chance, Drop.of(item));
            return this;
        }

        public Builder drop(Float chance, Supplier<ItemStack> item) {
            drops.put(chance, Drop.of(item, 1));
            return this;
        }

        public Builder drop(Float chance, Supplier<ItemStack> item, int randomQuantity) {
            drops.put(chance, Drop.of(item, randomQuantity));
            return this;
        }

        public Builder drop(Float chance, Drop drop) {
            drops.put(chance, drop);
            return this;
        }

        public Builder drop(Float chance, ItemStack drop, int randomQuantity) {
            drops.put(chance, Drop.of(() -> drop, randomQuantity));
            return this;
        }

        public EnemyDrops build() {
            return new EnemyDrops(drops);
        }
    }

    @AllArgsConstructor(staticName = "of")
    public static class Drop {

        private final Supplier<ItemStack> itemStack;

        private final int randomQuantity;

        public static Drop of(ItemStack item) {
            return Drop.of(() -> item, 1);
        }

        public ItemStack getItem() {
            return itemStack.get().asQuantity(RANDOM.nextInt(randomQuantity) + 1);
        }
    }
}
