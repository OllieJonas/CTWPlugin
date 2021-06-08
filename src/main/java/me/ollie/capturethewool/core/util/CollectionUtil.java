package me.ollie.capturethewool.core.util;

import me.ollie.capturethewool.core.util.collections.WeightedRandomSet;

import java.util.*;
import java.util.function.Predicate;

public class CollectionUtil {

    private static final Random RANDOM = new Random();

    public static <T> Optional<T> random(Collection<T> collection, List<Predicate<T>> predicates) {
        return random(collection, predicates.stream().reduce(Predicate::and).orElse(t -> true));
    }

    public static <T> Optional<T> random(Collection<T> collection, Predicate<T> predicate) {
        Collection<T> copy = new HashSet<>(collection);
        while (!copy.isEmpty()) {
            T target = random(copy);
            if (predicate.test(target)) {
                return Optional.of(target);
            } else {
                copy.remove(target);
            }
        }

        return Optional.empty();
    }

    public static <T> T random(Collection<T> collection) {
        if (collection instanceof WeightedRandomSet<T> randomSet) {
            return randomSet.getRandom();
        } else {
            List<T> list = new ArrayList<>(collection);
            return list.get(RANDOM.nextInt(list.size()));
        }
    }
}
