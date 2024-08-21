package com.hs.redemption.service.impl;

import com.hs.redemption.domain.Redemption;
import com.hs.redemption.mapper.RedemptionMapper;
import com.hs.redemption.service.RedemptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RedemptionServiceImpl implements RedemptionService {

    @Autowired
    RedemptionMapper redemptionMapper;

    @Override
    @Transactional
    public void redeem(Redemption redemption) {
        String id = redemption.getRequestNumber();
        String productCode = redemption.getProductCode();
        String cardNumber = redemption.getCardNumber();
        double shares = redemption.getShares();
        String date = redemption.getDate();
        redemptionMapper.insertRequest(id, productCode, cardNumber, "赎回",shares,date,"待确认");
        redemptionMapper.updateHolding(cardNumber,shares,productCode,date);
    }
}
