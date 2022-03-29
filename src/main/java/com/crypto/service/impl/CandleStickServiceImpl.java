package com.crypto.service.impl;

import com.crypto.service.CandleStickService;
import com.crypto.service.constant.CryptoConstant;
import com.crypto.service.entity.CandleStickResponseEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CandleStickServiceImpl implements CandleStickService {


    @Override
    public CandleStickResponseEntity getCandleStick(String instrumentName, String period) {
        RestTemplate restTemplate = new RestTemplate();

        /**
         * Concat request URL.
         */
        StringBuilder url = new StringBuilder();
        url.append(CryptoConstant.REQUEST_URL_PREFIX);
        url.append(CryptoConstant.REQUEST_URL_GET_CANDLESTICK);
        url.append(CryptoConstant.REQUEST_URL_PARAMETER_INSTRUMENT_NAME);
        url.append(instrumentName);
        url.append(CryptoConstant.REQUEST_URL_PARAMETER_CONCAT);
        url.append(CryptoConstant.REQUEST_URL_PARAMETER_PERIOD);
        url.append(period);

        /**
         * Send request.
         */
        ResponseEntity<CandleStickResponseEntity> responseEntity = restTemplate.getForEntity(url.toString(), CandleStickResponseEntity.class);
        return responseEntity.getBody();
    }
}
