drop table if exists oms_order;

drop table if exists oms_order_item;

drop table if exists oms_order_operate_history;

drop table if exists oms_order_return_apply;

drop table if exists oms_order_return_reason;

drop table if exists oms_order_setting;

drop table if exists oms_payment_info;

drop table if exists oms_refund_info;

/*==============================================================*/
/* Table: oms_order                                             */
/*==============================================================*/
create table oms_order
(
   id                   bigint not null auto_increment comment 'id',
   member_id            bigint comment 'member_id',
   order_sn             char(32) comment 'order number',
   coupon_id            bigint comment 'coupon used',
   create_time          datetime comment 'create_time',
   member_username      varchar(200) comment 'username',
   total_amount         decimal(18,4) comment 'order total amount',
   pay_amount           decimal(18,4) comment 'amount payable',
   freight_amount       decimal(18,4) comment 'freight amount',
   promotion_amount     decimal(18,4) comment 'promotion discount amount (promo price, spend-save, tiered price)',
   integration_amount   decimal(18,4) comment 'points deduction amount',
   coupon_amount        decimal(18,4) comment 'coupon deduction amount',
   discount_amount      decimal(18,4) comment 'admin-adjusted discount amount',
   pay_type             tinyint comment 'payment method [1->Alipay; 2->WeChat; 3->UnionPay; 4->COD]',
   source_type          tinyint comment 'order source [0->PC; 1->App]',
   status               tinyint comment 'order status [0->pending payment; 1->pending shipment; 2->shipped; 3->completed; 4->closed; 5->invalid]',
   delivery_company     varchar(64) comment 'shipping carrier (delivery method)',
   delivery_sn          varchar(64) comment 'tracking number',
   auto_confirm_day     int comment 'auto-confirm period (days)',
   integration          int comment 'points to be earned',
   growth               int comment 'growth points to be earned',
   bill_type            tinyint comment 'invoice type [0->no invoice; 1->e-invoice; 2->paper invoice]',
   bill_header          varchar(255) comment 'invoice title',
   bill_content         varchar(255) comment 'invoice content',
   bill_receiver_phone  varchar(32) comment 'invoice recipient phone',
   bill_receiver_email  varchar(64) comment 'invoice recipient email',
   receiver_name        varchar(100) comment 'recipient name',
   receiver_phone       varchar(32) comment 'recipient phone',
   receiver_post_code   varchar(32) comment 'recipient postal code',
   receiver_province    varchar(32) comment 'province/municipality',
   receiver_city        varchar(32) comment 'city',
   receiver_region      varchar(32) comment 'district',
   receiver_detail_address varchar(200) comment 'detailed address',
   note                 varchar(500) comment 'order note',
   confirm_status       tinyint comment 'receipt confirmation status [0->unconfirmed; 1->confirmed]',
   delete_status        tinyint comment 'delete status [0->not deleted; 1->deleted]',
   use_integration      int comment 'points used when placing order',
   payment_time         datetime comment 'payment time',
   delivery_time        datetime comment 'shipment time',
   receive_time         datetime comment 'receipt confirmation time',
   comment_time         datetime comment 'review time',
   modify_time          datetime comment 'modification time',
   primary key (id)
);

alter table oms_order comment 'order';

/*==============================================================*/
/* Table: oms_order_item                                        */
/*==============================================================*/
create table oms_order_item
(
   id                   bigint not null auto_increment comment 'id',
   order_id             bigint comment 'order_id',
   order_sn             char(32) comment 'order_sn',
   spu_id               bigint comment 'spu_id',
   spu_name             varchar(255) comment 'spu_name',
   spu_pic              varchar(500) comment 'spu_pic',
   spu_brand            varchar(200) comment 'brand',
   category_id          bigint comment 'product category id',
   sku_id               bigint comment 'product sku id',
   sku_name             varchar(255) comment 'product sku name',
   sku_pic              varchar(500) comment 'product sku image',
   sku_price            decimal(18,4) comment 'product sku price',
   sku_quantity         int comment 'product purchase quantity',
   sku_attrs_vals       varchar(500) comment 'product sale attribute combination (JSON)',
   promotion_amount     decimal(18,4) comment 'product promotion breakdown amount',
   coupon_amount        decimal(18,4) comment 'coupon discount breakdown amount',
   integration_amount   decimal(18,4) comment 'points discount breakdown amount',
   real_amount          decimal(18,4) comment 'product amount after discount breakdown',
   gift_integration     int comment 'bonus points',
   gift_growth          int comment 'bonus growth points',
   primary key (id)
);

alter table oms_order_item comment 'order item';

