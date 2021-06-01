package me.ollie.capturethewool.core.util.control;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * A very Poor Man's implementation of Either from Haskell.
 * <p>
 * Please note that is a very bastardized version of Either, but gets the job done I think? idk anymore lmao
 *
 * @param <L> left param type
 * @param <R> right param type
 */
public record Either<L, R>(Optional<L> left, Optional<R> right) {

    public static <L, R> Either<L, R> left(L left) {
        return new Either<>(Optional.of(left), Optional.empty());
    }

    public static <L, R> Either<L, R> right(R right) {
        return new Either<>(Optional.empty(), Optional.of(right));
    }

    public void apply(Consumer<? super L> lFunc, Consumer<? super R> rFunc) {
        left.ifPresent(lFunc);
        right.ifPresent(rFunc);
    }
}
