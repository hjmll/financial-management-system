-- auto-generated definition
create table product
(
    product_code      varchar(20)    not null comment '产品代码'
        primary key,
    product_net_value decimal(10, 4) null comment '产品当日净值',
    net_value_date    date           null comment '净值日期',
    product_name      varchar(50)    null comment '产品名称',
    risk_level        varchar(20)    null comment '风险等级',
    product_type      varchar(20)    null comment '产品类型',
    description       text           null comment '产品描述',
    product_status    char(255)      null comment '产品状态'
)
    comment '产品表';
	
--建立索引
create index idx_product_code
    on product (product_code);
