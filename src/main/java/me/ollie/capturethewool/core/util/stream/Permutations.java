package me.ollie.capturethewool.core.util.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

// copied from: https://stackoverflow.com/questions/43756348/permutation-of-words-using-java-stream
public class Permutations {

    public static <T> Stream<List<T>> of(List<T> c, int r){
        if (r==1){
            return c.stream()
                    .map(Arrays::asList);
        } else
        if (r==2){
            return c.stream()
                    .flatMap(
                            e1 -> c.stream()  // e1: refers to an element of c
                                    .filter(e2 -> !e1.equals(e2)) // e2: refers to an element of c
                                    .map(e2 -> Arrays.asList(e1, e2))
                    );
        } else {
            return of(c, r-1)
                    .flatMap(
                            l -> c.stream()
                                    .filter( e -> !l.contains(e))
                                    .map(e -> {
                                        List<T> out = new ArrayList<>(l);
                                        out.add(e);
                                        return out;}
                                    )
                    );
        }
    }


}
