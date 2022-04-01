package com.crypto.service;

import com.crypto.constant.CryptoConstant;
import com.crypto.entity.TradeResponseData;
import com.crypto.entity.TradeResponseEntity;
import com.crypto.entity.TradeResponseResult;
import com.crypto.service.impl.TradeServiceImpl;
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
import java.util.*;

@RunWith(MockitoJUnitRunner.class)
public class TradeServiceTest {
    @InjectMocks
    private TradeServiceImpl tradeService;
    @Mock
    private RestTemplate restTemplate;

    @Test
    public void getTrades_TradeBTC_USDT_RestTemplateSendRequestOnce() {
        String instrumentName = CryptoConstant.INSTRUMENT_BTC_USDT;

        TradeResponseEntity entity = new TradeResponseEntity();
        ResponseEntity<TradeResponseEntity> responseEntity = new ResponseEntity<>(entity, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(Mockito.anyString(), Mockito.any(HttpMethod.class), Mockito.any(HttpEntity.class), Mockito.any(Class.class), Mockito.any(Map.class))).thenReturn(responseEntity);

        tradeService.getTrades(instrumentName);

        Mockito.verify(restTemplate, Mockito.times(1)).exchange(Mockito.anyString(), Mockito.any(HttpMethod.class), Mockito.any(HttpEntity.class), Mockito.any(Class.class), Mockito.any(Map.class));
    }

    @Test
    public void getFirstTradeDuringEpochSecond_Search3TradesAfterBeginTime_ReturnFirstTrade() {
        String instrumentName = CryptoConstant.INSTRUMENT_BTC_USDT;
        Long beginSecond = 60000L;

        //Time.
        Long t1T = 60001L;
        Long t2T = 60002L;
        Long t3T = 60003L;

        //Trade ID.
        Long t1D = 1L;
        Long t2D = 2L;
        Long t3D = 3L;

        List<TradeResponseData> dataList = new ArrayList<>();
        TradeResponseData t1 = new TradeResponseData();
        t1.setD(t1D);
        t1.setT(t1T);
        dataList.add(t1);

        TradeResponseData t2 = new TradeResponseData();
        t2.setD(t2D);
        t2.setT(t2T);
        dataList.add(t2);

        TradeResponseData t3 = new TradeResponseData();
        t3.setD(t3D);
        t3.setT(t3T);
        dataList.add(t3);

        TradeResponseEntity entity = new TradeResponseEntity();
        TradeResponseResult result = new TradeResponseResult();
        result.setData(dataList);
        entity.setResult(result);
        ResponseEntity<TradeResponseEntity> responseEntity = new ResponseEntity<>(entity, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(Mockito.anyString(), Mockito.any(HttpMethod.class), Mockito.any(HttpEntity.class), Mockito.any(Class.class), Mockito.any(Map.class))).thenReturn(responseEntity);

        TradeResponseData firstTradeDuringEpochSecond = tradeService.getFirstTradeDuringEpochSecond(instrumentName, beginSecond);

        Assertions.assertEquals(firstTradeDuringEpochSecond.getT(), t1T);
        Assertions.assertEquals(firstTradeDuringEpochSecond.getD(), t1D);
    }

