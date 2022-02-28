/*基于电商交易场景（用户、商品、订单），设计一套简单的表结构，提交 DDL 的 SQL 文件到 Github*/


/**
  用户表
 **/
create table user
(
    id          varchar(50) null comment '用户id',
    name        char(50)    null comment '姓名',
    sex         char(50)    null comment '性别',
    create_time timestamp   not null comment '创建时间'
);

/**
  商品表
 */
create table goods
(
    id          varchar(50) null comment '商品id',
    name        char(50)    null comment '商品名称',
    create_time timestamp   not null comment '创建时间',
    update_time timestamp   not null comment '修改时间'

);
/**
  订单表
 */
-- auto-generated definition
create table order_info
(
    id          varchar(50) not null comment '订单id',
    g_id        varchar(50) not null comment '商品id',
    u_id        varchar(50) not null comment '用户id',
    status      varchar(50) not null comment '订单状态  1.已取消 2.已完成',
    pay_status  varchar(50) not null comment '支付状态 1.未支付 2.支付中 3.已支付 ',
    money       mediumtext  null comment '金额',
    create_time long   not null comment '创建时间',
    update_time long   not null comment '修改时间'
);

