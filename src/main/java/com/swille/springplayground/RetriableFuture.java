package com.swille.springplayground;

import java.util.concurrent.*;
import java.util.function.Supplier;

public class RetriableFuture<T> {
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private final Supplier<T> supplier;
    private final long interval;
    private final TimeUnit unit;

    public RetriableFuture(Supplier<T> supplier, long interval, TimeUnit unit) {
        this.supplier = supplier;
        this.interval = interval;
        this.unit = unit;
    }

    public T retryUntilNotNull() {
        CompletableFuture<T> future = new CompletableFuture<>();
        executor.scheduleAtFixedRate(() -> {
            T result = supplier.get();
            if (result != null) {
                future.complete(result);
            }
        }, 0, interval, unit);

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        } finally {
            executor.shutdownNow();
        }
    }
}
