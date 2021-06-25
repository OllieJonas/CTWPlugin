package me.ollie.capturethewool.dungeon.puzzles.maze.render;

import me.ollie.capturethewool.dungeon.puzzles.maze.Maze;
import me.ollie.capturethewool.dungeon.puzzles.maze.MazeSolution;

public interface MazeRenderingStrategy {

    void render(Maze maze, MazeSolution solution);
}
