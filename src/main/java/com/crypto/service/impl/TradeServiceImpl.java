package com.crypto.service.impl;

import com.crypto.service.TradeService;
import com.crypto.constant.CryptoConstant;
import com.crypto.entity.TradeResponseData;
import com.crypto.entity.TradeResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TradeServiceImpl implements TradeService {
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public TradeResponseEntity getTrades(String instrumentName) {
        /**
         * Builder request url, parameters and headers.
         */
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity httpEntity = new HttpEntity(headers);

        String urlTemplate = UriComponentsBuilder.fromHttpUrl(CryptoConstant.REQUEST_TRADE_URL)
                .queryParam(CryptoConstant.REQUEST_PARAM_INSTRUMENT_NAME, "{instrument_name}")
                .encode()
                .toUriString();

        Map<String, String> params = new HashMap<>();
        params.put(CryptoConstant.REQUEST_PARAM_INSTRUMENT_NAME, instrumentName);

        /**
         * Send request.
         */
        ResponseEntity<TradeResponseEntity> tradeResponseEntity = restTemplate.exchange(urlTemplate, HttpMethod.GET, httpEntity, TradeResponseEntity.class, params);

        return tradeResponseEntity.getBody();
    }

    @Override
    public TradeResponseData getFirstTradeDuringEpochSecond(String instrumentName, Long beginSecond) {
        List<TradeResponseData> dataList = getTrades(instrumentName).getResult().getData().stream().sorted(Comparator.comparingLong(t -> t.getT().longValue())).collect(Collectors.toList());

        /**
         * Fetch data then sort by trade timestamp continuously.
         */
        while (!dataList.stream().anyMatch(t -> t.getT().compareTo(beginSecond) >= 0)) {
            dataList = getTrades(instrumentName).getResult().getData().stream().sorted(Comparator.comparingLong(t -> t.getT().longValue())).collect(Collectors.toList());
        }

        /**
         * Get first trade after beginSecond.
         */
        return dataList.stream().filter(t -> t.getT().compareTo(beginSecond) >= 0).findFirst().get();
    }

    @Override
    public TradeResponseData getLastTradeDuringEpochSecond(String instrumentName, Long beginSecond, Long endSecond) {
        List<TradeResponseData> allTradeDuringPeriod = new LinkedList<>();
        List<TradeResponseData> dataList = getTrades(instrumentName).getResult().getData();

        while (dataList.stream().anyMatch(t -> t.getT().compareTo(beginSecond) >= 0 && t.getT().compareTo(endSecond) <= 0) || allTradeDuringPeriod.isEmpty()) {
            allTradeDuringPeriod.addAll(dataList.stream().filter(t -> t.getT().compareTo(beginSecond) >= 0 && t.getT().compareTo(endSecond) <= 0 && !allTradeDuringPeriod.contains(t)).collect(Collectors.toList()));
            dataList = getTrades(instrumentName).getResult().getData();
        }

        return allTradeDuringPeriod.stream().sorted((t1, t2) -> -t1.getT().compareTo(t2.getT())).findFirst().get();
    }

    @Override
    public TradeResponseData getHighestTradeDuringEpochSecond(String instrumentName, Long beginSecond, Long endSecond) {
        List<TradeResponseData> list = new LinkedList<>();

        List<TradeResponseData> dataList = getTrades(instrumentName).getResult().getData();

        /**
         * Fetch data then add to list continuously.
         */
        while (dataList.stream().anyMatch(t -> t.getT().compareTo(beginSecond) >= 0 && t.getT().compareTo(endSecond) <= 0) || list.isEmpty()) {
            /**
             * Add the data that didn't exist in list.
             */
            list.addAll(dataList.stream().filter(t -> t.getT().compareTo(beginSecond) >= 0 && t.getT().compareTo(endSecond) <= 0 && !list.contains(t)).collect(Collectors.toList()));
            dataList = getTrades(instrumentName).getResult().getData();
        }

        /**
         * Sorted the list by trade price descending, then get first element(highest price).
         */
        return list.stream().sorted((t1, t2) -> -t1.getP().compareTo(t2.getP())).findFirst().get();
    }

    @Override
    public TradeResponseData getLowestTradeDuringEpochSecond(String instrumentName, Long beginSecond, Long endSecond) {
        List<TradeResponseData> list = new LinkedList<>();

        List<TradeResponseData> dataList = getTrades(instrumentName).getResult().getData();

        /**
         * Fetch data then add to list continuously.
         */
        while (dataList.stream().anyMatch(t -> t.getT().compareTo(beginSecond) >= 0 && t.getT().compareTo(endSecond) <= 0) || list.isEmpty()) {
            /**
             * Add the data that didn't exist in list.
             */
            list.addAll(dataList.stream().filter(t -> t.getT().compareTo(beginSecond) >= 0 && t.getT().compareTo(endSecond) <= 0 && !list.contains(t)).collect(Collectors.toList()));
            dataList = getTrades(instrumentName).getResult().getData();
        }

        /**
         * Sorted the list by trade price ascending, then get first element(lowest price).
         */
        return list.stream().sorted(Comparator.comparing(TradeResponseData::getP)).findFirst().get();
    }
}
