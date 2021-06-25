package me.ollie.capturethewool.dungeon.puzzles.maze;

import lombok.Getter;
import me.ollie.capturethewool.core.util.region.Region;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Maze {

    private final Region region;

    private final MazeGrid grid;

    public Maze(Region region) {
        this(region, new ArrayList<>());
    }

    public Maze(Region region, List<List<MazeGrid.Cell>> cells) {
        this(region, new MazeGrid(cells));
    }

    public Maze(Region region, MazeGrid grid) {
        this.region = region;
        this.grid = grid;
    }
}
