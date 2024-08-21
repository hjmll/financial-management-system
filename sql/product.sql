-- auto-generated definition
create table product
(
    productCode     varchar(20)    not null comment '产品代码'
        primary key,
    productNetValue decimal(10, 4) null comment '产品当日净值',
    netValueDate    date           null comment '净值日期',
    productName     varchar(50)    null comment '产品名称',
    riskLevel       varchar(20)    null comment '风险等级',
    productType     varchar(20)    null comment '产品类型',
    description     text           null comment '产品描述',
    productStatus   char(255)      null comment '产品状态'
)
    comment '产品表';

create index idx_productCode
    on product (productCode);


	
	

