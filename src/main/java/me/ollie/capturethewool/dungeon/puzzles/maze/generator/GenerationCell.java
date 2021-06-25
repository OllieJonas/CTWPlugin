package me.ollie.capturethewool.dungeon.puzzles.maze.generator;

import lombok.Getter;
import lombok.Setter;
import me.ollie.capturethewool.core.util.CollectionUtil;
import me.ollie.capturethewool.dungeon.puzzles.maze.MazeGrid;
import org.jooq.lambda.tuple.Tuple2;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
public class GenerationCell {

    private final MazeGrid.Cell cell;

    @Setter
    private boolean visited;

    public GenerationCell(MazeGrid.Cell cell) {
        this(cell, false);
    }

    public GenerationCell(MazeGrid.Cell cell, boolean visited) {
        this.cell = cell;
        this.visited = visited;
    }


    public static GenerationCell nextRandom(List<List<GenerationCell>> cells) {
        List<GenerationCell> row = CollectionUtil.random(cells);
        return CollectionUtil.random(row);
    }

    public Tuple2<GenerationCell, MazeGrid.Direction> getUnvisitedNeighbour(List<List<GenerationCell>> cells) {
        Set<MazeGrid.Direction> visited = new HashSet<>();

        GenerationCell curr = null;
        MazeGrid.Direction currDir = null;

        while (!visited.containsAll(MazeGrid.Direction.VALUES)) {
            MazeGrid.Direction direction = CollectionUtil.random(MazeGrid.Direction.VALUES);
            Tuple2<Integer, Integer> translation = direction.translation();
            GenerationCell next = safeGet(cells, getCell().getX() + translation.v1(), getCell().getZ() + translation.v2());

            if (next != null && !next.isVisited()) {
                curr = next;
                currDir = direction;
                break;
            } else {
                visited.add(direction);
            }
        }

        return new Tuple2<>(curr, currDir);
    }


    private <T> T safeGet(List<List<T>> list, int x, int y) {

        if (x <= 0 || y <= 0) return null;

        if (list.size() <= x) return null;

        List<T> row = list.get(x);

        if (row.size() <= y) return null;

        return row.get(y);
    }

    public GenerationCell getNeighbour(List<List<GenerationCell>> cells, MazeGrid.Direction direction) {
        Tuple2<Integer, Integer> translation = direction.translation();
        return safeGet(cells, getCell().getX() + translation.v1(), getCell().getZ() + translation.v2());
    }

    public Collection<Tuple2<GenerationCell, MazeGrid.Direction>> getUnvisitedNeighbours(List<List<GenerationCell>> cells) {
        Collection<Tuple2<GenerationCell, MazeGrid.Direction>> collection = new HashSet<>();

        for (MazeGrid.Direction direction : MazeGrid.Direction.VALUES) {
            GenerationCell cell = getNeighbour(cells, direction);
            if (cell != null) collection.add(new Tuple2<>(cell, direction));
        }

        return collection;
    }
}
