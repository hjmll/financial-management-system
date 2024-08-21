package com.hs.subscription.service.impl;

import com.hs.subscription.mapper.ProductMapper;
import com.hs.subscription.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<String> selectProduct() {
        return productMapper.selectProduct();
    }

    @Override
    public String selectRiskLevelByProductCode(String productCode) {
        return productMapper.selectRiskLevelByProductCode(productCode);
    }

}
