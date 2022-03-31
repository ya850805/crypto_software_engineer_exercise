package com.crypto.service.impl;

import com.crypto.service.TradeService;
import com.crypto.service.constant.CryptoConstant;
import com.crypto.service.entity.TradeResponseData;
import com.crypto.service.entity.TradeResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TradeServiceImpl implements TradeService {
    @Override
    public TradeResponseEntity getTrades(String instrumentName) {
        RestTemplate restTemplate = new RestTemplate();

        /**
         * Concat request URL.
         */
        StringBuilder url = new StringBuilder();
        url.append(CryptoConstant.REQUEST_URL_PREFIX);
        url.append(CryptoConstant.REQUEST_URL_GET_TRADES);
        url.append(CryptoConstant.REQUEST_URL_PARAMETER_INSTRUMENT_NAME);
        url.append(instrumentName);

        /**
         * Send request.
         */
        ResponseEntity<TradeResponseEntity> tradeResponseEntity = restTemplate.getForEntity(url.toString(), TradeResponseEntity.class);

        log.debug("get trades...");

        return tradeResponseEntity.getBody();
    }

    @Override
    public TradeResponseData getFirstTradeDuringEpochSecond(String instrumentName, Long beginSecond) {
        List<TradeResponseData> dataList = getTrades(instrumentName).getResult().getData().stream().sorted(Comparator.comparingLong(t -> t.getT().longValue())).collect(Collectors.toList());

        while (!dataList.stream().anyMatch(t -> t.getT().compareTo(beginSecond) >= 0)) {
            dataList = getTrades(instrumentName).getResult().getData().stream().sorted(Comparator.comparingLong(t -> t.getT().longValue())).collect(Collectors.toList());
        }

        return dataList.stream().filter(t -> t.getT().compareTo(beginSecond) >= 0).findFirst().get();
    }

    @Override
    public TradeResponseData getLastTradeDuringEpochSecond(String instrumentName, Long endSecond) {
        List<TradeResponseData> dataList = getTrades(instrumentName).getResult().getData().stream().sorted(Comparator.comparingLong(t -> t.getT().longValue())).collect(Collectors.toList());

        while(!dataList.stream().anyMatch(t -> t.getT().compareTo(endSecond) > 0)) {
            dataList = getTrades(instrumentName).getResult().getData().stream().sorted(Comparator.comparingLong(t -> t.getT().longValue())).collect(Collectors.toList());
        }

        return dataList.stream().filter(t -> t.getT().compareTo(endSecond) <= 0).reduce((t1, t2) -> t2).get();
    }
}
