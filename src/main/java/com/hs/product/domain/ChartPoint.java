package com.hs.product.domain;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 图标数据点类
 */
public class ChartPoint {
    private Date date;
    private BigDecimal value;

    public ChartPoint(Date date, BigDecimal value) {
        this.date = date;
        this.value = value;
    }

    // Getters and setters
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ChartPoint{" +
                "date=" + date +
                ", value=" + value +
                '}';
    }
}