package com.hs.settlement.service;

public interface Halt {
    // 查询待确认申购数
    int getSubCount(String date);

    // 查询待确认赎回数
    int getRedCount(String date);

}
