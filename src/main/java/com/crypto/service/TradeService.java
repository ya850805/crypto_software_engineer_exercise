package com.crypto.service;

import com.crypto.service.entity.TradeResponseData;
import com.crypto.service.entity.TradeResponseEntity;

public interface TradeService {
    TradeResponseEntity getTrades(String instrumentName);
    TradeResponseData getFirstTradeDuringEpochSecond(String instrumentName, Long beginSecond);
    TradeResponseData getLastTradeDuringEpochSecond(String instrumentName, Long beginSecond, Long endSecond);
    TradeResponseData getHighestTradeDuringEpochSecond(String instrumentName, Long beginSecond, Long endSecond);
    TradeResponseData getLowestTradeDuringEpochSecond(String instrumentName, Long beginSecond, Long endSecond);
}
