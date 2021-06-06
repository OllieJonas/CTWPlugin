package me.ollie.capturethewool.core.util;

import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.bukkit.block.Block;

import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class BlockUtil {
    public static Map<Block, Double> getInRadius(Location loc, double dR) {
        return getInRadius(loc, dR, 9999);
    }

    public static Map<Block, Double> getInRadius(Location loc, double dR, double maxHeight) {
        Map<Block, Double> blockList = new HashMap<>();
        int iR = (int) dR + 1;

        for (int x=-iR ; x <= iR ; x++)
            for (int z=-iR ; z <= iR ; z++)
                for (int y=-iR ; y <= iR ; y++) {

                    if (Math.abs(y) > maxHeight)
                        continue;

                    Block curBlock = loc.getWorld().getBlockAt((int) (loc.getX() + x), (int) (loc.getY() + y), (int) (loc.getZ() + z));

                    double offset = MathsUtil.offset(loc, curBlock.getLocation().add(0.5, 0.5, 0.5));;

                    if (offset <= dR)
                        blockList.put(curBlock, 1 - (offset/dR));
                }

        return blockList;
    }


    public static Map<Block, Double> getInRadius(Block block, double dR) {
        return getInRadius(block, dR, false);
    }

    public static Map<Block, Double> getInRadius(Block block, double dR, boolean hollow) {
        Map<Block, Double> blockList = new HashMap<>();
        int iR = (int) dR + 1;

        for (int x=-iR ; x <= iR ; x++)
            for (int z=-iR ; z <= iR ; z++)
                for (int y=-iR ; y <= iR ; y++) {
                    Block curBlock = block.getRelative(x, y, z);

                    double offset = MathsUtil.offset(block.getLocation(), curBlock.getLocation());

                    if (offset <= dR && !(hollow && offset < dR - 1)) {
                        blockList.put(curBlock, 1 - (offset / dR));
                    }
                }

        return blockList;
    }
}
