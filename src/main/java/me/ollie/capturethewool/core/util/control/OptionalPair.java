package me.ollie.capturethewool.core.util.control;

import java.util.Optional;
import java.util.function.Consumer;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class OptionalPair<T1, T2> {

    private final Optional<T1> val1;

    private final Optional<T2> val2;

    public static <T1, T2> OptionalPair<T1, T2> empty() {
        return new OptionalPair<>(Optional.empty(), Optional.empty());
    }

    public static <T1, T2> OptionalPair<T1, T2> of(T1 val1, T2 val2) {
        return new OptionalPair<>(Optional.of(val1), Optional.of(val2));
    }

    public static <T1, T2> OptionalPair<T1, T2> left(T1 val) {
        return new OptionalPair<>(Optional.of(val), Optional.empty());
    }

    public static <T1, T2> OptionalPair<T1, T2> right(T2 val) {
        return new OptionalPair<>(Optional.empty(), Optional.of(val));
    }

    private OptionalPair(Optional<T1> val1, Optional<T2> val2) {
        this.val1 = val1;
        this.val2 = val2;
    }

    public void apply(Consumer<? super T1> val1Cons, Consumer<? super T2> val2Cons) {
        val1.ifPresent(val1Cons);
        val2.ifPresent(val2Cons);
    }
}
