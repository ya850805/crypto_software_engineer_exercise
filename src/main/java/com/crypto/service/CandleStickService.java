package com.crypto.service;

import com.crypto.entity.CandleStickResponseData;
import com.crypto.entity.CandleStickResponseEntity;

public interface CandleStickService {
    CandleStickResponseEntity getCandleStick(String instrumentName, String period);
    CandleStickResponseData getCandleStickByEndEpochSecond(String instrumentName, String period, Long endTime);
}
