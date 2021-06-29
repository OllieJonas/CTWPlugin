package me.ollie.capturethewool.map.firstmap;

import me.ollie.capturethewool.core.pve.miniboss.MiniBossManager;
import me.ollie.capturethewool.core.util.region.Region;
import me.ollie.capturethewool.dungeon.Dungeon;
import me.ollie.capturethewool.dungeon.loot.ChestLootGenerationStrategy;
import me.ollie.capturethewool.dungeon.rooms.MobRoom;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.Set;

public class DungeonConstants {

    private final MiniBossManager miniBossManager;

    public DungeonConstants(MiniBossManager miniBossManager) {
        this.miniBossManager = miniBossManager;
    }

    // common

    private static final World WORLD = Bukkit.getWorld("Capture_the_Wool");

    // iron

    // jungle ruins
    private static final Location JUNGLE_BOTTOM_LEFT = new Location(WORLD, -100, 60, -14);

    private static final Location JUNGLE_TOP_RIGHT = new Location(WORLD, -129, 122, 15);

    private static final Location JUNGLE_FIRST_ROOM_BOTTOM_LEFT = JUNGLE_BOTTOM_LEFT.clone();

    private static final Location JUNGLE_FIRST_ROOM_TOP_RIGHT = JUNGLE_TOP_RIGHT.clone().add(0, -50, 0);

    private static final Region JUNGLE_RUINS_REGION = new Region(JUNGLE_BOTTOM_LEFT, JUNGLE_TOP_RIGHT);

    private static final Region JUNGLE_FIRST_ROOM_REGION = new Region(JUNGLE_FIRST_ROOM_BOTTOM_LEFT, JUNGLE_FIRST_ROOM_TOP_RIGHT);

    public static final Dungeon JUNGLE_RUINS = Dungeon.builder()
            .name("Jungle Ruins")
            .colour(ChatColor.DARK_GREEN)
            .region(JUNGLE_RUINS_REGION)
            .rooms(Set.of(
                    new MobRoom(JUNGLE_FIRST_ROOM_REGION)
                            .randomSpawners(3)
                            .lootGenerationStrategy(new ChestLootGenerationStrategy.Iron())
            ))

            .shortcuts(Set.of(

            ))

            .build();

    public static final Dungeon OLD_LIBRARY = Dungeon.builder()
            .name("Old Library")
            .colour(ChatColor.GOLD)

            .build();

    // gold
    public static final Dungeon PIRATE_ISLAND = Dungeon.builder()
            .name("Pirate Island")
            .colour(ChatColor.DARK_AQUA)

            .build();

    public static final Dungeon FUNGAL_SWAMP = Dungeon.builder()
            .name("Fungal Swamp")
            .colour(ChatColor.RED)


            .build();

    // diamond
    public static final Dungeon ICE_PALACE = Dungeon.builder()
            .name("Ice Palace")
            .colour(ChatColor.AQUA)

            .build();

    public static final Dungeon FIRE_TOWER = Dungeon.builder()
            .name("Fire Tower")
            .colour(ChatColor.DARK_RED)

            .build();
}
