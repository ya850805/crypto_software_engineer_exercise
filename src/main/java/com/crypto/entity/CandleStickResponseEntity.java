package com.crypto.entity;

import lombok.Data;

@Data
public class CandleStickResponseEntity extends BaseResponseEntity {
    private CandleStickResponseResult result;
}
