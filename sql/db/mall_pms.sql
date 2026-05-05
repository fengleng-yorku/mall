drop table if exists pms_attr;

drop table if exists pms_attr_attrgroup_relation;

drop table if exists pms_attr_group;

drop table if exists pms_brand;

drop table if exists pms_category;

drop table if exists pms_category_brand_relation;

drop table if exists pms_comment_replay;

drop table if exists pms_product_attr_value;

drop table if exists pms_sku_images;

drop table if exists pms_sku_info;

drop table if exists pms_sku_sale_attr_value;

drop table if exists pms_spu_comment;

drop table if exists pms_spu_images;

drop table if exists pms_spu_info;

drop table if exists pms_spu_info_desc;

/*==============================================================*/
/* Table: pms_attr                                              */
/*==============================================================*/
create table pms_attr
(
   attr_id              bigint not null auto_increment comment 'attribute id',
   attr_name            char(30) comment 'attribute name',
   search_type          tinyint comment 'searchable [0-no, 1-yes]',
  `value_type` tinyint(4) DEFAULT NULL COMMENT 'value type [0-single value, 1-multiple values]',
  `icon` varchar(255) DEFAULT NULL COMMENT 'attribute icon',
  `value_select` char(255) DEFAULT NULL COMMENT 'selectable value list [comma-separated]',
  `attr_type` tinyint(4) DEFAULT NULL COMMENT 'attribute type [0-sale attr, 1-base attr, 2-both]',
   enable               bigint comment 'enable status [0-disabled, 1-enabled]',
   catelog_id           bigint comment 'category',
   show_desc            tinyint comment 'quick display [show in description: 0-no, 1-yes]; still adjustable in SKU',
   primary key (attr_id)
);

alter table pms_attr comment 'product attribute';

/*==============================================================*/
/* Table: pms_attr_attrgroup_relation                           */
/*==============================================================*/
create table pms_attr_attrgroup_relation
(
   id                   bigint not null auto_increment comment 'id',
   attr_id              bigint comment 'attribute id',
   attr_group_id        bigint comment 'attribute group id',
   attr_sort            int comment 'sort within attribute group',
   primary key (id)
);

alter table pms_attr_attrgroup_relation comment 'attribute and attribute group relation';

/*==============================================================*/
/* Table: pms_attr_group                                        */
/*==============================================================*/
create table pms_attr_group
(
   attr_group_id        bigint not null auto_increment comment 'group id',
   attr_group_name      char(20) comment 'group name',
   sort                 int comment 'sort',
   descript             varchar(255) comment 'description',
   icon                 varchar(255) comment 'group icon',
   catelog_id           bigint comment 'category id',
   primary key (attr_group_id)
);

alter table pms_attr_group comment 'attribute group';

/*==============================================================*/
/* Table: pms_brand                                             */
/*==============================================================*/
create table pms_brand
(
   brand_id             bigint not null auto_increment comment 'brand id',
   name                 char(50) comment 'brand name',
   logo                 varchar(2000) comment 'brand logo url',
   descript             longtext comment 'description',
   show_status          tinyint comment 'display status [0-hidden; 1-visible]',
   first_letter         char(1) comment 'first letter for search',
   sort                 int comment 'sort',
   primary key (brand_id)
);

alter table pms_brand comment 'brand';

/*==============================================================*/
/* Table: pms_category                                          */
/*==============================================================*/
create table pms_category
(
   cat_id               bigint not null auto_increment comment 'category id',
   name                 char(50) comment 'category name',
   parent_cid           bigint comment 'parent category id',
   cat_level            int comment 'level',
   show_status          tinyint comment 'display status [0-hidden, 1-visible]',
   sort                 int comment 'sort',
   icon                 char(255) comment 'icon url',
   product_unit         char(50) comment 'unit of measure',
   product_count        int comment 'product count',
   primary key (cat_id)
);

alter table pms_category comment 'product three-level category';

/*==============================================================*/
/* Table: pms_category_brand_relation                           */
/*==============================================================*/
create table pms_category_brand_relation
(
   id                   bigint not null auto_increment,
   brand_id             bigint comment 'brand id',
   catelog_id           bigint comment 'category id',
   brand_name           varchar(255),
   catelog_name         varchar(255),
   primary key (id)
);

alter table pms_category_brand_relation comment 'brand category relation';

/*==============================================================*/
/* Table: pms_comment_replay                                    */
/*==============================================================*/
create table pms_comment_replay
(
   id                   bigint not null auto_increment comment 'id',
   comment_id           bigint comment 'comment id',
   reply_id             bigint comment 'reply id',
   primary key (id)
);

alter table pms_comment_replay comment 'product comment reply relation';

