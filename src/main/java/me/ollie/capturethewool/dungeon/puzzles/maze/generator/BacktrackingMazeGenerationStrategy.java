package me.ollie.capturethewool.dungeon.puzzles.maze.generator;

import lombok.Getter;
import lombok.Setter;
import me.ollie.capturethewool.core.util.CollectionUtil;
import me.ollie.capturethewool.core.util.region.Region;
import me.ollie.capturethewool.dungeon.puzzles.maze.Maze;
import me.ollie.capturethewool.dungeon.puzzles.maze.MazeGrid;
import org.bukkit.Material;
import org.jooq.lambda.tuple.Tuple2;

import java.util.*;
import java.util.stream.Collectors;

public class BacktrackingMazeGenerationStrategy implements MazeGenerationStrategy {

    @Getter
    public static class Cell {
        private final MazeGrid.Cell cell;

        @Setter
        private boolean visited;

        public Cell(MazeGrid.Cell cell) {
            this(cell, false);
        }

        public Cell(MazeGrid.Cell cell, boolean visited) {
            this.cell = cell;
            this.visited = visited;
        }
    }

    private int count;

//    @Override
//    public Maze generate(Region region) {
//        MazeGrid grid = new WallsMazeGenerationStrategy().generate(region).getGrid();
//        List<List<Cell>> cells = grid.getCells().stream()
//                .map(l -> l.stream()
//                        .map(Cell::new)
//                        .collect(Collectors.toList()))
//                .collect(Collectors.toList());
//
//        Cell curr = nextRandom(cells);
//
//        while (!cells.parallelStream()
//                .flatMap(Collection::parallelStream)
//                .allMatch(Cell::isVisited)) {
//
//            count++;
//
//            curr.setVisited(true);
//
//            Tuple2<Cell, MazeGrid.Direction> tuple = getUnvisitedNeighbour(cells, curr);
//            Cell next = tuple.v1();
//            if (isValid(next)) {
//                curr = next;
//                grid.carve(next.getCell(), tuple.v2());
//
//
//            } else {
//                curr = nextRandom(cells);
//            }
//        }
//        System.out.println("count: " + count);
//        System.out.println("grid: \n" + grid.getStringRepresentation());
//        return new Maze(region, grid.getCells());
//    }
    @Override
    public Maze generate(Region region) {
        MazeGrid grid = new WallsMazeGenerationStrategy().generate(region).getGrid();
        List<List<Cell>> cells = grid.getCells().stream()
                .map(l -> l.stream()
                        .map(Cell::new)
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());


        List<List<Cell>> valid = cells.stream()
                .map(l -> l.stream()
                        .filter(this::isValid)
                        .collect(Collectors.toList()))
                .filter(l -> !l.isEmpty())
                .collect(Collectors.toList());

        System.out.println(valid.stream().flatMap(Collection::stream).filter(c -> !c.isVisited()).count());

        Cell curr = nextRandom(valid);

        while (!valid.parallelStream()
                .flatMap(Collection::parallelStream)
                .allMatch(Cell::isVisited)) {

            curr.setVisited(true);

            if (count++ % 100 == 0) System.out.println(grid.getStringRepresentation() + "\n\n");

            Tuple2<Cell, MazeGrid.Direction> tuple = getUnvisitedNeighbour(cells, curr);
            Cell next = tuple.v1();
            if (next != null) {
                curr = next;

                grid.carve(next.getCell(), tuple.v2());


            } else {
                curr = nextRandom(valid);
            }
        }
        System.out.println("count: " + count);
        System.out.println("grid: \n" + grid.getStringRepresentation());
        return new Maze(region, grid.getCells());
    }

    private boolean isValid(Cell cell) {
        return cell != null && !(cell.getCell().isWall() || cell.getCell().isBorder());
    }

    private Cell nextRandom(List<List<Cell>> cells) {
        List<Cell> row = CollectionUtil.random(cells);
        return CollectionUtil.random(row);
    }

    private Tuple2<Cell, MazeGrid.Direction> getUnvisitedNeighbour(List<List<Cell>> cells, Cell cell) {
        Set<MazeGrid.Direction> visited = new HashSet<>();

        Cell curr = null;
        MazeGrid.Direction currDir = null;

        while (!visited.containsAll(MazeGrid.Direction.VALUES)) {
            MazeGrid.Direction direction = CollectionUtil.random(MazeGrid.Direction.VALUES);
            Tuple2<Integer, Integer> translation = direction.translation();
            Cell next = safeGet(cells, cell.getCell().getX() + translation.v1(), cell.getCell().getZ() + translation.v2());

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
}
