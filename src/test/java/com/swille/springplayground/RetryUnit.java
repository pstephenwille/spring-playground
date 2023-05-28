package com.swille.springplayground;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class RetryUnit {


    @Autowired
    Retry retry;

    @Test
    void doRetry_works() throws Exception {
        var actual = retry.doRetry();
        Thread.sleep(5000);
        assertThat(actual).isEqualTo("my do");
    }

}