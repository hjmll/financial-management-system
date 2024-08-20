package com.hs.subscription.service;

import com.hs.subscription.domain.PreTrade;

import java.util.List;

public interface ProductService {
    List<String> selectProductsByFundAccount(String fundAccount);
}
