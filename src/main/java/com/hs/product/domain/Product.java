package com.hs.product.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 产品表
 * @TableName product
 */
@TableName(value ="product")
@Data
public class Product implements Serializable {
    /**
     * 产品代码
     */
    private String productCode;

    /**
     * 产品当日净值
     */
    private BigDecimal productNetValue;

    /**
     * 净值日期
     */
    private Date netValueDate;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 风险等级
     */
    private String riskLevel;

    /**
     * 产品类型
     */
    private String productType;

    /**
     * 产品描述
     */
    private String description;

    /**
     * 产品状态
     */
    private String productStatus;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}