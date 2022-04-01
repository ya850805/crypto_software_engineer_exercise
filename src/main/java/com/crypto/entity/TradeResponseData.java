package com.crypto.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TradeResponseData {
    /**
     * Reserved. Can be ignored
     */
    private Long dataTime;

    /**
     * Trade ID
     */
    private Long d;

    /**
     * Side ("buy" or "sell")
     */
    private String s;

    /**
     * Trade price
     */
    private BigDecimal p;

    /**
     * Trade quantity
     */
    private BigDecimal q;

    /**
     * Trade timestamp
     */
    private Long t;

    /**
     * e.g. ETH_CRO, BTC_USDT
     */
    private String i;

}
