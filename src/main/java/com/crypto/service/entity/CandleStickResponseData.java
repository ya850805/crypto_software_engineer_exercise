package com.crypto.service.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CandleStickResponseData {
    /**
     * End time of candlestick (Unix timestamp)
     */
    private Long t;

    /**
     * Open
     */
    private BigDecimal o;

    /**
     * High
     */
    private BigDecimal h;

    /**
     * Low
     */
    private BigDecimal l;

    /**
     * Close
     */
    private BigDecimal c;

    /**
     * Volumn
     */
    private BigDecimal v;
}
