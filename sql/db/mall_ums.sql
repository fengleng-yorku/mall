drop table if exists ums_growth_change_history;

drop table if exists ums_integration_change_history;

drop table if exists ums_member;

drop table if exists ums_member_collect_spu;

drop table if exists ums_member_collect_subject;

drop table if exists ums_member_level;

drop table if exists ums_member_login_log;

drop table if exists ums_member_receive_address;

drop table if exists ums_member_statistics_info;

/*==============================================================*/
/* Table: ums_growth_change_history                             */
/*==============================================================*/
create table ums_growth_change_history
(
   id                   bigint not null auto_increment comment 'id',
   member_id            bigint comment 'member_id',
   create_time          datetime comment 'create_time',
   change_count         int comment 'change value (positive or negative)',
   note                 varchar(0) comment 'note',
   source_type          tinyint comment 'source type [0-shopping, 1-admin adjustment]',
   primary key (id)
);

alter table ums_growth_change_history comment 'growth points change history';

/*==============================================================*/
/* Table: ums_integration_change_history                        */
/*==============================================================*/
create table ums_integration_change_history
(
   id                   bigint not null auto_increment comment 'id',
   member_id            bigint comment 'member_id',
   create_time          datetime comment 'create_time',
   change_count         int comment 'change value',
   note                 varchar(255) comment 'note',
   source_tyoe          tinyint comment 'source [0->shopping; 1->admin adjustment; 2->promotion]',
   primary key (id)
);

alter table ums_integration_change_history comment 'points change history';

/*==============================================================*/
/* Table: ums_member                                            */
/*==============================================================*/
create table ums_member
(
   id                   bigint not null auto_increment comment 'id',
   level_id             bigint comment 'member level id',
   username             char(64) comment 'username',
   password             varchar(64) comment 'password',
   nickname             varchar(64) comment 'nickname',
   mobile               varchar(20) comment 'mobile number',
   email                varchar(64) comment 'email',
   header               varchar(500) comment 'avatar',
   gender               tinyint comment 'gender',
   birth                date comment 'birthday',
   city                 varchar(500) comment 'city',
   job                  varchar(255) comment 'occupation',
   sign                 varchar(255) comment 'personal signature',
   source_type          tinyint comment 'registration source',
   integration          int comment 'points',
   growth               int comment 'growth points',
   status               tinyint comment 'enable status',
   create_time          datetime comment 'registration time',
   primary key (id)
);

alter table ums_member comment 'member';

/*==============================================================*/
/* Table: ums_member_collect_spu                                */
/*==============================================================*/
create table ums_member_collect_spu
(
   id                   bigint not null comment 'id',
   member_id            bigint comment 'member id',
   spu_id               bigint comment 'spu_id',
   spu_name             varchar(500) comment 'spu_name',
   spu_img              varchar(500) comment 'spu_img',
   create_time          datetime comment 'create_time',
   primary key (id)
);

alter table ums_member_collect_spu comment 'member collected products';

/*==============================================================*/
/* Table: ums_member_collect_subject                            */
/*==============================================================*/
create table ums_member_collect_subject
(
   id                   bigint not null auto_increment comment 'id',
   subject_id           bigint comment 'subject_id',
   subject_name         varchar(255) comment 'subject_name',
   subject_img          varchar(500) comment 'subject_img',
   subject_urll         varchar(500) comment 'activity url',
   primary key (id)
);

alter table ums_member_collect_subject comment 'member collected subjects';

/*==============================================================*/
/* Table: ums_member_level                                      */
/*==============================================================*/
create table ums_member_level
(
   id                   bigint not null auto_increment comment 'id',
   name                 varchar(100) comment 'level name',
   growth_point         int comment 'growth points required for this level',
   default_status       tinyint comment 'is default level [0->no; 1->yes]',
   free_freight_point   decimal(18,4) comment 'free shipping threshold',
   comment_growth_point int comment 'growth points earned per review',
   priviledge_free_freight tinyint comment 'free shipping privilege',
   priviledge_member_price tinyint comment 'member price privilege',
   priviledge_birthday  tinyint comment 'birthday privilege',
   note                 varchar(255) comment 'note',
   primary key (id)
);

alter table ums_member_level comment 'member level';

/*==============================================================*/
/* Table: ums_member_login_log                                  */
/*==============================================================*/
create table ums_member_login_log
(
   id                   bigint not null auto_increment comment 'id',
   member_id            bigint comment 'member_id',
   create_time          datetime comment 'create time',
   ip                   varchar(64) comment 'ip',
   city                 varchar(64) comment 'city',
   login_type           tinyint(1) comment 'login type [1-web, 2-app]',
   primary key (id)
);

alter table ums_member_login_log comment 'member login log';

/*==============================================================*/
/* Table: ums_member_receive_address                            */
/*==============================================================*/
create table ums_member_receive_address
(
   id                   bigint not null auto_increment comment 'id',
   member_id            bigint comment 'member_id',
   name                 varchar(255) comment 'recipient name',
   phone                varchar(64) comment 'phone',
   post_code            varchar(64) comment 'postal code',
   province             varchar(100) comment 'province/municipality',
   city                 varchar(100) comment 'city',
   region               varchar(100) comment 'district',
   detail_address       varchar(255) comment 'detailed address (street)',
   areacode             varchar(15) comment 'region code',
   default_status       tinyint(1) comment 'is default',
   primary key (id)
);

alter table ums_member_receive_address comment 'member receive address';

/*==============================================================*/
/* Table: ums_member_statistics_info                            */
/*==============================================================*/
create table ums_member_statistics_info
(
   id                   bigint not null auto_increment comment 'id',
   member_id            bigint comment 'member id',
   consume_amount       decimal(18,4) comment 'total consumption amount',
   coupon_amount        decimal(18,4) comment 'total discount amount',
   order_count          int comment 'order count',
   coupon_count         int comment 'coupon count',
   comment_count        int comment 'comment count',
   return_order_count   int comment 'return order count',
   login_count          int comment 'login count',
   attend_count         int comment 'follow count',
   fans_count           int comment 'fans count',
   collect_product_count int comment 'collected product count',
   collect_subject_count int comment 'collected subject count',
   collect_comment_count int comment 'collected comment count',
   invite_friend_count  int comment 'invited friend count',
   primary key (id)
);

alter table ums_member_statistics_info comment 'member statistics info';
