drop table if exists sms_coupon;

drop table if exists sms_coupon_history;

drop table if exists sms_coupon_spu_category_relation;

drop table if exists sms_coupon_spu_relation;

drop table if exists sms_home_adv;

drop table if exists sms_home_subject;

drop table if exists sms_home_subject_spu;

drop table if exists sms_member_price;

drop table if exists sms_seckill_promotion;

drop table if exists sms_seckill_session;

drop table if exists sms_seckill_sku_notice;

drop table if exists sms_seckill_sku_relation;

drop table if exists sms_sku_full_reduction;

drop table if exists sms_sku_ladder;

drop table if exists sms_spu_bounds;

/*==============================================================*/
/* Table: sms_coupon                                            */
/*==============================================================*/
create table sms_coupon
(
   id                   bigint not null auto_increment comment 'id',
   coupon_type          tinyint(1) comment 'coupon type [0->general gift; 1->member gift; 2->shopping gift; 3->registration gift]',
   coupon_img           varchar(2000) comment 'coupon image',
   coupon_name          varchar(100) comment 'coupon name',
   num                  int comment 'quantity',
   amount               decimal(18,4) comment 'amount',
   per_limit            int comment 'per-person claim limit',
   min_point            decimal(18,4) comment 'minimum spend threshold',
   start_time           datetime comment 'start time',
   end_time             datetime comment 'end time',
   use_type             tinyint(1) comment 'usage type [0->all products; 1->specific category; 2->specific product]',
   note                 varchar(200) comment 'note',
   publish_count        int(11) comment 'issued quantity',
   use_count            int(11) comment 'used quantity',
   receive_count        int(11) comment 'claimed quantity',
   enable_start_time    datetime comment 'claim start date',
   enable_end_time      datetime comment 'claim end date',
   code                 varchar(64) comment 'promo code',
   member_level         tinyint(1) comment 'claimable member level [0->all levels; other->corresponding level]',
   publish              tinyint(1) comment 'publish status [0-unpublished, 1-published]',
   primary key (id)
);

alter table sms_coupon comment 'coupon info';

/*==============================================================*/
/* Table: sms_coupon_history                                    */
/*==============================================================*/
create table sms_coupon_history
(
   id                   bigint not null auto_increment comment 'id',
   coupon_id            bigint comment 'coupon id',
   member_id            bigint comment 'member id',
   member_nick_name     varchar(64) comment 'member name',
   get_type             tinyint(1) comment 'acquisition type [0->admin granted; 1->self claimed]',
   create_time          datetime comment 'create time',
   use_type             tinyint(1) comment 'usage status [0->unused; 1->used; 2->expired]',
   use_time             datetime comment 'use time',
   order_id             bigint comment 'order id',
   order_sn             bigint comment 'order number',
   primary key (id)
);

alter table sms_coupon_history comment 'coupon claim history';

/*==============================================================*/
/* Table: sms_coupon_spu_category_relation                      */
/*==============================================================*/
create table sms_coupon_spu_category_relation
(
   id                   bigint not null auto_increment comment 'id',
   coupon_id            bigint comment 'coupon id',
   category_id          bigint comment 'product category id',
   category_name        varchar(64) comment 'product category name',
   primary key (id)
);

alter table sms_coupon_spu_category_relation comment 'coupon category relation';

/*==============================================================*/
/* Table: sms_coupon_spu_relation                               */
/*==============================================================*/
create table sms_coupon_spu_relation
(
   id                   bigint not null auto_increment comment 'id',
   coupon_id            bigint comment 'coupon id',
   spu_id               bigint comment 'spu_id',
   spu_name             varchar(255) comment 'spu_name',
   primary key (id)
);

alter table sms_coupon_spu_relation comment 'coupon product relation';

/*==============================================================*/
/* Table: sms_home_adv                                          */
/*==============================================================*/
create table sms_home_adv
(
   id                   bigint not null auto_increment comment 'id',
   name                 varchar(100) comment 'name',
   pic                  varchar(500) comment 'image url',
   start_time           datetime comment 'start time',
   end_time             datetime comment 'end time',
   status               tinyint(1) comment 'status',
   click_count          int comment 'click count',
   url                  varchar(500) comment 'ad detail url',
   note                 varchar(500) comment 'note',
   sort                 int comment 'sort',
   publisher_id         bigint comment 'publisher',
   auth_id              bigint comment 'reviewer',
   primary key (id)
);

alter table sms_home_adv comment 'home page carousel ad';

/*==============================================================*/
/* Table: sms_home_subject                                      */
/*==============================================================*/
create table sms_home_subject
(
   id                   bigint not null auto_increment comment 'id',
   name                 varchar(200) comment 'subject name',
   title                varchar(255) comment 'subject title',
   sub_title            varchar(255) comment 'subject subtitle',
   status               tinyint(1) comment 'display status',
   url                  varchar(500) comment 'detail url',
   sort                 int comment 'sort',
   img                  varchar(500) comment 'subject image url',
   primary key (id)
);

alter table sms_home_subject comment 'home page subject (each subject links to a new page showing subject product info)';

