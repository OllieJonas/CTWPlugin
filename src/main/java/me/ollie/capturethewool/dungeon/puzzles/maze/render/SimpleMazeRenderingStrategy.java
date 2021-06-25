package me.ollie.capturethewool.dungeon.puzzles.maze.render;

import me.ollie.capturethewool.core.util.region.Region;
import me.ollie.capturethewool.dungeon.puzzles.maze.Maze;
import me.ollie.capturethewool.dungeon.puzzles.maze.MazeGrid;
import me.ollie.capturethewool.dungeon.puzzles.maze.MazeSolution;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import java.util.List;

public class SimpleMazeRenderingStrategy implements MazeRenderingStrategy {

    @Override
    public void render(Maze maze, MazeSolution solution) {
        Region region = maze.getRegion();
        World world = region.getWorld();
        world.getBlockAt(maze.getRegion().getFirst()).setType(Material.REDSTONE_BLOCK);
        world.getBlockAt(maze.getRegion().getSecond()).setType(Material.EMERALD_BLOCK);
        world.getBlockAt(maze.getGrid().get(0, 0).translate(region)).setType(Material.DIAMOND_BLOCK);
        for (List<MazeGrid.Cell> row : maze.getGrid().getCells()) {
            for (MazeGrid.Cell cell : row) {
                Location loc = cell.translate(region);
                world.getBlockAt(loc).setType(getMaterial(cell));
            }
        }
    }

    private Material getMaterial(MazeGrid.Cell cell) {
        if (cell.isBorder()) return Material.STONE_BRICKS;
        if (cell.isWall()) return Material.COBBLESTONE;
        else return Material.COARSE_DIRT;
    }
}
