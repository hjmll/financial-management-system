package com.hs.subscription.service.impl;

import com.hs.subscription.mapper.SubProductMapper;
import com.hs.subscription.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class SubProductServiceImpl implements ProductService {

    @Autowired
    private SubProductMapper productMapper;

    @Override
    public List<String> selectProduct() {
        return productMapper.selectProduct();
    }

    @Override
    public String selectRiskLevelByProductCode(String productCode) {
        return productMapper.selectRiskLevelByProductCode(productCode);
    }

}
