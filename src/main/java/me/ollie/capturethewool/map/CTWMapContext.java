package me.ollie.capturethewool.map;

import lombok.Builder;
import me.ollie.capturethewool.dungeon.Dungeon;
import org.bukkit.Location;

import java.util.Collection;

@SuppressWarnings("ClassCanBeRecord")
@Builder
public class CTWMapContext {

    private final Location redSpawnLocation;

    private final Location blueSpawnLocation;

    private final Collection<Location> ironVillagerLocations;

    private final Collection<Location> goldVillagerLocations;

    private final Collection<Location> diamondVillagerLocations;

    private final Collection<Location> obsidianVillagerLocations;

    private final Collection<Location> exchangeVillagerLocations;

    private final Collection<Dungeon> ironDungeons;

    private final Collection<Dungeon> goldDungeon;

    private final Collection<Dungeon> diamondDungeons;
}
