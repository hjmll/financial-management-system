package com.hs.redemption.service.impl;

import com.hs.redemption.domain.Customer;
import com.hs.redemption.domain.Holding;
import com.hs.redemption.mapper.RedCustomerMapper;
import com.hs.redemption.mapper.RedHoldingMapper;
import com.hs.redemption.mapper.RedProductMapper;
import com.hs.redemption.service.CustomerService;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class RedCustomerServiceImpl implements CustomerService {

    @Autowired
    RedCustomerMapper customerMapper;

    @Autowired
    RedHoldingMapper holdingMapper;

    @Autowired
    RedProductMapper productMapper;

    @Override
    public List<Customer> selectCustomerByFundAccountAndName(String fundAccount, String name) {
        return customerMapper.selectCustomerByFundAccountAndName(fundAccount, name);
    }

    @Override
    @Transactional
    public List<Holding> selectHoldingByFundAccount(String fundAccount) {
        List<String> cardNumbers = holdingMapper.selectCardNumberByFundAccount(fundAccount);
        List<Holding> holdings = new ArrayList<>();
        for (String cardNumber : cardNumbers) {
            List<Holding> holdingPerCard = holdingMapper.selectHoldingByCardNumber(cardNumber);
            for (Holding holding : holdingPerCard) {
                if(holding.getShares() > 0){
                    holding.setFundAccount(fundAccount);
                    holding.setProductName(productMapper.selectProductNameByCode(holding.getProductCode()));
                    holdings.add(holding);
                }
            }
        }
        return holdings;
    }

    @Override
    public String selectCustomerNameByFundAccount(String fundAccount) {
        return customerMapper.selectCustomerNameByFundAccount(fundAccount);
    }

    @Override
    public String selectFundAccountByCardNumber(String cardNumber) {
        return customerMapper.selectFundAccountByCardNumber(cardNumber);
    }
}
