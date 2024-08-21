-- auto-generated definition
create table product_quotation
(
    productCode     varchar(20)    not null comment '产品代码',
    productNetValue decimal(10, 4) not null comment '产品当日净值',
    tDate           date           not null comment '交易日期',
    primary key (productCode, tDate),
    constraint product_quotation_fk_1
        foreign key (productCode) references product (productCode)
)
    comment '产品行情表';

--建立索引
ALTER TABLE product_quotation
    ADD UNIQUE INDEX unique_product_code_t_date (productCode, tDate);
