package com.crypto.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CandleStickResponseResult {
    /**
     * e.g. ETH_CRO, BTC_USDT
     */
    @JsonProperty("instrument_name")
    private String instrumentName;

    /**
     * The period (e.g. 5m)
     */
    private String interval;

    /**
     * data list.
     */
    private List<CandleStickResponseData> data;
}
