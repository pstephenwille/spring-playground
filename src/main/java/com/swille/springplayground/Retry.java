package com.swille.springplayground;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.*;

@Component
public class Retry {

    ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();

    public String doRetry(){
        ses.scheduleAtFixedRate(
                new Runnable() {
                int maxCount = 3;
            @Override
           public void run() {
                if(--maxCount <= 0) {
                    System.out.println((char)27 + "[97;43m"+ "shutdown" +(char)27+"[0m");
                    ses.shutdown();
                }


                   System.out.println((char)27 + "[97;43m"+ "do-work" +(char)27+"[0m");
           }
        }, 0, 100, MILLISECONDS);


        return "my do";
    }

}
