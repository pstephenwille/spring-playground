package com.swille.springplayground;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class RetryUnit {


    @Autowired
    Retry retry;

    @Test
    void doRetry_works() throws Exception {
        var actual = retry.doRetry();
        assertThat(actual[0]).isEqualTo("success");
    }
    @Test
    void doRetry2_works() throws Exception {
        var actual = retry.retry2();
        assertThat(actual[0]).isEqualTo("success");
    }

}
