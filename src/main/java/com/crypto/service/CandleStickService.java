package com.crypto.service;

import com.crypto.service.entity.CandleStickResponseEntity;

public interface CandleStickService {
    CandleStickResponseEntity getCandleStick(String instrumentName, String period);
}
