package com.hs.product.mapper;

import com.hs.product.domain.Product;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author hjm
* @description 针对表【product(产品表)】的数据库操作Mapper
* @createDate 2024-08-19 23:21:31
* @Entity generator.domain.Product
*/
public interface ProductMapper extends BaseMapper<Product> {
    //查询所有
    List<Product> queryProductInfo();

}




