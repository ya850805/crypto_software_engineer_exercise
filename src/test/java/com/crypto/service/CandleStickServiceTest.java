package com.crypto.service;

import com.crypto.constant.CryptoConstant;
import com.crypto.entity.CandleStickResponseData;
import com.crypto.entity.CandleStickResponseEntity;
import com.crypto.entity.CandleStickResponseResult;
import com.crypto.service.impl.CandleStickServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RunWith(MockitoJUnitRunner.class)
public class CandleStickServiceTest {
    @InjectMocks
    private CandleStickServiceImpl candleStickService;
    @Mock
    private RestTemplate restTemplate;

    @Test
    public void getCandleStick_BTC_USDTin1Minutes_RestTemplateSendRequestOnce() {
        String instrumentName = CryptoConstant.INSTRUMENT_BTC_USDT;
        String period = CryptoConstant.PERIOD_ONE_MINUTE;

        CandleStickResponseEntity entity = new CandleStickResponseEntity();
        ResponseEntity<CandleStickResponseEntity> responseEntity = new ResponseEntity<>(entity, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(Mockito.anyString(), Mockito.any(HttpMethod.class), Mockito.any(HttpEntity.class), Mockito.any(Class.class), Mockito.any(Map.class))).thenReturn(responseEntity);

        candleStickService.getCandleStick(instrumentName, period);

        Mockito.verify(restTemplate, Mockito.times(1)).exchange(Mockito.anyString(), Mockito.any(HttpMethod.class), Mockito.any(HttpEntity.class), Mockito.any(Class.class), Mockito.any(Map.class));
    }

    @Test
    public void getCandleStickByEndEpochSecond_Search3Candlestick_ReturnCorrectData() {
        String instrumentName = CryptoConstant.INSTRUMENT_BTC_USDT;
        String period = CryptoConstant.PERIOD_ONE_MINUTE;
        Long time = 600000L;

        List<CandleStickResponseData> data = new ArrayList<>();
        CandleStickResponseData d1 = new CandleStickResponseData();
        BigDecimal d1V = new BigDecimal(1000L);
        d1.setT(1000L);
        d1.setV(d1V);
        data.add(d1);

        CandleStickResponseData d2 = new CandleStickResponseData();
        BigDecimal d2V = new BigDecimal(2000L);
        d2.setT(time);
        d2.setV(d2V);
        data.add(d2);

        CandleStickResponseData d3 = new CandleStickResponseData();
        BigDecimal d3V = new BigDecimal(3000L);
        d3.setV(d3V);
        d3.setT(3000L);
        data.add(d3);

        CandleStickResponseEntity entity = new CandleStickResponseEntity();
        CandleStickResponseResult result = new CandleStickResponseResult();
        result.setData(data);
        entity.setResult(result);
        ResponseEntity<CandleStickResponseEntity> responseEntity = new ResponseEntity<>(entity, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(Mockito.anyString(), Mockito.any(HttpMethod.class), Mockito.any(HttpEntity.class), Mockito.any(Class.class), Mockito.any(Map.class))).thenReturn(responseEntity);

        CandleStickResponseData responseData = candleStickService.getCandleStickByEndEpochSecond(instrumentName, period, time);

        Assertions.assertEquals(responseData.getT(), time);
        Assertions.assertEquals(responseData.getV(), d2V);
    }

}
