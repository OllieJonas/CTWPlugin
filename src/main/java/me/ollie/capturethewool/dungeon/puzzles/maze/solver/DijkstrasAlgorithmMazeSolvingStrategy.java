package me.ollie.capturethewool.dungeon.puzzles.maze.solver;

import lombok.Getter;
import lombok.Setter;
import me.ollie.capturethewool.dungeon.puzzles.maze.Maze;
import me.ollie.capturethewool.dungeon.puzzles.maze.MazeGrid;
import me.ollie.capturethewool.dungeon.puzzles.maze.MazeSolution;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DijkstrasAlgorithmMazeSolvingStrategy implements MazeSolvingStrategy {

    @Getter
    @Setter
    private static class Node {

        private MazeGrid.Cell cell;

        private List<Node> shortestPath;

        private int distance;

        private Map<Node, Integer> adjacentNodes;

        public Node(MazeGrid.Cell cell) {
            this.cell = cell;
            this.shortestPath = new LinkedList<>();
            this.distance = Integer.MAX_VALUE;
            this.adjacentNodes = new HashMap<>();
        }
    }


    @Override
    public MazeSolution solve(Maze maze) {
        return null;
    }
}
