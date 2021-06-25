package me.ollie.capturethewool.dungeon.puzzles.maze.generator;

import me.ollie.capturethewool.core.util.region.Region;
import me.ollie.capturethewool.dungeon.puzzles.maze.Maze;

public interface MazeGenerationStrategy {

    Maze generate(Region region);
}
