package me.ollie.capturethewool.dungeon.puzzles.maze.generator;

import me.ollie.capturethewool.core.util.region.Region;
import me.ollie.capturethewool.dungeon.puzzles.maze.Maze;
import me.ollie.capturethewool.dungeon.puzzles.maze.MazeGrid;

import java.util.ArrayList;
import java.util.List;

// alot of algorithms require a bunch of walls, so this does that :)
public class WallsMazeGenerationStrategy implements MazeGenerationStrategy {

    private final boolean includeWalls;

    public WallsMazeGenerationStrategy() {
        this(true);
    }

    public WallsMazeGenerationStrategy(boolean includeWalls) {
        this.includeWalls = includeWalls;
    }

    @Override
    public Maze generate(Region region) {

        List<List<MazeGrid.Cell>> cells = new ArrayList<>();

        // build all cells
        for (int x = 0; x < region.getXSize(); x++) {
            List<MazeGrid.Cell> row = new ArrayList<>();
            for (int z = 0; z < region.getZSize(); z++) {
                MazeGrid.Cell cell = new MazeGrid.Cell(x, z);

                if (x == 0 || z == 0 || x == region.getXSize() - 1 || z == region.getZSize() - 1)
                    cell.setBorder(true);

                if (includeWalls && (x % 2 == 0 || z % 2 == 0))
                    cell.setWall(true);


                row.add(cell);
            }

            cells.add(row);
        }
        return new Maze(region, cells);
    }
}
