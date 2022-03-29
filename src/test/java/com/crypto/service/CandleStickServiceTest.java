package com.crypto.service;

import com.crypto.service.entity.CandleStickResponseEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CandleStickServiceTest {
    @Autowired
    private CandleStickService candleStickService;

    @Test
    public void getCandleStick__() {
        CandleStickResponseEntity candleStick = candleStickService.getCandleStick();

    }
}
