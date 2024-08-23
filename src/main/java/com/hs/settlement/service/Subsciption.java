package com.hs.settlement.service;

import com.hs.settlement.domain.Request;

import java.util.List;

public interface Subsciption {

    // 找出上一交易日的每一条申购申请和对应产品的当日净值
    // 计算份额，修改份额流水状态为确认并填写份额，触发器自动更新持仓中的持有份额
    List<Request> confirmSubscribe(String date);
}
