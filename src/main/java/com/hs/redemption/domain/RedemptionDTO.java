package com.hs.redemption.domain;

import lombok.Data;

@Data
public class RedemptionDTO {
    /**
     * 赎回份额
     */
    private double shares;

    /**
     * 产品代码
     */
    private String productCode;

    /**
     * 卡号
     */
    private String cardNumber;

    /**
     * 赎回日期
     */
    private String date;
}
