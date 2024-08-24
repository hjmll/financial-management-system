package com.hs.settlement.service.impl;

import com.hs.settlement.domain.Settlement;
import com.hs.settlement.mapper.InitialMapper;
import com.hs.settlement.service.Initialization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class InitializationImpl implements Initialization {

    @Autowired
    private InitialMapper initialMapper;

    @Override
    public int updateSettlement(Settlement settlement) {

        return initialMapper.initialNav(settlement);
    }

    @Override
    public int updateNavs(String date, String nextdate) {
        List<Settlement> settlements = initialMapper.getSettlementsByDate(date); // 获取所有产品的原净值
        Random random = new Random();
        int updatedCount = 0;

        // 更新每一条产品净值
        for (Settlement settlement : settlements) {
            double originalNav = settlement.getNav();
            double newNav = originalNav * (0.9f + (random.nextFloat() * 0.4f)); // 随机值在0.9到1.2之间
            settlement.setNav(newNav);
            settlement.setDate(nextdate);
            try{
                updatedCount += updateSettlement(settlement); // 产品表更新新的净值，净值表触发插入
            }
            catch (Exception e){
                System.out.println(e.getMessage());
                return -1;
            }
        }
        return updatedCount;
    }


}
