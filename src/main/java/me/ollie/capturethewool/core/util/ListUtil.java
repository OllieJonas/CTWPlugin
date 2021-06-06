package me.ollie.capturethewool.core.util;

import me.ollie.capturethewool.core.util.collections.WeightedRandomSet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ListUtil {

    private static final Random RANDOM = new Random();

    public static <T> T random(Collection<T> collection) {
        if (collection instanceof WeightedRandomSet<T> randomSet) {
            return randomSet.getRandom();
        } else {
            List<T> list = new ArrayList<>(collection);
            return list.get(RANDOM.nextInt(list.size()));
        }
    }
}
