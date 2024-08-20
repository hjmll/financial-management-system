package com.hs.subscription.service.impl;

import com.hs.subscription.domain.PreTrade;
import com.hs.subscription.mapper.ProductMapper;
import com.hs.subscription.mapper.SubscriptionMapper;
import com.hs.subscription.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<String> selectProductsByFundAccount(String fundAccount) {
        List<String> list = productMapper.selectProductsByFundAccount(fundAccount);
        List<String> productCodeList = new ArrayList<>();
        for (String productCode: list) {
            if(productMapper.findProductStatus(productCode).equals("激活"))
                productCodeList.add(productCode);
        }
        return productCodeList;
    }

}
