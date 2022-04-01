package com.crypto.entity;

import lombok.Data;

import java.util.List;

@Data
public class TradeResponseResult {
    /**
     * data list.
     */
    private List<TradeResponseData> data;
}
