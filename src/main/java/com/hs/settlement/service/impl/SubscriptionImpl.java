package com.hs.settlement.service.impl;

import com.hs.settlement.domain.Request;
import com.hs.settlement.mapper.SubsciptionMapper;
import com.hs.settlement.service.Subsciption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class SubscriptionImpl implements Subsciption {

    @Autowired
    private SubsciptionMapper subsciptionMapper;

    @Override
    public List<Request> confirmSubscribe(String date) {
        // 从request表中获取当前日期的申购申请
        List<Request> requests = subsciptionMapper.getSubRequestsByDate(date);

        for (Request request : requests) {
            String productCode = request.getProductCode();
            double nav = subsciptionMapper.getProductNav(productCode); // 获取产品的当日净值

            // 计算申购的份额
            double shares = request.getAmount() / nav;

            // 更新request表，修改状态为确认并填写份额，数据库自动触发更新holding表
            request.setShares(shares);
            request.setStatus("确认");
            subsciptionMapper.updateRequest(request);
        }
        return requests;
    }
}