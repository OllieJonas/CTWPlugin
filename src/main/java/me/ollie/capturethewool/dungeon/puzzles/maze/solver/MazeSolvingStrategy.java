package me.ollie.capturethewool.dungeon.puzzles.maze.solver;

import me.ollie.capturethewool.dungeon.puzzles.maze.Maze;
import me.ollie.capturethewool.dungeon.puzzles.maze.MazeSolution;

public interface MazeSolvingStrategy {

    MazeSolution solve(Maze maze);
}
