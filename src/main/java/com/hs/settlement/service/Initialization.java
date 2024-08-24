package com.hs.settlement.service;
import com.hs.settlement.domain.Settlement;

import java.util.List;

public interface Initialization {
    // 更新单条产品净值数据
    int updateSettlement(Settlement settlement);

    // 获取产品净值列表
    List<Settlement> getQuotation();
    // 实现日初始化
    // 数据库提取出所有产品的原来净值，计算，插入当前日期下所有产品的新的净值
    int updateNavs(String date, String nextdate);

}