    @Test
    public void getLastTradeDuringEpochSecond_3TradesInPeriod_ReturnLastTrade() {
        String instrumentName = CryptoConstant.INSTRUMENT_BTC_USDT;
        Long beginSecond = 60000L;
        Long endSecond = 120000L;

        //Time.
        Long t1T = 70000L;
        Long t2T = 80000L;
        Long t3T = 90000L;
        Long t4T = 200000L;

        //Trade ID.
        Long t1D = 1L;
        Long t2D = 2L;
        Long t3D = 3L;
        Long t4D = 4L;

        List<TradeResponseData> dataList = new ArrayList<>();
        TradeResponseData t1 = new TradeResponseData();
        t1.setD(t1D);
        t1.setT(t1T);
        dataList.add(t1);

        TradeResponseData t2 = new TradeResponseData();
        t2.setD(t2D);
        t2.setT(t2T);
        dataList.add(t2);

        TradeResponseData t3 = new TradeResponseData();
        t3.setD(t3D);
        t3.setT(t3T);
        dataList.add(t3);

        //Response for first time request.
        TradeResponseEntity entity = new TradeResponseEntity();
        TradeResponseResult result = new TradeResponseResult();
        result.setData(dataList);
        entity.setResult(result);
        ResponseEntity<TradeResponseEntity> responseEntity = new ResponseEntity<>(entity, HttpStatus.OK);

        TradeResponseData t4 = new TradeResponseData();
        t4.setD(t4D);
        t4.setT(t4T);

        //Response for second time request.
        TradeResponseEntity entity2 = new TradeResponseEntity();
        TradeResponseResult result2 = new TradeResponseResult();
        result2.setData(Arrays.asList(t4));
        entity2.setResult(result2);
        ResponseEntity<TradeResponseEntity> responseEntity2 = new ResponseEntity<>(entity2, HttpStatus.OK);

        Mockito.when(restTemplate.exchange(Mockito.anyString(), Mockito.any(HttpMethod.class), Mockito.any(HttpEntity.class), Mockito.any(Class.class), Mockito.any(Map.class))).thenReturn(responseEntity).thenReturn(responseEntity2);

        TradeResponseData lastTradeDuringEpochSecond = tradeService.getLastTradeDuringEpochSecond(instrumentName, beginSecond, endSecond);

        Assertions.assertEquals(lastTradeDuringEpochSecond.getT(), t3T);
        Assertions.assertEquals(lastTradeDuringEpochSecond.getD(), t3D);
    }

    @Test
    public void getHighestTradeDuringEpochSecond_3TradesInPeriod_ReturnHighestPriceTrade() {
        String instrumentName = CryptoConstant.INSTRUMENT_BTC_USDT;
        Long beginSecond = 60000L;
        Long endSecond = 120000L;

        //Time.
        Long t1T = 70000L;
        Long t2T = 80000L;
        Long t3T = 90000L;
        Long t4T = 200000L;

        //Trade ID.
        Long t1D = 1L;
        Long t2D = 2L;
        Long t3D = 3L;
        Long t4D = 4L;

        //Price
        BigDecimal t1P = new BigDecimal(1000);
        BigDecimal t2P = new BigDecimal(2000);
        BigDecimal t3P = new BigDecimal(3000);
        BigDecimal t4P = new BigDecimal(6000);

        List<TradeResponseData> dataList = new ArrayList<>();
        TradeResponseData t1 = new TradeResponseData();
        t1.setD(t1D);
        t1.setT(t1T);
        t1.setP(t1P);
        dataList.add(t1);

        TradeResponseData t2 = new TradeResponseData();
        t2.setD(t2D);
        t2.setT(t2T);
        t2.setP(t2P);
        dataList.add(t2);

        TradeResponseData t3 = new TradeResponseData();
        t3.setD(t3D);
        t3.setT(t3T);
        t3.setP(t3P);
        dataList.add(t3);

        //Response for first time request.
        TradeResponseEntity entity = new TradeResponseEntity();
        TradeResponseResult result = new TradeResponseResult();
        result.setData(dataList);
        entity.setResult(result);
        ResponseEntity<TradeResponseEntity> responseEntity = new ResponseEntity<>(entity, HttpStatus.OK);

        TradeResponseData t4 = new TradeResponseData();
        t4.setD(t4D);
        t4.setT(t4T);
        t4.setP(t4P);

        //Response for second time request.
        TradeResponseEntity entity2 = new TradeResponseEntity();
        TradeResponseResult result2 = new TradeResponseResult();
        result2.setData(Arrays.asList(t4));
        entity2.setResult(result2);
        ResponseEntity<TradeResponseEntity> responseEntity2 = new ResponseEntity<>(entity2, HttpStatus.OK);

        Mockito.when(restTemplate.exchange(Mockito.anyString(), Mockito.any(HttpMethod.class), Mockito.any(HttpEntity.class), Mockito.any(Class.class), Mockito.any(Map.class))).thenReturn(responseEntity).thenReturn(responseEntity2);

        TradeResponseData highestPriceTradeDuringEpochSecond = tradeService.getHighestTradeDuringEpochSecond(instrumentName, beginSecond, endSecond);

        Assertions.assertEquals(highestPriceTradeDuringEpochSecond.getP(), t3P);
        Assertions.assertEquals(highestPriceTradeDuringEpochSecond.getT(), t3T);
        Assertions.assertEquals(highestPriceTradeDuringEpochSecond.getD(), t3D);
    }