/*==============================================================*/
/* Table: oms_order_operate_history                             */
/*==============================================================*/
create table oms_order_operate_history
(
   id                   bigint not null auto_increment comment 'id',
   order_id             bigint comment 'order id',
   operate_man          varchar(100) comment 'operator [user; system; admin]',
   create_time          datetime comment 'operation time',
   order_status         tinyint comment 'order status [0->pending payment; 1->pending shipment; 2->shipped; 3->completed; 4->closed; 5->invalid]',
   note                 varchar(500) comment 'note',
   primary key (id)
);

alter table oms_order_operate_history comment 'order operation history';

/*==============================================================*/
/* Table: oms_order_return_apply                                */
/*==============================================================*/
create table oms_order_return_apply
(
   id                   bigint not null auto_increment comment 'id',
   order_id             bigint comment 'order_id',
   sku_id               bigint comment 'return item id',
   order_sn             char(32) comment 'order number',
   create_time          datetime comment 'application time',
   member_username      varchar(64) comment 'member username',
   return_amount        decimal(18,4) comment 'refund amount',
   return_name          varchar(100) comment 'returner name',
   return_phone         varchar(20) comment 'returner phone',
   status               tinyint(1) comment 'status [0->pending; 1->returning; 2->completed; 3->rejected]',
   handle_time          datetime comment 'handling time',
   sku_img              varchar(500) comment 'product image',
   sku_name             varchar(200) comment 'product name',
   sku_brand            varchar(200) comment 'product brand',
   sku_attrs_vals       varchar(500) comment 'product sale attributes (JSON)',
   sku_count            int comment 'return quantity',
   sku_price            decimal(18,4) comment 'product unit price',
   sku_real_price       decimal(18,4) comment 'actual paid unit price',
   reason               varchar(200) comment 'reason',
   description         varchar(500) comment 'description',
   desc_pics            varchar(2000) comment 'proof images, comma-separated',
   handle_note          varchar(500) comment 'handling note',
   handle_man           varchar(200) comment 'handler',
   receive_man          varchar(100) comment 'receiver',
   receive_time         datetime comment 'receive time',
   receive_note         varchar(500) comment 'receive note',
   receive_phone        varchar(20) comment 'receive phone',
   company_address      varchar(500) comment 'company receive address',
   primary key (id)
);

alter table oms_order_return_apply comment 'order return application';

/*==============================================================*/
/* Table: oms_order_return_reason                               */
/*==============================================================*/
create table oms_order_return_reason
(
   id                   bigint not null auto_increment comment 'id',
   name                 varchar(200) comment 'return reason name',
   sort                 int comment 'sort',
   status               tinyint(1) comment 'enable status',
   create_time          datetime comment 'create_time',
   primary key (id)
);

alter table oms_order_return_reason comment 'order return reason';

/*==============================================================*/
/* Table: oms_order_setting                                     */
/*==============================================================*/
create table oms_order_setting
(
   id                   bigint not null auto_increment comment 'id',
   flash_order_overtime int comment 'flash sale order timeout close time (minutes)',
   normal_order_overtime int comment 'normal order timeout time (minutes)',
   confirm_overtime     int comment 'auto-confirm receipt after shipment (days)',
   finish_overtime      int comment 'auto-complete transaction period, no returns allowed (days)',
   comment_overtime     int comment 'auto-positive-review period after order completion (days)',
   member_level         tinyint(2) comment 'member level [0-all levels; other-corresponding member level]',
   primary key (id)
);

alter table oms_order_setting comment 'order settings';

/*==============================================================*/
/* Table: oms_payment_info                                      */
/*==============================================================*/
create table oms_payment_info
(
   id                   bigint not null auto_increment comment 'id',
   order_sn             char(32) comment 'order number (external business number)',
   order_id             bigint comment 'order id',
   alipay_trade_no      varchar(50) comment 'Alipay transaction number',
   total_amount         decimal(18,4) comment 'total payment amount',
   subject              varchar(200) comment 'transaction content',
   payment_status       varchar(20) comment 'payment status',
   create_time          datetime comment 'create time',
   confirm_time         datetime comment 'confirm time',
   callback_content     varchar(4000) comment 'callback content',
   callback_time        datetime comment 'callback time',
   primary key (id)
);

alter table oms_payment_info comment 'payment info';

/*==============================================================*/
/* Table: oms_refund_info                                       */
/*==============================================================*/
create table oms_refund_info
(
   id                   bigint not null auto_increment comment 'id',
   order_return_id      bigint comment 'refund order',
   refund               decimal(18,4) comment 'refund amount',
   refund_sn            varchar(64) comment 'refund transaction number',
   refund_status        tinyint(1) comment 'refund status',
   refund_channel       tinyint comment 'refund channel [1-Alipay, 2-WeChat, 3-UnionPay, 4-wire transfer]',
   refund_content       varchar(5000),
   primary key (id)
);

alter table oms_refund_info comment 'refund info';
