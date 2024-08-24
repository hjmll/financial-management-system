package com.hs.settlement.service;

import com.hs.settlement.domain.Request;
import com.hs.settlement.domain.Transaction;

import java.util.List;

public interface Redemption {

    // 找出当前的日期下的每一条赎回申请和对应产品的当日净值
    // 修改赎回金额，修改状态为确认
    List<Request> confirmRedempt(String date, String newDate, String transactionId);

    // 插入一条收入的赎回确认的金额流水
    List<Transaction> getTransaction(String Date);



    // 计算当天所有资金交易数
    int countTodayTransaction(String date);
}
