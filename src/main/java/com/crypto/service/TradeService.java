package com.crypto.service;

import com.crypto.entity.TradeResponseData;
import com.crypto.entity.TradeResponseEntity;

public interface TradeService {
    TradeResponseEntity getTrades(String instrumentName);
    TradeResponseData getFirstTradeDuringEpochSecond(String instrumentName, Long beginSecond);
    TradeResponseData getLastTradeDuringEpochSecond(String instrumentName, Long beginSecond, Long endSecond);
    TradeResponseData getHighestTradeDuringEpochSecond(String instrumentName, Long beginSecond, Long endSecond);
    TradeResponseData getLowestTradeDuringEpochSecond(String instrumentName, Long beginSecond, Long endSecond);
}
