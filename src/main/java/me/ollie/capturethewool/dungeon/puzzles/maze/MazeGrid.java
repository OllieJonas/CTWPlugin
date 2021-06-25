package me.ollie.capturethewool.dungeon.puzzles.maze;

import lombok.Getter;
import lombok.Setter;
import me.ollie.capturethewool.core.util.region.Region;
import org.bukkit.Location;
import org.jooq.lambda.tuple.Tuple2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class MazeGrid {

    @Getter
    public static class Cell {

        private final int x;

        private final int z;

        @Setter
        private boolean isWall;

        @Setter
        private boolean isBorder;

        @Setter
        private boolean isStart;

        @Setter
        private boolean isEnd;

        public Cell(Cell cell) {
            this(cell.getX(), cell.getZ(), cell.isWall(), cell.isBorder(), cell.isStart(), cell.isEnd());
        }

        public Cell(int x, int z) {
            this(x, z, false, false, false, false);
        }

        public Cell(Cell cell, int x, int z) {
            this(x, z, cell.isWall(), cell.isBorder(), cell.isStart(), cell.isEnd());
        }

        public Cell(int x, int z, boolean isWall, boolean isBorder, boolean isStart, boolean isEnd) {
            this.x = x;
            this.z = z;
            this.isWall = isWall;
            this.isBorder = isBorder;
            this.isStart = isStart;
            this.isEnd = isEnd;
        }

        public Location translate(Region region) {
            return new Location(region.getWorld(), region.getMinX() + x, region.getMinY(), region.getMinZ() + z);
        }

        public boolean canWalkOn() {
            return !(isWall || isBorder);
        }
    }

    public enum Direction {
        N,
        E,
        S,
        W;

        public Tuple2<Integer, Integer> translation() {
            return switch (this) {
                case N -> new Tuple2<>(1, 0);
                case E -> new Tuple2<>(0, 1);
                case S -> new Tuple2<>(-1, 0);
                case W -> new Tuple2<>(0, -1);
            };
        }

        public static Set<Direction> VALUES = Set.of(Direction.values());
    }

    public void carve(Cell cell, Direction direction) {
        Tuple2<Integer, Integer> newPosition = direction.translation().map((x, z) -> new Tuple2<>(cell.getX() + x, cell.getZ() + z));

        if (newPosition.v1() >= width() || newPosition.v2() >= height()) return;

        setWall(newPosition.v1(), newPosition.v2(), false);
    }

    @Setter
    private List<List<Cell>> cells;

    public MazeGrid() {
        this(new ArrayList<>());
    }

    public MazeGrid(List<List<Cell>> cells) {
        this.cells = cells;
    }

    public void initEmpty() {
    }

    public MazeGrid horizontalWall(int z) {
        return horizontalWall(z, -1);
    }

    public MazeGrid horizontalWall(int z, int carve) {
        List<Cell> horizontal = cells.get(z);
        for (int i = 0; i < horizontal.size(); i++) {

            if (i != carve) {
                Cell cell = horizontal.get(i);
                cell.setWall(true);
            }
        }
        return this;
    }

    public MazeGrid verticalWall(int x) {
        return verticalWall(x, -1);
    }

    public void setWall(int x, int z, boolean wall) {
        List<Cell> row = cells.get(x);
        if (row != null) {
            Cell cell = row.get(z);
            if (cell != null)
                cell.setWall(wall);
        }
    }

    public MazeGrid verticalWall(int x, int carve) {
        int size = cells.get(0).size();
        for (int i = 0; i < size; i++) {

            if (i != carve) {
                List<Cell> row = cells.get(i);
                row.get(x).setWall(true);
            }
        }
        return this;
    }

    public Cell get(int x, int z) {
        return cells.get(x).get(z);
    }

    public int height() {
        return cells.size();
    }

    public int width() {
        return cells.get(0).size();
    }

    public String getStringRepresentation() {
        return cells.stream().map(l -> l.stream().map(this::getCharRepresentation).collect(Collectors.joining(" "))).collect(Collectors.joining("\n"));
    }

    private String getCharRepresentation(Cell cell) {
        if (cell.isWall() || cell.isBorder()) return "W";
        else if (cell.isStart()) return "S";
        else if (cell.isEnd()) return "E";
        else return "-";
    }
}
