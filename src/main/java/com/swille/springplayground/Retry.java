package com.swille.springplayground;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Supplier;

import static java.util.concurrent.TimeUnit.*;

@Component
public class Retry {

    ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();

    public String[] doRetry() throws InterruptedException, ExecutionException, TimeoutException {
        final String[] result = {""};
        final List<Integer> maxCount = new java.util.ArrayList<>(List.of(1, 2, 3, 4, 5));
        CompletableFuture<String[]> future = new CompletableFuture<>();
        CompletableFuture<String[]> future2 = new CompletableFuture<>();


        String[] futureTimeout = new String[]{"timedout"};
        int count = 5;
        while (futureTimeout[0].equals("timedout")) {
            if (--count <= 0) {
                future2 = new CompletableFuture<>();
                future2.complete(new String[]{"success"});
            } else {
                future2.completeOnTimeout(futureTimeout, 100, MILLISECONDS);
            }
            futureTimeout = future2.get();
            System.out.println((char) 27 + "[97;43m" + futureTimeout[0] + (char) 27 + "[0m");
        }

        return futureTimeout;
        /*
        ses.scheduleAtFixedRate(
                () -> {
                    if (maxCount.size() == 0) {

                        System.out.println((char) 27 + "[97;43m" + "shutdown" + (char) 27 + "[0m");
                        result[0] = "success";
                        future.complete(result);
                        maxCount.add(-1);
                    }

                    maxCount.remove(0);

                }, 0, 100, MILLISECONDS);

        try {
            return future.get();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            ses.shutdown();
        }
        return result;

         */
    }

    public String[] retry2() throws ExecutionException, InterruptedException {
        int count = 5;
        String[] results = null;

        while (--count >= 0 || results == null) {
            CompletableFuture<String[]> completableFuture = new CompletableFuture();

            if (count <= 4) {
                completableFuture.complete(new String[]{"success"});

                results = completableFuture.get();
                break;

            }
            completableFuture.completeOnTimeout(null, 100, MILLISECONDS);
        }

        return results;
    }
}
