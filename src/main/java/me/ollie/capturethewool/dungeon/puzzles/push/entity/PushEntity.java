package me.ollie.capturethewool.dungeon.puzzles.push.entity;

import lombok.Getter;
import lombok.Setter;
import me.ollie.capturethewool.dungeon.puzzles.push.lock.PushLock;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.Optional;

public abstract class PushEntity {

    protected final JavaPlugin plugin;

    @Getter
    protected final PushLock lock;

    @Getter
    public enum Direction {
        N(BlockFace.NORTH),
        E(BlockFace.EAST),
        S(BlockFace.SOUTH),
        W(BlockFace.WEST);

        BlockFace face;

        Direction(BlockFace face) {
            this.face = face;
        }

        public Vector getVector() {
            return face.getOppositeFace().getDirection();
        }

        public static Optional<Direction> from(BlockFace face) {
            return switch (face) {
                case NORTH -> Optional.of(N);
                case EAST -> Optional.of(E);
                case SOUTH -> Optional.of(S);
                case WEST -> Optional.of(W);
                default -> Optional.empty();
            };
        }
    }

    @Getter
    protected Location currLocation;

    public PushEntity(JavaPlugin plugin, PushLock lock) {
        this.plugin = plugin;
        this.lock = lock;
    }

    protected Optional<Location> raytrace(Direction direction) {
        RayTraceResult result = currLocation.getWorld().rayTraceBlocks(currLocation.clone().subtract(0, 0.5, 0), direction.getVector(), 32, FluidCollisionMode.NEVER);
        return Optional.ofNullable(result).map(RayTraceResult::getHitBlock).map(Block::getLocation).map(l -> l.subtract(direction.getVector().clone())).map(Location::toCenterLocation);
    }

    public void setLocation(Location location) {
        this.currLocation = location.toCenterLocation();
    }

    public abstract void spawn();

    public abstract void destroy();

    public abstract Location push(Direction direction);
}
