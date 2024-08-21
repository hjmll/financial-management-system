package com.hs.subscription.service;

import com.hs.subscription.domain.Subscription;

public interface SubscriptionService {
    double findBalance(String fundAccount, String productCode);

    int findTodayRequestNumber(String date);

    void subscribe(Subscription subscription);

//    PreTrade preTrade(String fundAccount, String productCode);
}
