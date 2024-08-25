package com.hs.settlement.service.impl;

import com.hs.settlement.domain.Request;
import com.hs.settlement.domain.Transaction;
import com.hs.settlement.mapper.RedemptionMapper2;
import com.hs.settlement.mapper.SubsciptionMapper;
import com.hs.settlement.service.Redemption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class RedemptionImpl implements Redemption {

    @Autowired
    private RedemptionMapper2 redemptionMapper;

    @Autowired
    private SubsciptionMapper subsciptionMapper;

    // 找出当前的日期下的每一条赎回申请和对应产品的当日净值
    // 修改赎回金额，修改状态为确认
    @Override
    public List<Request> confirmRedempt(String date, String newDate, String transactionId) {
        // 从request表中获取上一交易日的赎回申请
        List<Request> requests = redemptionMapper.getRedRequestsByDate(date);

        for (Request request : requests) {
            String productCode = request.getProductCode();
            double nav = subsciptionMapper.getProductNav(productCode); // 获取产品的当日净值

            // 计算赎回的金额
            double amount = request.getShares() * nav;

            // 更新request表，修改状态为确认并填写金额
            request.setAmount(amount);
            request.setStatus("确认");
            redemptionMapper.updateRequest(request);

            // 更新transaction表，插入今天日期的新的赎回金额到账流水
            Transaction transaction = new Transaction();
            transaction.setId(transactionId);
            transaction.setAmount(amount);
            transaction.setDate(newDate);
            transaction.setDescription("赎回确认");
            transaction.setType("IN");
            transaction.setCardNumber(request.getCardNumber());
            redemptionMapper.insertTransaction(transaction); // 数据库触发更新银行卡余额
        }
        return requests;
    }

    @Override
    public List<Transaction> getTransaction(String date) {
        return redemptionMapper.getTodayTransaction(date);
    }

    @Override
    public int countTodayTransaction(String date) {
        return redemptionMapper.countTodayTransaction(date);
    }

}
