package me.ollie.capturethewool.dungeon.puzzles.push;

import me.ollie.capturethewool.core.util.BlockFaceUtil;
import me.ollie.capturethewool.core.util.LocationUtil;
import me.ollie.capturethewool.core.util.region.Region;
import me.ollie.capturethewool.dungeon.puzzles.AbstractPuzzle;
import me.ollie.capturethewool.dungeon.puzzles.push.entity.PushEntity;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class PushPuzzle extends AbstractPuzzle {

    private final JavaPlugin plugin;

    private final PushEntity entity;

    private final Location startLocation;

    private final Location endLocation;

    public record Configuration(Material startBlockType, Material endBlockType) {}

    public PushPuzzle(JavaPlugin plugin, Region region, PushEntity entity, Configuration configuration) {
        super(region);

        this.plugin = plugin;
        this.entity = entity;

        this.startLocation = region.getBlocks(configuration.startBlockType()).stream().findFirst().orElseThrow().getLocation().add(0, 1, 0);
        this.endLocation = region.getBlocks(configuration.endBlockType()).stream().findFirst().orElseThrow().getLocation().add(0, 1, 0);

    }

    public void init() {
        entity.setLocation(startLocation);
        entity.spawn();

        RESET_HOLOGRAM.apply(plugin, startLocation.clone().add(0, 2, 0));

        plugin.getServer().getPluginManager().registerEvents(new Listener(), plugin);
    }

    public void push(PushEntity.Direction direction) {
        Location newLocation = entity.push(direction);
        if (LocationUtil.floor(newLocation).equals(endLocation)) {
            onFinish();
        }
    }

    @Override
    public void onFinish() {
        finished = true;
        Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage("congrats bro you did it"));
    }

    @Override
    public void destroy() {
        entity.destroy();
    }

    @Override
    public void reset() {
        entity.destroy();
        entity.setLocation(startLocation);
        entity.spawn();
    }

    private class Listener implements org.bukkit.event.Listener {

        @EventHandler
        public void onMove(PlayerMoveEvent event) {
            if (entity == null) return;

            Location player = event.getPlayer().getLocation().toBlockLocation();
            Location block = entity.getCurrLocation();

            if (!LocationUtil.isAdjacent(player, block)) return;

            PushEntity.Direction.from(BlockFaceUtil.yawToFace(player.getYaw())).ifPresent(PushPuzzle.this::push);
        }
    }
}
