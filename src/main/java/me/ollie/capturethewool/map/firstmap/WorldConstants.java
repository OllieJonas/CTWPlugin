package me.ollie.capturethewool.map.firstmap;

import lombok.Getter;
import me.ollie.capturethewool.map.CTWMapContext;
import me.ollie.capturethewool.map.ReducedLocation;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.List;

public class WorldConstants {

    public static final World WORLD = Bukkit.getWorld("Capture_the_Wool");

    // Red stuff
    private static final ReducedLocation RED_SPAWN_LOCATION = new ReducedLocation(100, 47, -102, -45, 0);

    private static final ReducedLocation RED_IRON_VILLAGER_LOCATION = new ReducedLocation(110, 47, -108, 0, 0);

    private static final ReducedLocation RED_GOLD_VILLAGER_LOCATION = new ReducedLocation(113, 47, -105, 90, 0);

    private static final ReducedLocation RED_DIAMOND_VILLAGER_LOCATION = new ReducedLocation(110, 47, -101, 180, 0);

    private static final ReducedLocation RED_EXCHANGE_VILLAGER_LOCATION = new ReducedLocation(108.5, 47, -96.5, 90, 0);

    // Blue stuff
    private static final ReducedLocation BLUE_SPAWN_LOCATION = new ReducedLocation(-100, 47, 102, -135, 0);

    private static final ReducedLocation BLUE_IRON_VILLAGER_LOCATION = new ReducedLocation(-110, 47, 108, 0, 0);

    private static final ReducedLocation BLUE_GOLD_VILLAGER_LOCATION = new ReducedLocation(-113, 47, 105, 90, 0);

    private static final ReducedLocation BLUE_DIAMOND_VILLAGER_LOCATION = new ReducedLocation(-110, 47, 101, 180, 0);

    private static final ReducedLocation BLUE_EXCHANGE_VILLAGER_LOCATION = new ReducedLocation(-108.5, 47, 96.5, 90, 0);
    
    private final World world;

    @Getter
    private CTWMapContext context;


    public WorldConstants(World world) {
        this.world = world;
    }

    public CTWMapContext build() {
        if (this.context != null) return context;

        return this.context = CTWMapContext.builder()
                .redSpawnLocation(RED_SPAWN_LOCATION.toLocation(world))
                .blueSpawnLocation(BLUE_SPAWN_LOCATION.toLocation(world))

                .ironVillagerLocations(List.of(
                        RED_IRON_VILLAGER_LOCATION.toLocation(world),
                        BLUE_IRON_VILLAGER_LOCATION.toLocation(world)
                ))

                .goldVillagerLocations(List.of(
                        RED_GOLD_VILLAGER_LOCATION.toLocation(world),
                        BLUE_GOLD_VILLAGER_LOCATION.toLocation(world)
                ))

                .diamondVillagerLocations(List.of(
                        RED_DIAMOND_VILLAGER_LOCATION.toLocation(world),
                        BLUE_DIAMOND_VILLAGER_LOCATION.toLocation(world)
                ))

                .exchangeVillagerLocations(List.of(
                        RED_EXCHANGE_VILLAGER_LOCATION.toLocation(world),
                        BLUE_EXCHANGE_VILLAGER_LOCATION.toLocation(world)
                ))

                .obsidianVillagerLocations(List.of(

                ))

                .ironDungeons(List.of(
                        DungeonConstants.JUNGLE_RUINS,
                        DungeonConstants.OLD_LIBRARY
                ))

                .goldDungeons(List.of(
                        DungeonConstants.PIRATE_ISLAND,
                        DungeonConstants.FUNGAL_SWAMP
                ))

                .diamondDungeons(List.of(
                        DungeonConstants.ICE_PALACE,
                        DungeonConstants.FIRE_TOWER
                ))

                .build();
    }



}
