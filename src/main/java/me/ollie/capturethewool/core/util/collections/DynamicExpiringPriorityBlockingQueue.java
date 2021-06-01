package me.ollie.capturethewool.core.util.collections;

import me.ollie.capturethewool.core.util.concurrent.NamedThreadFactory;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class DynamicExpiringPriorityBlockingQueue<E> {

    public static <E> DynamicExpiringPriorityBlockingQueue<E> create(Comparator<? super E> comparator) {
        return new DynamicExpiringPriorityBlockingQueue<>(comparator);
    }

    private static class ExpiringElement<E> {
        E element;
        long duration;
        TimeUnit timeUnit;
        AtomicBoolean isExpired;

        private ExpiringElement(E element, long duration, TimeUnit timeUnit) {
            this.element = element;
            this.duration = duration;
            this.timeUnit = timeUnit;
            this.isExpired = new AtomicBoolean(false);
        }

        public static <E> ExpiringElement<E> of(E element, long duration, TimeUnit unit) {
            return new ExpiringElement<>(element, duration, unit);
        }

        public void setExpired() {
            isExpired.set(true);
        }

        public boolean isExpired() {
            return isExpired.get();
        }

        @Override
        public String toString() {
            return "" +
                    "e=" + element;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ExpiringElement<?> that = (ExpiringElement<?>) o;
            return duration == that.duration && element.equals(that.element) && timeUnit == that.timeUnit;
        }

        @Override
        public int hashCode() {
            return Objects.hash(element, duration, timeUnit);
        }
    }

    private static final ThreadFactory NAMED_THREAD_FACTORY = new NamedThreadFactory(DynamicExpiringPriorityBlockingQueue.class);

    private final ScheduledExecutorService expirationService;

    private final PriorityBlockingQueue<ExpiringElement<E>> blockingQueue;

    private DynamicExpiringPriorityBlockingQueue(Comparator<? super E> comparator) {
        this.blockingQueue = new PriorityBlockingQueue<>(10, (e1, e2) -> comparator.compare(e1.element, e2.element));
        this.expirationService = Executors.newSingleThreadScheduledExecutor(NAMED_THREAD_FACTORY);
    }

    public void update(E element) {
        Optional<ExpiringElement<E>> expiringElementOptional = blockingQueue.stream().filter(e -> e.element.equals(element)).findFirst();
        if (expiringElementOptional.isPresent()) {
            ExpiringElement<E> old = expiringElementOptional.get();
            blockingQueue.remove(old);
            blockingQueue.add(ExpiringElement.of(element, old.duration, old.timeUnit));
        }
    }

    public void add(E element, long duration, TimeUnit timeUnit) {
        ExpiringElement<E> expiringElement = ExpiringElement.of(element, duration, timeUnit);
        blockingQueue.add(expiringElement);
        schedule(expiringElement);
    }

    public void print() {
        List<ExpiringElement<E>> lst = new ArrayList<>();
        blockingQueue.drainTo(lst);
        System.out.println(lst.stream().map(Object::toString).collect(Collectors.joining(", ")));
        blockingQueue.addAll(lst);
    }

    public E peek() {
        ExpiringElement<E> elem = firstNonExpiring();
        return (elem != null ? elem.element : null);
    }

    public E poll() {
        firstNonExpiring();
        ExpiringElement<E> elem;
        return ((elem = blockingQueue.poll()) != null ? elem.element : null);
    }

    private ExpiringElement<E> firstNonExpiring() {
        if (blockingQueue.isEmpty())
            return null;

        ExpiringElement<E> curr = blockingQueue.peek();

        while (curr != null && curr.isExpired()) {
            blockingQueue.poll();
            curr = blockingQueue.peek();
        }

        return curr;
    }

    private void schedule(ExpiringElement<E> element) {
        expirationService.schedule(() -> {

            if (element.equals(blockingQueue.peek()))
                blockingQueue.poll();

            print();
            element.setExpired();
        }, element.duration, element.timeUnit);
    }
}