package com.swille.springplayground;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

import static java.util.concurrent.TimeUnit.*;

@Component
public class Retry {

    ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();

    public Future doRetry() throws InterruptedException, ExecutionException, TimeoutException {
        final String[] result = {""};
        final List<Integer> maxCount = new java.util.ArrayList<>(List.of(1, 2, 3, 4, 5));

        var foo = ses.scheduleAtFixedRate(
                () -> {
                    ses.submit(() -> {
                        if (maxCount.size() == 0) {
                            System.out.println((char) 27 + "[97;43m" + "shutdown" + (char) 27 + "[0m");
                            result[0] = "my do";
                            maxCount.add(-1);
                        }

                        maxCount.remove(0);
                    });

                }, 0, 100, MILLISECONDS);

        var res = foo.get();
        return null;
    }

}
