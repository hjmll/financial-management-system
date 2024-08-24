package com.hs.product.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 产品行情表
 * @TableName product_quotation
 */
@TableName(value ="product_quotation")
@Data
public class ProductQuotation implements Serializable {
    /**
     * 产品代码
     */
    @TableId
    private String productCode;

    /**
     * 产品当日净值
     */
    private BigDecimal productNetValue;

    /**
     * 
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date tDate;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}