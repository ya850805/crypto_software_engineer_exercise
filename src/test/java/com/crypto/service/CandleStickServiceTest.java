package com.crypto.service;

import com.crypto.service.constant.CryptoConstant;
import com.crypto.service.entity.CandleStickResponseEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CandleStickServiceTest {
    @Autowired
    private CandleStickService candleStickService;

    @Test
    @DisplayName("Test for get-candlestick request")
    public void getCandleStick_BTC_USDTin1Minutes_ResponseCodeIs0() {
        String instrumentName = CryptoConstant.INSTRUMENT_BTC_USDT;
        String period = CryptoConstant.PERIOD_ONE_MINUTE;

        CandleStickResponseEntity responseEntity = candleStickService.getCandleStick(instrumentName, period);
        Assertions.assertEquals(responseEntity.getCode(), CryptoConstant.SUCCESS_CODE);
    }
}
