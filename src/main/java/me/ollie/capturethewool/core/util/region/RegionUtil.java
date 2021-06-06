package me.ollie.capturethewool.core.util.region;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.jooq.lambda.tuple.Tuple6;

import java.util.ArrayList;
import java.util.List;

public class RegionUtil {

    public static List<Block> getBlocks(Location first, Location second) {
        List<Block> blocks = new ArrayList<>();

        Tuple6<Integer, Integer, Integer, Integer, Integer, Integer> ctx = Region.getContext(first, second);

        int bottomBlockX = ctx.v1();
        int topBlockX = ctx.v2();

        int topBlockY = ctx.v3();
        int bottomBlockY = ctx.v4();

        int topBlockZ = ctx.v5();
        int bottomBlockZ = ctx.v6();

        for (int x = bottomBlockX; x <= topBlockX; x++) {
            for (int y = bottomBlockY; y <= topBlockY; y++) {
                for (int z = bottomBlockZ; z <= topBlockZ; z++) {

                    Block block = first.getWorld().getBlockAt(x, y, z);
                    blocks.add(block);

                }
            }
        }
        return blocks;
    }
}
