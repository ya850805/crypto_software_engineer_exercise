package com.crypto.service.impl;

import com.crypto.service.TradeService;
import com.crypto.service.constant.CryptoConstant;
import com.crypto.service.entity.TradeResponseEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

        return tradeResponseEntity.getBody();
    }
}
