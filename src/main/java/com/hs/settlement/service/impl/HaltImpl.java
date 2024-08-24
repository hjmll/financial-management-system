package com.hs.settlement.service.impl;

import com.hs.settlement.mapper.HaltMapper;
import com.hs.settlement.service.Halt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HaltImpl implements Halt {

    @Autowired
    private HaltMapper haltMapper;

    @Override
    public int getSubCount(String date){

        return haltMapper.countSub(date);
    }

    @Override
    public int getRedCount(String date){

        return haltMapper.countRed(date);
    }

}
