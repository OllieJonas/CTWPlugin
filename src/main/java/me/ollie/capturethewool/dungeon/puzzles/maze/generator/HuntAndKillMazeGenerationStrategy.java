package me.ollie.capturethewool.dungeon.puzzles.maze.generator;

import me.ollie.capturethewool.core.util.region.Region;
import me.ollie.capturethewool.dungeon.puzzles.maze.Maze;
import me.ollie.capturethewool.dungeon.puzzles.maze.MazeGrid;
import org.jooq.lambda.tuple.Tuple2;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class HuntAndKillMazeGenerationStrategy implements MazeGenerationStrategy {

    @Override
    public Maze generate(Region region) {

        MazeGrid grid = new WallsMazeGenerationStrategy().generate(region).getGrid();
        List<List<GenerationCell>> cells = grid.getCells().stream()
                .map(l -> l.stream()
                        .map(GenerationCell::new)
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());

        List<List<GenerationCell>> valid = cells.stream().map(l -> l.stream().filter(c -> c.getCell().canWalkOn()).collect(Collectors.toList())).filter(l -> !l.isEmpty()).collect(Collectors.toList());

        GenerationCell curr = GenerationCell.nextRandom(valid);

        int count = 0;

        while (count++ < 250 && !valid.parallelStream().flatMap(Collection::parallelStream).allMatch(GenerationCell::isVisited)) {
            System.out.println(grid.getStringRepresentation() + "\n\n");
            curr.setVisited(true);
            Tuple2<GenerationCell, MazeGrid.Direction> nextTuple = curr.getUnvisitedNeighbour(cells);
            GenerationCell next = nextTuple.v1();

            if (next != null) {
                grid.carve(curr.getCell(), nextTuple.v2());
                curr = next;

            } else {
                Optional<GenerationCell> optional = valid.parallelStream().flatMap(Collection::parallelStream).filter(c -> c.isVisited() && c.getUnvisitedNeighbours(cells).size() > 0).findAny();
                curr = optional.orElseGet(() -> GenerationCell.nextRandom(valid));
            }
        }

        System.out.println("count: " + count);
        System.out.println("grid: \n" + grid.getStringRepresentation());
        return new Maze(region, grid);
    }
}
