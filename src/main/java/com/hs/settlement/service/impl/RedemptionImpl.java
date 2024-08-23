package com.hs.settlement.service.impl;

import com.hs.settlement.mapper.RedemptionMapper;
import com.hs.settlement.service.Redemption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RedemptionImpl implements Redemption {

    @Autowired
    private RedemptionMapper redemptionMapper;

    @Override
    public int countTodayTransaction(String date) {
        return redemptionMapper.countTodayTransaction(date);
    }

}
