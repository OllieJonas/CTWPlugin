package me.ollie.capturethewool.dungeon.rooms;

import lombok.Getter;
import me.ollie.capturethewool.core.pve.Spawner;
import me.ollie.capturethewool.core.util.region.Region;
import me.ollie.capturethewool.dungeon.loot.ChestLootGenerationStrategy;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashSet;
import java.util.function.Consumer;

@Getter
public abstract class Room {

    protected final Region region;

    protected Collection<Spawner<?>> spawners;

    protected ChestLootGenerationStrategy lootGenerationStrategy;

    public Room(Region region) {
        this.region = region;
        this.spawners = new HashSet<>();
    }

    public Room lootGenerationStrategy(ChestLootGenerationStrategy lootGenerationStrategy) {
        this.lootGenerationStrategy = lootGenerationStrategy;
        return this;
    }

    public abstract void onEnter(Player player);

    public abstract void onLeave(Player player);

    public abstract void init();
}
