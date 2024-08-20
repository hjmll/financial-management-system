package com.hs.subscription.service.impl;

import com.hs.subscription.domain.PreTrade;
import com.hs.subscription.domain.Subscription;
import com.hs.subscription.mapper.CustomerMapper;
import com.hs.subscription.mapper.ProductMapper;
import com.hs.subscription.mapper.SubscriptionMapper;
import com.hs.subscription.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    @Autowired
    SubscriptionMapper subscriptionMapper;

    @Autowired
    CustomerMapper customerMapper;

    @Autowired
    ProductMapper productMapper;

    @Override
    public double findBalance(String fundAccount, String productCode) {
        return subscriptionMapper.findBalance(subscriptionMapper.findBankCardNumber(fundAccount, productCode));
    }

    @Override
    public int findTodayTransactionNumber() {
        return subscriptionMapper.findTodayTransactionNumber();
    }

    @Override
    public void subscribe(Subscription subscription) {
        LocalDate localDate = LocalDate.now();
        Date date = Date.valueOf(localDate);
        String transactionNumber = subscription.getTransactionNumber();
        double amount = subscription.getAmount();
        String productCode =subscription.getProductCode();
        String fundAccount = subscription.getFundAccount();
        String cardNumber = subscriptionMapper.findBankCardNumber(fundAccount, productCode);
        subscriptionMapper.insertTransaction(transactionNumber,cardNumber, date,
                "申购申请",amount,"OUT");
        subscriptionMapper.insertRequest(transactionNumber, productCode, cardNumber, "申购",
                amount, date, "待确认");
        subscriptionMapper.updateBalance(cardNumber, amount);
        subscriptionMapper.updateBankcardLastUpdated(cardNumber, date);
    }

    @Override
    public PreTrade preTrade(String fundAccount, String productCode) {
        PreTrade preTrade = new PreTrade();
        preTrade.setBalance(findBalance(fundAccount, productCode));
        preTrade.setCustomerRiskLevel(customerMapper.selectCustomerByFundAccount(fundAccount).getRiskLevel());
        preTrade.setProductRiskLevel(productMapper.selectRiskLevelByProductCode(productCode));
        return preTrade;
    }
}
