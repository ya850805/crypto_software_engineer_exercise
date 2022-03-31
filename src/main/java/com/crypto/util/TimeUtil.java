package com.crypto.util;

import com.crypto.service.constant.CryptoConstant;

public class TimeUtil {
    public static Long parseEpochSecond(String period) {
        switch (period) {
            case CryptoConstant.PERIOD_ONE_MINUTE:
                return 60000L;
            //TODO other time
            default:
                return 0L;
        }

    }
}