/*==============================================================*/
/* Table: pms_product_attr_value                                */
/*==============================================================*/
create table pms_product_attr_value
(
   id                   bigint not null auto_increment comment 'id',
   spu_id               bigint comment 'product id',
   attr_id              bigint comment 'attribute id',
   attr_name            varchar(200) comment 'attribute name',
   attr_value           varchar(200) comment 'attribute value',
   attr_sort            int comment 'sort order',
   quick_show           tinyint comment 'quick display [show in description: 0-no, 1-yes]',
   primary key (id)
);

alter table pms_product_attr_value comment 'spu attribute value';

/*==============================================================*/
/* Table: pms_sku_images                                        */
/*==============================================================*/
create table pms_sku_images
(
   id                   bigint not null auto_increment comment 'id',
   sku_id               bigint comment 'sku_id',
   img_url              varchar(255) comment 'image url',
   img_sort             int comment 'sort',
   default_img          int comment 'default image [0-not default, 1-default]',
   primary key (id)
);

alter table pms_sku_images comment 'sku image';

/*==============================================================*/
/* Table: pms_sku_info                                          */
/*==============================================================*/
create table pms_sku_info
(
   sku_id               bigint not null auto_increment comment 'skuId',
   spu_id               bigint comment 'spuId',
   sku_name             varchar(255) comment 'sku name',
   sku_desc             varchar(2000) comment 'sku description',
   catalog_id           bigint comment 'category id',
   brand_id             bigint comment 'brand id',
   sku_default_img      varchar(255) comment 'default image',
   sku_title            varchar(255) comment 'title',
   sku_subtitle         varchar(2000) comment 'subtitle',
   price                decimal(18,4) comment 'price',
   sale_count           bigint comment 'sales count',
   primary key (sku_id)
);

alter table pms_sku_info comment 'sku info';

/*==============================================================*/
/* Table: pms_sku_sale_attr_value                               */
/*==============================================================*/
create table pms_sku_sale_attr_value
(
   id                   bigint not null auto_increment comment 'id',
   sku_id               bigint comment 'sku_id',
   attr_id              bigint comment 'attr_id',
   attr_name            varchar(200) comment 'sale attribute name',
   attr_value           varchar(200) comment 'sale attribute value',
   attr_sort            int comment 'sort order',
   primary key (id)
);

alter table pms_sku_sale_attr_value comment 'sku sale attribute and value';

/*==============================================================*/
/* Table: pms_spu_comment                                       */
/*==============================================================*/
create table pms_spu_comment
(
   id                   bigint not null auto_increment comment 'id',
   sku_id               bigint comment 'sku_id',
   spu_id               bigint comment 'spu_id',
   spu_name             varchar(255) comment 'product name',
   member_nick_name     varchar(255) comment 'member nickname',
   star                 tinyint(1) comment 'star rating',
   member_ip            varchar(64) comment 'member ip',
   create_time          datetime comment 'create time',
   show_status          tinyint(1) comment 'display status [0-hidden, 1-visible]',
   spu_attributes       varchar(255) comment 'attribute combination at purchase',
   likes_count          int comment 'likes count',
   reply_count          int comment 'reply count',
   resources            varchar(1000) comment 'comment media (images/videos) [JSON: [{type:file_type, url:resource_url}]]',
   content              text comment 'content',
   member_icon          varchar(255) comment 'member avatar',
   comment_type         tinyint comment 'comment type [0-direct product comment, 1-reply to comment]',
   primary key (id)
);

alter table pms_spu_comment comment 'product comment';

/*==============================================================*/
/* Table: pms_spu_images                                        */
/*==============================================================*/
create table pms_spu_images
(
   id                   bigint not null auto_increment comment 'id',
   spu_id               bigint comment 'spu_id',
   img_name             varchar(200) comment 'image name',
   img_url              varchar(255) comment 'image url',
   img_sort             int comment 'sort order',
   default_img          tinyint comment 'is default image',
   primary key (id)
);

alter table pms_spu_images comment 'spu image';

/*==============================================================*/
/* Table: pms_spu_info                                          */
/*==============================================================*/
create table pms_spu_info
(
   id                   bigint not null auto_increment comment 'product id',
   spu_name             varchar(200) comment 'product name',
   spu_description      varchar(1000) comment 'product description',
   catalog_id           bigint comment 'category id',
   brand_id             bigint comment 'brand id',
   weight               decimal(18,4),
   publish_status       tinyint comment 'publish status [0-off shelf, 1-on shelf]',
   create_time          datetime,
   update_time          datetime,
   primary key (id)
);

alter table pms_spu_info comment 'spu info';

/*==============================================================*/
/* Table: pms_spu_info_desc                                     */
/*==============================================================*/
create table pms_spu_info_desc
(
   spu_id               bigint not null comment 'product id',
   decript              longtext comment 'product description',
   primary key (spu_id)
);

alter table pms_spu_info_desc comment 'spu info description';
