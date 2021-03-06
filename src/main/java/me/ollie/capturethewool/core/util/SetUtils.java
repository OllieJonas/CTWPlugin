package me.ollie.capturethewool.core.util;

import lombok.experimental.UtilityClass;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@UtilityClass
public class SetUtils {
    /**
     * Calculates the difference between two sets
     *
     * @param original The original set
     * @param toRemove The items to remove
     * @return The difference between two sets
     */
    public <E> Set<E> difference(Set<E> original, Set<E> toRemove) {
        if (original == null || original.isEmpty() || toRemove == null || toRemove.isEmpty())
            return original;

        original.removeAll(toRemove);
        return original;
    }

    public <E> Set<E> union(Set<E> set1, Set<E> set2) {
        return Stream.concat(set1.stream(), set2.stream()).collect(Collectors.toUnmodifiableSet());
    }
}
