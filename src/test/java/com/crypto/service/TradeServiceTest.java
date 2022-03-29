package com.crypto.service;

import com.crypto.service.constant.CryptoConstant;
import com.crypto.service.entity.TradeResponseEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TradeServiceTest {
    @Autowired
    private TradeService tradeService;
    
    @Test
    @DisplayName("Test for get-trades request")
    public void getTrades_TradeBTC_USDT_ResponseCodeIs0() {
        String instrumentName = CryptoConstant.INSTRUMENT_BTC_USDT;

        TradeResponseEntity responseEntity = tradeService.getTrades(instrumentName);
        Assertions.assertEquals(responseEntity.getCode(), CryptoConstant.SUCCESS_CODE);
    }
}