/*==============================================================*/
/* Table: sms_home_subject_spu                                  */
/*==============================================================*/
create table sms_home_subject_spu
(
   id                   bigint not null auto_increment comment 'id',
   name                 varchar(200) comment 'subject name',
   subject_id           bigint comment 'subject id',
   spu_id               bigint comment 'spu_id',
   sort                 int comment 'sort',
   primary key (id)
);

alter table sms_home_subject_spu comment 'subject product';

/*==============================================================*/
/* Table: sms_member_price                                      */
/*==============================================================*/
create table sms_member_price
(
   id                   bigint not null auto_increment comment 'id',
   sku_id               bigint comment 'sku_id',
   member_level_id      bigint comment 'member level id',
   member_level_name    varchar(100) comment 'member level name',
   member_price         decimal(18,4) comment 'member price',
   add_other            tinyint(1) comment 'stackable with other promotions [0-not stackable, 1-stackable]',
   primary key (id)
);

alter table sms_member_price comment 'product member price';

/*==============================================================*/
/* Table: sms_seckill_promotion                                 */
/*==============================================================*/
create table sms_seckill_promotion
(
   id                   bigint not null auto_increment comment 'id',
   title                varchar(255) comment 'promotion title',
   start_time           datetime comment 'start date',
   end_time             datetime comment 'end date',
   status               tinyint comment 'online/offline status',
   create_time          datetime comment 'create time',
   user_id              bigint comment 'creator',
   primary key (id)
);

alter table sms_seckill_promotion comment 'flash sale promotion';

/*==============================================================*/
/* Table: sms_seckill_session                                   */
/*==============================================================*/
create table sms_seckill_session
(
   id                   bigint not null auto_increment comment 'id',
   name                 varchar(200) comment 'session name',
   start_time           datetime comment 'daily start time',
   end_time             datetime comment 'daily end time',
   status               tinyint(1) comment 'enable status',
   create_time          datetime comment 'create time',
   primary key (id)
);

alter table sms_seckill_session comment 'flash sale session';

/*==============================================================*/
/* Table: sms_seckill_sku_notice                                */
/*==============================================================*/
create table sms_seckill_sku_notice
(
   id                   bigint not null auto_increment comment 'id',
   member_id            bigint comment 'member_id',
   sku_id               bigint comment 'sku_id',
   session_id           bigint comment 'session id',
   subcribe_time        datetime comment 'subscribe time',
   send_time            datetime comment 'send time',
   notice_type          tinyint(1) comment 'notification method [0-SMS, 1-email]',
   primary key (id)
);

alter table sms_seckill_sku_notice comment 'flash sale sku notification subscription';

/*==============================================================*/
/* Table: sms_seckill_sku_relation                              */
/*==============================================================*/
create table sms_seckill_sku_relation
(
   id                   bigint not null auto_increment comment 'id',
   promotion_id         bigint comment 'promotion id',
   promotion_session_id bigint comment 'session id',
   sku_id               bigint comment 'product id',
   seckill_price        decimal comment 'flash sale price',
   seckill_count        decimal comment 'flash sale total quantity',
   seckill_limit        decimal comment 'per-person purchase limit',
   seckill_sort         int comment 'sort',
   primary key (id)
);

alter table sms_seckill_sku_relation comment 'flash sale sku relation';

/*==============================================================*/
/* Table: sms_sku_full_reduction                                */
/*==============================================================*/
create table sms_sku_full_reduction
(
   id                   bigint not null auto_increment comment 'id',
   sku_id               bigint comment 'spu_id',
   full_price           decimal(18,4) comment 'spend threshold',
   reduce_price         decimal(18,4) comment 'reduction amount',
   add_other            tinyint(1) comment 'stackable with other promotions',
   primary key (id)
);

alter table sms_sku_full_reduction comment 'sku spend-save info';

/*==============================================================*/
/* Table: sms_sku_ladder                                        */
/*==============================================================*/
create table sms_sku_ladder
(
   id                   bigint not null auto_increment comment 'id',
   sku_id               bigint comment 'spu_id',
   full_count           int comment 'quantity threshold',
   discount             decimal(4,2) comment 'discount rate',
   price                decimal(18,4) comment 'discounted price',
   add_other            tinyint(1) comment 'stackable with other promotions [0-not stackable, 1-stackable]',
   primary key (id)
);

alter table sms_sku_ladder comment 'sku tiered price';

/*==============================================================*/
/* Table: sms_spu_bounds                                        */
/*==============================================================*/
create table sms_spu_bounds
(
   id                   bigint not null auto_increment comment 'id',
   spu_id               bigint,
   grow_bounds          decimal(18,4) comment 'growth points bounds',
   buy_bounds           decimal(18,4) comment 'shopping points bounds',
   work                 tinyint(1) comment 'promotion effect flags [4-bit, right to left: 0-no promo, growth points granted; 1-no promo, shopping points granted; 2-with promo, growth points granted; 3-with promo, shopping points granted; bit 0=not granted, 1=granted]',
   primary key (id)
);

alter table sms_spu_bounds comment 'spu points bounds settings';
