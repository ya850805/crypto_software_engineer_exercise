package com.crypto.service.impl;

import com.crypto.service.CandleStickService;
import com.crypto.constant.CryptoConstant;
import com.crypto.entity.CandleStickResponseData;
import com.crypto.entity.CandleStickResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CandleStickServiceImpl implements CandleStickService {
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public CandleStickResponseEntity getCandleStick(String instrumentName, String period) {
        /**
         * Builder request url, parameters and headers.
         */
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity httpEntity = new HttpEntity(headers);

        String urlTemplate = UriComponentsBuilder.fromHttpUrl(CryptoConstant.REQUEST_CANDLESTICK_URL)
                .queryParam(CryptoConstant.REQUEST_PARAM_INSTRUMENT_NAME, "{instrument_name}")
                .queryParam(CryptoConstant.REQUEST_PARAM_PERIOD, "{timeframe}")
                .encode()
                .toUriString();

        Map<String, String> params = new HashMap<>();
        params.put(CryptoConstant.REQUEST_PARAM_INSTRUMENT_NAME, instrumentName);
        params.put(CryptoConstant.REQUEST_PARAM_PERIOD, period);

        /**
         * Send request.
         */
        ResponseEntity<CandleStickResponseEntity> candleStickResponseEntity = restTemplate.exchange(urlTemplate, HttpMethod.GET, httpEntity, CandleStickResponseEntity.class, params);

        return candleStickResponseEntity.getBody();
    }

    @Override
    public CandleStickResponseData getCandleStickByEndEpochSecond(String instrumentName, String period, Long time) {
        List<CandleStickResponseData> data = getCandleStick(instrumentName, period).getResult().getData();

        while (!data.stream().anyMatch(c -> c.getT().equals(time))) {
            data = getCandleStick(instrumentName, period).getResult().getData();
        }

        return data.stream().filter(c -> c.getT().equals(time)).findFirst().get();
    }
}
