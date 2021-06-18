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

        if (collection == null || collection.isEmpty()) return Optional.empty();

        Collection<T> copy = new HashSet<>(collection);
        while (!copy.isEmpty()) {
            Optional<T> optional = randomOpt(copy);

            if (optional.isPresent()) {
                T target = optional.get();
                if (predicate.test(target)) {
                    return optional;
                } else {
                    copy.remove(target);
                }
            }
        }

        return Optional.empty();
    }

    public static <T> T random(Collection<T> collection) {
        return randomOpt(collection).orElse(null);
    }

    public static <T> Optional<T> randomOpt(Collection<T> collection) {
        if (collection == null || collection.isEmpty()) return Optional.empty();

        if (collection instanceof WeightedRandomSet<T> randomSet) {
            return Optional.of(randomSet.getRandom());
        } else {
            List<T> list = new ArrayList<>(collection);
            return Optional.of(list.get(RANDOM.nextInt(list.size())));
        }
    }
}
