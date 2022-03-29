package com.crypto.service.entity;

import lombok.Data;

import java.util.List;

@Data
public class TradeResponseEntity extends BaseResponseEntity {
    /**
     * data list.
     */
    private List<TradeResponseData> tradeResponseDataList;
}
