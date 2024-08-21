package com.hs.subscription.service.impl;

import com.hs.subscription.domain.Subscription;
import com.hs.subscription.mapper.SubCustomerMapper;
import com.hs.subscription.mapper.SubProductMapper;
import com.hs.subscription.mapper.SubscriptionMapper;
import com.hs.subscription.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    @Autowired
    SubscriptionMapper subscriptionMapper;

    @Autowired
    SubCustomerMapper customerMapper;

    @Autowired
    SubProductMapper productMapper;

    @Override
    @Transactional
    public double findBalance(String fundAccount, String tradingAccount) {
        return subscriptionMapper.findBalance(subscriptionMapper.findBankCardNumber(fundAccount, tradingAccount));
    }

    @Override
    public int findTodayRequestNumber(String date) {
        return subscriptionMapper.findTodayRequestNumber(date);
    }

    @Override
    @Transactional
    public void subscribe(Subscription subscription) {
        String date = subscription.getDate();
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

//    @Override
//    public PreTrade preTrade(String fundAccount, String productCode) {
//        PreTrade preTrade = new PreTrade();
//        preTrade.setBalance(findBalance(fundAccount, productCode));
//        preTrade.setCustomerRiskLevel(customerMapper.selectCustomerByFundAccount(fundAccount).getRiskLevel());
//        preTrade.setProductRiskLevel(productMapper.selectRiskLevelByProductCode(productCode));
//        return preTrade;
//    }
}