    @Test
    public void getLowestTradeDuringEpochSecond_3TradesInPeriod_ReturnLowestPriceTrade() {
        String instrumentName = CryptoConstant.INSTRUMENT_BTC_USDT;
        Long beginSecond = 60000L;
        Long endSecond = 120000L;

        //Time.
        Long t1T = 70000L;
        Long t2T = 80000L;
        Long t3T = 90000L;
        Long t4T = 200000L;

        //Trade ID.
        Long t1D = 1L;
        Long t2D = 2L;
        Long t3D = 3L;
        Long t4D = 4L;

        //Price
        BigDecimal t1P = new BigDecimal(1000);
        BigDecimal t2P = new BigDecimal(2000);
        BigDecimal t3P = new BigDecimal(3000);
        BigDecimal t4P = new BigDecimal(6000);

        List<TradeResponseData> dataList = new ArrayList<>();
        TradeResponseData t1 = new TradeResponseData();
        t1.setD(t1D);
        t1.setT(t1T);
        t1.setP(t1P);
        dataList.add(t1);

        TradeResponseData t2 = new TradeResponseData();
        t2.setD(t2D);
        t2.setT(t2T);
        t2.setP(t2P);
        dataList.add(t2);

        TradeResponseData t3 = new TradeResponseData();
        t3.setD(t3D);
        t3.setT(t3T);
        t3.setP(t3P);
        dataList.add(t3);

        //Response for first time request.
        TradeResponseEntity entity = new TradeResponseEntity();
        TradeResponseResult result = new TradeResponseResult();
        result.setData(dataList);
        entity.setResult(result);
        ResponseEntity<TradeResponseEntity> responseEntity = new ResponseEntity<>(entity, HttpStatus.OK);

        TradeResponseData t4 = new TradeResponseData();
        t4.setD(t4D);
        t4.setT(t4T);
        t4.setP(t4P);

        //Response for second time request.
        TradeResponseEntity entity2 = new TradeResponseEntity();
        TradeResponseResult result2 = new TradeResponseResult();
        result2.setData(Arrays.asList(t4));
        entity2.setResult(result2);
        ResponseEntity<TradeResponseEntity> responseEntity2 = new ResponseEntity<>(entity2, HttpStatus.OK);

        Mockito.when(restTemplate.exchange(Mockito.anyString(), Mockito.any(HttpMethod.class), Mockito.any(HttpEntity.class), Mockito.any(Class.class), Mockito.any(Map.class))).thenReturn(responseEntity).thenReturn(responseEntity2);

        TradeResponseData lowestPriceTradeDuringEpochSecond = tradeService.getLowestTradeDuringEpochSecond(instrumentName, beginSecond, endSecond);

        Assertions.assertEquals(lowestPriceTradeDuringEpochSecond.getP(), t1P);
        Assertions.assertEquals(lowestPriceTradeDuringEpochSecond.getT(), t1T);
        Assertions.assertEquals(lowestPriceTradeDuringEpochSecond.getD(), t1D);
    }
}
