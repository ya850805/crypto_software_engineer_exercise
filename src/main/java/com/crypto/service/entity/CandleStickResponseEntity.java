package com.crypto.service.entity;

import lombok.Data;

import java.util.List;

@Data
public class CandleStickResponseEntity extends BaseResponseEntity {
    /**
     * e.g. ETH_CRO, BTC_USDT
     */
    private String instrumentName;

    /**
     * The period (e.g. 5m)
     */
    private String interval;

    /**
     * data list.
     */
    private List<CandleStickResponseData> candleStickResponseDataList;
}
