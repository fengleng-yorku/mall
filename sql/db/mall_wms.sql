drop table if exists wms_purchase;

drop table if exists wms_purchase_detail;

drop table if exists wms_ware_info;

drop table if exists wms_ware_order_task;

drop table if exists wms_ware_order_task_detail;

drop table if exists wms_ware_sku;

/*==============================================================*/
/* Table: wms_purchase                                          */
/*==============================================================*/
create table wms_purchase
(
   id                   bigint not null auto_increment comment 'purchase order id',
   assignee_id          bigint comment 'purchaser id',
   assignee_name        varchar(255) comment 'purchaser name',
   phone                char(13) comment 'contact',
   priority             int(4) comment 'priority',
   status               int(4) comment 'status',
   ware_id              bigint comment 'warehouse id',
   amount               decimal(18,4) comment 'total amount',
   create_time          datetime comment 'create date',
   update_time          datetime comment 'update date',
   primary key (id)
);

alter table wms_purchase comment 'purchase info';

/*==============================================================*/
/* Table: wms_purchase_detail                                   */
/*==============================================================*/
create table wms_purchase_detail
(
   id                   bigint not null auto_increment,
   purchase_id          bigint comment 'purchase order id',
   sku_id               bigint comment 'purchase sku id',
   sku_num              int comment 'purchase quantity',
   sku_price            decimal(18,4) comment 'purchase amount',
   ware_id              bigint comment 'warehouse id',
   status               int comment 'status [0-new, 1-assigned, 2-purchasing, 3-completed, 4-failed]',
   primary key (id)
);

/*==============================================================*/
/* Table: wms_ware_info                                         */
/*==============================================================*/
create table wms_ware_info
(
   id                   bigint not null auto_increment comment 'id',
   name                 varchar(255) comment 'warehouse name',
   address              varchar(255) comment 'warehouse address',
   areacode             varchar(20) comment 'area code',
   primary key (id)
);

alter table wms_ware_info comment 'warehouse info';

/*==============================================================*/
/* Table: wms_ware_order_task                                   */
/*==============================================================*/
create table wms_ware_order_task
(
   id                   bigint not null auto_increment comment 'id',
   order_id             bigint comment 'order_id',
   order_sn             varchar(255) comment 'order_sn',
   consignee            varchar(100) comment 'consignee',
   consignee_tel        char(15) comment 'consignee phone',
   delivery_address     varchar(500) comment 'delivery address',
   order_comment        varchar(200) comment 'order note',
   payment_way          tinyint(1) comment 'payment method [1:online payment, 2:COD]',
   task_status          tinyint(2) comment 'task status',
   order_body           varchar(255) comment 'order description',
   tracking_no          char(30) comment 'tracking number',
   create_time          datetime comment 'create_time',
   ware_id              bigint comment 'warehouse id',
   task_comment         varchar(500) comment 'task note',
   primary key (id)
);

alter table wms_ware_order_task comment 'warehouse order task';

/*==============================================================*/
/* Table: wms_ware_order_task_detail                            */
/*==============================================================*/
create table wms_ware_order_task_detail
(
   id                   bigint not null auto_increment comment 'id',
   sku_id               bigint comment 'sku_id',
   sku_name             varchar(255) comment 'sku_name',
   sku_num              int comment 'purchase quantity',
   task_id              bigint comment 'task id',
   primary key (id)
);

alter table wms_ware_order_task_detail comment 'warehouse order task detail';

/*==============================================================*/
/* Table: wms_ware_sku                                          */
/*==============================================================*/
create table wms_ware_sku
(
   id                   bigint not null auto_increment comment 'id',
   sku_id               bigint comment 'sku_id',
   ware_id              bigint comment 'warehouse id',
   stock                int comment 'stock quantity',
   sku_name             varchar(200) comment 'sku_name',
   stock_locked         int comment 'locked stock',
   primary key (id)
);

alter table wms_ware_sku comment 'product stock';
