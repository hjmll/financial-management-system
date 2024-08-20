package com.hs.subscription.service;

import com.hs.subscription.domain.PreTrade;
import com.hs.subscription.domain.Subscription;

public interface SubscriptionService {
    double findBalance(String fundAccount, String productCode);

    int findTodayTransactionNumber();

    void subscribe(Subscription subscription);

    PreTrade preTrade(String fundAccount, String productCode);
}
