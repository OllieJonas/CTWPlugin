package me.ollie.capturethewool.core.util.concurrent;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class NamedThreadFactory implements ThreadFactory {

    private final AtomicInteger threadNumber = new AtomicInteger(1);

    private final String nameFormat;

    public NamedThreadFactory(Class<?> clazz) {
        this(clazz.getSimpleName());
    }

    public NamedThreadFactory(String nameFormat) {
        this.nameFormat = nameFormat;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r, String.format(nameFormat, threadNumber.getAndIncrement()));
        thread.setDaemon(true);
        return thread;
    }
}