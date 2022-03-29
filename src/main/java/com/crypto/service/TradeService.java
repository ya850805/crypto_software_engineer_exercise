package com.crypto.service;

import com.crypto.service.entity.TradeResponseEntity;

public interface TradeService {
    TradeResponseEntity getTrades(String instrumentName);
}
