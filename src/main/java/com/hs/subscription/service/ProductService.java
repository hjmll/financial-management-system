package com.hs.subscription.service;

import java.util.List;

public interface ProductService {
    List<String> selectProduct();

    String selectRiskLevelByProductCode(String productCode);
}
