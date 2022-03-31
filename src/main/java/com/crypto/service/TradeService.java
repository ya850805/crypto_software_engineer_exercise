package com.crypto.service;

import com.crypto.service.entity.TradeResponseData;
import com.crypto.service.entity.TradeResponseEntity;
import com.crypto.service.entity.TradeResponseResult;

public interface TradeService {
    TradeResponseEntity getTrades(String instrumentName);
    TradeResponseData getFirstTradeDuringEpochSecond(String instrumentName, Long beginSecond);
    TradeResponseData getLastTradeDuringEpochSecond(String instrumentName, Long endSecond);
    TradeResponseData getHighestTradeDuringEpochSecond(String instrumentName, Long beginSecond, Long endSecond);
}
