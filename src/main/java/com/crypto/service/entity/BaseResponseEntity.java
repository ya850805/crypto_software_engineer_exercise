package com.crypto.service.entity;

import lombok.Data;

@Data
public class BaseResponseEntity {
    /**
     * Response code, 0: success
     */
    private Integer code;
    private String method;
}
