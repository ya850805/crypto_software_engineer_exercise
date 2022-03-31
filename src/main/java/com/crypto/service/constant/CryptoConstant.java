package com.crypto.service.constant;

public class CryptoConstant {
    /**
     * URL constant.
     */
    public static final String REQUEST_TRADE_URL = "https://api.crypto.com/v2/public/get-trades";
    public static final String REQUEST_CANDLESTICK_URL = "https://api.crypto.com/v2/public/get-candlestick";
    public static final String REQUEST_PARAM_INSTRUMENT_NAME = "instrument_name";
    public static final String REQUEST_PARAM_PERIOD = "timeframe";

    /**
     * Instrument constant.
     */
    public static final String INSTRUMENT_BTC_USDT = "BTC_USDT";

    /**
     * Period constant.
     */
    public static final String PERIOD_ONE_MINUTE = "1m";
    public static final String PERIOD_FIVE_MINUTE = "5m";
    public static final String PERIOD_FIFTEEN_MINUTE = "15m";
    public static final String PERIOD_THIRTY_MINUTE = "30m";
    public static final String PERIOD_ONE_HOUR = "1h";
    public static final String PERIOD_FOUR_HOUR = "4h";
    public static final String PERIOD_SIX_HOUR = "6h";
    public static final String PERIOD_TWELVE_HOUR = "12h";
    public static final String PERIOD_ONE_DAY = "1D";
    public static final String PERIOD_ONE_WEEK = "7D";
    public static final String PERIOD_TWO_WEEKS = "14D";
    public static final String PERIOD_ONE_MONTH = "1M";

    /**
     * Response code constant.
     */
    public static final Integer SUCCESS_CODE = 0;
}
