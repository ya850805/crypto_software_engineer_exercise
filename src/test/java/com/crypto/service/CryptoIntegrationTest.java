package com.crypto.service;

import com.crypto.service.constant.CryptoConstant;
import com.crypto.service.entity.CandleStickResponseData;
import com.crypto.service.entity.TradeResponseData;
import com.crypto.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CryptoIntegrationTest {
    @Autowired
    private CandleStickService candleStickService;
    @Autowired
    private TradeService tradeService;

    /**
     * Verify "Open" price.
     */
    @Test
    @DisplayName("Verify open price in next one minute.")
    public void VerifyTheOpenPriceAreEquals_BTC_USDTinNextOneMinute_FirstTradePriceIsEqualsToFollowingCandleStickOpenPrice() throws InterruptedException {
        String instrumentName = CryptoConstant.INSTRUMENT_BTC_USDT;
        String period = CryptoConstant.PERIOD_ONE_MINUTE;

        /**
         * Get current timestamp plus one minute data.
         * ex. now is 13:01:30 p.m., then get 13:02:00~13:02:59 data.
         */
        LocalDateTime nextMinute = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).plusMinutes(1);
        Long startEpochSecond = nextMinute.atZone(TimeZone.getDefault().toZoneId()).toEpochSecond() * 1000;
        Long endEpochSecond = startEpochSecond + TimeUtil.parseEpochSecond(period);

        /**
         * Print time interval or test.
         */
        System.out.println("Inquiry trade between: ");
        LocalDateTime startDatetime = LocalDateTime.ofInstant(Instant.ofEpochMilli(startEpochSecond), TimeZone.getDefault().toZoneId());
        System.out.println(startDatetime);
        LocalDateTime endDatetime = LocalDateTime.ofInstant(Instant.ofEpochMilli(endEpochSecond), TimeZone.getDefault().toZoneId());
        System.out.println(endDatetime);

        System.out.println("====================");

        /**
         * Get first BTC_USTD trades between startEpochSecond and endEpochSecond.
         */
        System.out.println("Waiting to get first trade in time interval...");
        TradeResponseData trade = tradeService.getFirstTradeDuringEpochSecond(instrumentName, startEpochSecond);
        System.out.println("Already get trade.");

        System.out.println("====================");

        /**
         * Waiting to generate following candlestick.
         */
        System.out.println("Waiting to generate following candlestick...");
        Thread.sleep(TimeUtil.parseEpochSecond(CryptoConstant.PERIOD_ONE_MINUTE));

        /**
         * Get startEpochSecond BTC_USDT candle stick.
         */
        System.out.println("Waiting to get following candlestick data...");
        CandleStickResponseData candleStick = candleStickService.getCandleStickByEndEpochSecond(instrumentName, period, startEpochSecond);
        System.out.println("Already get candlestick.");

        System.out.println("====================");

        /**
         *Compare the first trade price in period is equals to candlestick open price or not.
         */
        System.out.println("Result: ");
        System.out.println("First trade price: " + trade.getP().setScale(2, RoundingMode.DOWN));
        System.out.println("Candlestick open price: " + candleStick.getO().setScale(2, RoundingMode.DOWN));
        Assertions.assertEquals(trade.getP().setScale(2, RoundingMode.DOWN), candleStick.getO().setScale(2, RoundingMode.DOWN));
    }

    /**
     * Verify "Close" price.
     */
    @Test
    @DisplayName("Verify close price in next one minute.")
    public void VerifyTheClosePriceAreEquals_BTC_USDTinNextOneMinute_LastTradePriceIsEqualsToFollowingCandleStickClosePrice() throws InterruptedException {
        String instrumentName = CryptoConstant.INSTRUMENT_BTC_USDT;
        String period = CryptoConstant.PERIOD_ONE_MINUTE;

        /**
         * Get current timestamp plus one minute data.
         * ex. now is 13:01:30 p.m., then get 13:02:00~13:02:59 data.
         */
        LocalDateTime nextMinute = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).plusMinutes(1);
        Long startEpochSecond = nextMinute.atZone(TimeZone.getDefault().toZoneId()).toEpochSecond() * 1000;
        Long endEpochSecond = startEpochSecond + TimeUtil.parseEpochSecond(period);

        /**
         * Print time interval or test.
         */
        System.out.println("Inquiry trade between: ");
        LocalDateTime startDatetime = LocalDateTime.ofInstant(Instant.ofEpochMilli(startEpochSecond), TimeZone.getDefault().toZoneId());
        System.out.println(startDatetime);
        LocalDateTime endDatetime = LocalDateTime.ofInstant(Instant.ofEpochMilli(endEpochSecond), TimeZone.getDefault().toZoneId());
        System.out.println(endDatetime);

        System.out.println("====================");

        /**
         * Get last BTC_USTD trades between startEpochSecond and endEpochSecond.
         */
        System.out.println("Waiting to get last trade in time interval...");
        TradeResponseData trade = tradeService.getLastTradeDuringEpochSecond(instrumentName, startEpochSecond, endEpochSecond);
        System.out.println("Already get trade.");

        System.out.println("====================");

        /**
         * Waiting to generate following candlestick.
         */
        System.out.println("Waiting to generate following candlestick...");
        Thread.sleep(TimeUtil.parseEpochSecond(CryptoConstant.PERIOD_ONE_MINUTE));

        /**
         * Get startEpochSecond BTC_USDT candle stick.
         */
        System.out.println("Waiting to get following candlestick data...");
        CandleStickResponseData candleStick = candleStickService.getCandleStickByEndEpochSecond(instrumentName, period, startEpochSecond);
        System.out.println("Already get candlestick.");

        System.out.println("====================");

        /**
         *Compare the last trade price in period is equals to candlestick close price or not.
         */
        System.out.println("Result: ");
        System.out.println("Last trade price: " + trade.getP().setScale(2, RoundingMode.DOWN));
        System.out.println("Candlestick close price: " + candleStick.getC().setScale(2, RoundingMode.DOWN));
        Assertions.assertEquals(trade.getP().setScale(2, RoundingMode.DOWN), candleStick.getC().setScale(2, RoundingMode.DOWN));
    }

    /**
     * Verify highest.
     */
    @Test
    @DisplayName("Verify highest price in next one minute.")
    public void VerifyTheHighestPriceAreEquals_BTC_USDTinNextOneMinute_HighestTradePriceIsEqualsToFollowingCandleStickHighestPrice() throws InterruptedException {
        String instrumentName = CryptoConstant.INSTRUMENT_BTC_USDT;
        String period = CryptoConstant.PERIOD_ONE_MINUTE;

        /**
         * Get current timestamp plus one minute data.
         * ex. now is 13:01:30 p.m., then get 13:02:00~13:02:59 data.
         */
        LocalDateTime nextMinute = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).plusMinutes(1);
        Long startEpochSecond = nextMinute.atZone(TimeZone.getDefault().toZoneId()).toEpochSecond() * 1000;
        Long endEpochSecond = startEpochSecond + TimeUtil.parseEpochSecond(period);

        /**
         * Print time interval or test.
         */
        System.out.println("Inquiry trade between: ");
        LocalDateTime startDatetime = LocalDateTime.ofInstant(Instant.ofEpochMilli(startEpochSecond), TimeZone.getDefault().toZoneId());
        System.out.println(startDatetime);
        LocalDateTime endDatetime = LocalDateTime.ofInstant(Instant.ofEpochMilli(endEpochSecond), TimeZone.getDefault().toZoneId());
        System.out.println(endDatetime);

        /**
         * Get highest BTC_USTD trades between startEpochSecond and endEpochSecond.
         */
        System.out.println("Waiting to get highest trade in time interval...");
        TradeResponseData trade = tradeService.getHighestTradeDuringEpochSecond(instrumentName, startEpochSecond, endEpochSecond);
        System.out.println("Already get trade.");

        System.out.println("====================");

        /**
         * Waiting to generate following candlestick.
         */
        System.out.println("Waiting to generate following candlestick...");
        Thread.sleep(TimeUtil.parseEpochSecond(CryptoConstant.PERIOD_ONE_MINUTE));

        /**
         * Get startEpochSecond BTC_USDT candle stick.
         */
        System.out.println("Waiting to get following candlestick data...");
        CandleStickResponseData candleStick = candleStickService.getCandleStickByEndEpochSecond(instrumentName, period, startEpochSecond);
        System.out.println("Already get candlestick.");

        System.out.println("====================");

        /**
         *Compare the highest trade price in period is equals to candlestick high price or not.
         */
        System.out.println("Result: ");
        System.out.println("Highest trade price: " + trade.getP().setScale(2, RoundingMode.DOWN));
        System.out.println("Candlestick high price: " + candleStick.getH().setScale(2, RoundingMode.DOWN));
        Assertions.assertEquals(trade.getP().setScale(2, RoundingMode.DOWN), candleStick.getH().setScale(2, RoundingMode.DOWN));
    }

    /**
     * Verify lowest.
     */
    @Test
    @DisplayName("Verify lowest price in next one minute.")
    public void VerifyTheLowestPriceAreEquals_BTC_USDTinNextOneMinute_LowestTradePriceIsEqualsToFollowingCandleStickLowestPrice() throws InterruptedException {
        String instrumentName = CryptoConstant.INSTRUMENT_BTC_USDT;
        String period = CryptoConstant.PERIOD_ONE_MINUTE;

        /**
         * Get current timestamp plus one minute data.
         * ex. now is 13:01:30 p.m., then get 13:02:00~13:02:59 data.
         */
        LocalDateTime nextMinute = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).plusMinutes(1);
        Long startEpochSecond = nextMinute.atZone(TimeZone.getDefault().toZoneId()).toEpochSecond() * 1000;
        Long endEpochSecond = startEpochSecond + TimeUtil.parseEpochSecond(period);

        /**
         * Print time interval or test.
         */
        System.out.println("Inquiry trade between: ");
        LocalDateTime startDatetime = LocalDateTime.ofInstant(Instant.ofEpochMilli(startEpochSecond), TimeZone.getDefault().toZoneId());
        System.out.println(startDatetime);
        LocalDateTime endDatetime = LocalDateTime.ofInstant(Instant.ofEpochMilli(endEpochSecond), TimeZone.getDefault().toZoneId());
        System.out.println(endDatetime);

        /**
         * Get lowest BTC_USTD trades between startEpochSecond and endEpochSecond.
         */
        System.out.println("Waiting to get lowest trade in time interval...");
        TradeResponseData trade = tradeService.getLowestTradeDuringEpochSecond(instrumentName, startEpochSecond, endEpochSecond);
        System.out.println("Already get trade.");

        System.out.println("====================");

        /**
         * Get startEpochSecond BTC_USDT candle stick.
         */
        System.out.println("Waiting to get following candlestick data...");
        CandleStickResponseData candleStick = candleStickService.getCandleStickByEndEpochSecond(instrumentName, period, startEpochSecond);
        System.out.println("Already get candlestick.");

        System.out.println("====================");

        /**
         * Waiting to generate following candlestick.
         */
        System.out.println("Waiting to generate following candlestick...");
        Thread.sleep(TimeUtil.parseEpochSecond(CryptoConstant.PERIOD_ONE_MINUTE));

        /**
         *Compare the lowest trade price in period is equals to candlestick low price or not.
         */
        System.out.println("Result: ");
        System.out.println("Lowest trade price: " + trade.getP().setScale(2, RoundingMode.DOWN));
        System.out.println("Candlestick low price: " + candleStick.getL().setScale(2, RoundingMode.DOWN));
        Assertions.assertEquals(trade.getP().setScale(2, RoundingMode.DOWN), candleStick.getL().setScale(2, RoundingMode.DOWN));
    }
}
