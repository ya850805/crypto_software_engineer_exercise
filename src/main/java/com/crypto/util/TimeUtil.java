package com.crypto.util;

import com.crypto.constant.CryptoConstant;

public class TimeUtil {
    public static Long parseEpochSecond(String period) {
        switch (period) {
            case CryptoConstant.PERIOD_ONE_MINUTE:
                return 60000L;
            case CryptoConstant.PERIOD_FIVE_MINUTE:
                return 300000L;
            case CryptoConstant.PERIOD_FIFTEEN_MINUTE:
                return 900000L;
            case CryptoConstant.PERIOD_THIRTY_MINUTE:
                return 1800000L;
            case CryptoConstant.PERIOD_ONE_HOUR:
                return 3600000L;
            case CryptoConstant.PERIOD_FOUR_HOUR:
                return 14400000L;
            case CryptoConstant.PERIOD_SIX_HOUR:
                return 21600000L;
            case CryptoConstant.PERIOD_TWELVE_HOUR:
                return 43200000L;
            case CryptoConstant.PERIOD_ONE_DAY:
                return 86400000L;
            case CryptoConstant.PERIOD_ONE_WEEK:
                return 604800000L;
            case CryptoConstant.PERIOD_TWO_WEEKS:
                return 1209600000L;
            case CryptoConstant.PERIOD_ONE_MONTH:
                return 2419200000L;
            default:
                return 0L;
        }

    }
}
