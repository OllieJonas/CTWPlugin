package me.ollie.capturethewool.core.util.control;

public class Pair<T1, T2> {

    private final T1 val1;

    private final T2 val2;

    public static <T1, T2> Pair<T1, T2> of(T1 val1, T2 val2) {
        return new Pair<>(val1, val2);
    }

    private Pair(T1 val1, T2 val2) {
        this.val1 = val1;
        this.val2 = val2;
    }

    public T1 getVal1() {
        return val1;
    }

    public T2 getVal2() {
        return val2;
    }
}
