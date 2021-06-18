package me.ollie.capturethewool.core.util;

import lombok.experimental.UtilityClass;

import java.util.function.Supplier;

@UtilityClass
public class RunWithException {

    public <T> T supplier(Class<? extends Throwable> throwable, Supplier<T> supplier, Runnable onFail) {
        try {
            return supplier.get();
        } catch (Exception e) {
            if (e.getClass().isAssignableFrom(throwable)) {
                onFail.run();
            } else {
                e.printStackTrace();
            }
            return null;
        }
    }

    public void sneakyRunnable(Class<? extends Throwable> throwable, Runnable consumer, Runnable onFail) {
        try {
            consumer.run();
        } catch (Exception e) {
            if (e.getClass().isAssignableFrom(throwable)) {
                onFail.run();
            } else {
                e.printStackTrace();
            }
        }
    }
}
