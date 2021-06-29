package me.ollie.capturethewool.dungeon.loot;

import me.ollie.capturethewool.items.types.RarityItem;

import java.util.Collection;

public sealed interface ChestLootGenerationStrategy {

    Collection<RarityItem> items();

    final class Iron implements ChestLootGenerationStrategy {

        @Override
        public Collection<RarityItem> items() {
            return null;
        }
    }


}
