package me.ollie.capturethewool;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import lombok.Getter;
import me.ollie.capturethewool.core.GamesCore;
import me.ollie.capturethewool.core.hologram.PaginatedHologram;
import me.ollie.capturethewool.core.hologram.PaginatedHologramBuilder;
import me.ollie.capturethewool.core.hologram.meta.HologramBuilder;
import me.ollie.capturethewool.core.npc.InteractableVillager;
import me.ollie.capturethewool.core.pve.DisableSpawners;
import me.ollie.capturethewool.core.util.region.Region;
import me.ollie.capturethewool.core.world.ConstantTime;
import me.ollie.capturethewool.dungeon.puzzles.maze.Maze;
import me.ollie.capturethewool.dungeon.puzzles.maze.generator.BacktrackingMazeGenerationStrategy;
import me.ollie.capturethewool.dungeon.puzzles.maze.generator.HuntAndKillMazeGenerationStrategy;
import me.ollie.capturethewool.dungeon.puzzles.maze.render.MazeRenderingStrategy;
import me.ollie.capturethewool.dungeon.puzzles.maze.render.SimpleMazeRenderingStrategy;
import me.ollie.capturethewool.game.CaptureTheWool;
import me.ollie.capturethewool.game.key.KeyListener;
import me.ollie.capturethewool.items.meta.PowerfulItemEvents;
import me.ollie.capturethewool.items.projectile.CTWProjectileRegistry;
import me.ollie.capturethewool.items.swords.AssassinsBlade;
import me.ollie.capturethewool.map.CaptureTheWoolMap;
import me.ollie.capturethewool.map.WorldConstants;
import me.ollie.capturethewool.spawn.JoinLobbyVillager;
import org.bukkit.*;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Getter
    private static Main instance;

    private ProtocolManager protocolManager;

    @Getter
    private GamesCore gamesCore;

    @Override
    public void onLoad() {
        this.protocolManager = ProtocolLibrary.getProtocolManager();
    }


    @Override
    public void onEnable() {
        this.gamesCore = new GamesCore(this, protocolManager);

        this.gamesCore.getHolographicDamageListener().toggle();
        gamesCore.setSpecialProjectileRegistry(new CTWProjectileRegistry().getRegistry());
        
        World world = Bukkit.getWorld("world");
        
        gamesCore.getWorldUtilities().getConstantTime().set(world, ConstantTime.Time.MIDDAY);
        gamesCore.getWorldUtilities().getConstantTime().set(Bukkit.getWorld("Capture_the_Wool"), ConstantTime.Time.NIGHT);
        gamesCore.getWorldUtilities().getConstantWeather().addAll();
        gamesCore.getWorldUtilities().getConstantHunger().add("world");
        gamesCore.getWorldUtilities().getNoDamage().add("world");
        gamesCore.getWorldUtilities().getNoBlockInteraction().add("world");
        gamesCore.getWorldUtilities().getNoMobSpawning().add("world", "Capture_the_Wool");

        gamesCore.init();

        // information hologram loc
        Location informationHologramLoc = new Location(world, 488.5, 7.5, 72.5);
        Location informationHologramPrevButtonLoc = new Location(world, 488, 5.25, 71);
        Location informationHologramNextButtonLoc = new Location(world, 488, 5.25, 74);

        PaginatedHologram paginatedHologram = new PaginatedHologramBuilder(this)
                .title(ChatColor.AQUA + "" + ChatColor.BOLD + "Bee Movie Script")
                .pagesLocation(informationHologramLoc)
                .nextButton(informationHologramNextButtonLoc)
                .prevButton(informationHologramPrevButtonLoc)
                .page(new HologramBuilder(this, informationHologramLoc)
                        .text("According to all known laws of aviation, there's no way a bee should be able to fly. Its wings are too small to get its fat little body off the ground. The bee, of course, flies anyways. Because bees don't care what humans think is impossible.")
                        .build())
                .page(new HologramBuilder(this, informationHologramLoc).text("Yellow black, yellow black, yellow black, yellow black, yellow black, ooo black and yellow lets shake it up a little!").build())
                .page(new HologramBuilder(this, informationHologramLoc).text("Barry, breakfast is ready! Coming! Hang on a second. Hey Barry, Hey Adam.").build())
                .page(new HologramBuilder(this, informationHologramLoc).text("Can you believe this is happening? No I can't I'll pick you up!").build())
                .page(new HologramBuilder(this, informationHologramLoc).text("Barry use the stairs your father paid good money for those! Sorry, I'm excited!").build())
                .page(new HologramBuilder(this, informationHologramLoc).text("yan you really do suck").build())
                .build();

        paginatedHologram.init();

        Location leftVillagerLoc = new Location(world, 479.5, 4, 60.5);
        Location rightVillagerLoc = new Location(world, 467.5, 4, 60.5);

        new JoinLobbyVillager(this, gamesCore.getLobbyManager(), CaptureTheWool.class, leftVillagerLoc);
        new JoinLobbyVillager(this, gamesCore.getLobbyManager(), CaptureTheWool.class, rightVillagerLoc);

        Location lobbySpawnLocation = new Location(Bukkit.getWorld("Capture_the_Wool"), 500, 102.5, 500);

        gamesCore.getLobbyManager().createLobby(new CaptureTheWool(), new CaptureTheWoolMap(WorldConstants.WORLD), lobbySpawnLocation);

        registerEvents();
        
        Location mazeBottomLeft = new Location(world, 425.5, 3, 73.5);
        Location mazeTopRight = new Location(world, 410.5, 3, 58.5);

        Region mazeRegion = new Region(mazeTopRight, mazeBottomLeft);

        System.out.println("generate maze time");

        Maze maze = new HuntAndKillMazeGenerationStrategy().generate(mazeRegion);
        MazeRenderingStrategy mazeRenderer = new SimpleMazeRenderingStrategy();
        mazeRenderer.render(maze, null);

        instance = this;
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new AssassinsBlade(), this);
        getServer().getPluginManager().registerEvents(new DisableSpawners(), this);
        getServer().getPluginManager().registerEvents(new PowerfulItemEvents(), this);
        getServer().getPluginManager().registerEvents(new KeyListener(), this);
    }



    @Override
    public void onDisable() {
        gamesCore.onDisable();
        InteractableVillager.destroyAll();
    }
}
