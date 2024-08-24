package com.hs.settlement.domain;

import lombok.Data;

@Data
public class Request { // 只包含了清算业务需要的字段
    private String id; // 份额订单号
    private String productCode; // 产品代码
    private double amount; // 申购或赎回金额
    private double shares; // 交易份额
    private String status; // 订单状态
    private String cardNumber; // 银行卡号
}
