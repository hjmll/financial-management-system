-- auto-generated definition
create table product_quotation
(
    product_code     varchar(20)    not null comment '产品代码',
    product_net_value decimal(10, 4) not null comment '产品当日净值',
    t_date           date           not null comment '交易日期',
    primary key (product_code, t_date),
    constraint product_quotation_fk_1
        foreign key (product_code) references product (product_code)
)
    comment '产品行情表';

--建立索引
ALTER TABLE product_quotation
    ADD UNIQUE INDEX unique_product_code_t_date (product_code, t_date);

	
	