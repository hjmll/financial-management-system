package com.hs.subscription.service;

import org.springframework.stereotype.Service;

import java.util.List;
@Service("subProductService")
public interface ProductService {
    List<String> selectProduct();

    String selectRiskLevelByProductCode(String productCode);
}
