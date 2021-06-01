package me.ollie.capturethewool.core.util.function;

@FunctionalInterface
public interface TriConsumer<T, V, N> {
    void accept(T t, V v, N n);
}
