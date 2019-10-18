## 库表变更

> 此文件为库表更新 `SQL` 文件，由开发者共同维护，**每次修改之前先更新，修改之后及时提交** 。
>
> 文件内容：版本号 + `SQL`语句（版本号从1开始递增）

### 1、系统基础配置表

```json
{
	"version": 1,
	"author": "hxulin",
	"date": "2019-09-24"
}
```

```sql
create table sys_conf
(
   id                   bigint not null comment '编号',
   name                 varchar(100) comment '配置名称',
   value                varchar(500) comment '配置值',
   status               tinyint comment '状态',
   remark               varchar(200) comment '备注',
   update_time          datetime comment '修改时间',
   primary key (id),
   unique key uniq_name (name)
);
```

### 2、后台接口信息表

```json
{
	"version": 2,
	"author": "hxulin",
	"date": "2019-09-24"
}
```

```sql
create table sys_resource
(
   id                   bigint not null comment '主键',
   name                 varchar(50) comment '资源名称',
   mapping              varchar(200) comment '映射路径',
   auth_type            tinyint comment '权限认证类型',
   update_time          datetime comment '修改时间',
   primary key (id)
);
```

### 3、日志记录表

```json
{
	"version": 3,
	"author": "hxulin",
	"date": "2019-09-24"
}
```

```sql
create table sys_operate_log
(
   id                   bigint not null comment '主键',
   trail_id             char(32) comment '操作轨迹编号',
   content              varchar(50) comment '操作说明',
   uri                  varchar(200) comment '接口地址',
   parameters           varchar(2000) comment '提交参数',
   method               varchar(200) comment '后端方法',
   operate_result       tinyint comment '操作结果: 0成功, 1失败',
   operate_exception    varchar(100) comment '操作异常',
   error_message        varchar(200) comment '异常消息',
   username             varchar(20) comment '操作人用户名',
   ip_address           varchar(15) comment '操作IP地址',
   operator_id          bigint comment '操作人ID',
   update_time          datetime comment '操作时间',
   primary key (id)
);
```

### 4、用户表

```json
{
	"version": 4,
	"author": "hxulin",
	"date": "2019-10-18"
}
```

```sql
create table sys_user
(
   id                   bigint not null comment '主键',
   login_name           varchar(20) comment '登录名',
   password             char(32) comment '密码',
   nickname             varchar(20) comment '昵称',
   avatar               varchar(200) comment '头像',
   status               tinyint default 0 comment '状态',
   create_uid           bigint comment '创建人编号',
   update_uid           bigint comment '修改人编号',
   create_time          datetime comment '创建时间',
   update_time          datetime comment '上次修改时间',
   primary key (id),
   unique key uniq_login_name (login_name)
);
```

### 5、角色表

```json
{
	"version": 5,
	"author": "hxulin",
	"date": "2019-10-18"
}
```

```sql
create table sys_role
(
   id                   bigint not null comment '主键',
   code                 varchar(30) comment '角色编码',
   name                 varchar(30) comment '角色名称',
   status               tinyint default 0 comment '状态',
   create_uid           bigint comment '创建人编号',
   update_uid           bigint comment '修改人编号',
   create_time          datetime comment '创建时间',
   update_time          datetime comment '上次修改时间',
   primary key (id),
   unique key uniq_code (code)
);
```

### 6、菜单表

```json
{
	"version": 6,
	"author": "hxulin",
	"date": "2019-10-18"
}
```

```sql
create table sys_menu
(
   id                   bigint not null comment '主键',
   parent_id            bigint comment '父菜单ID',
   menu_name            varchar(20) comment '菜单名称',
   path                 varchar(200) comment '访问路径',
   menu_type            tinyint comment '菜单类型',
   icon                 varchar(100) comment '菜单图标',
   btn_class            varchar(20) comment '按钮标识',
   sort                 bigint comment '排序',
   create_uid           bigint comment '创建人编号',
   update_uid           bigint comment '修改人编号',
   create_time          datetime comment '创建时间',
   update_time          datetime comment '上次修改时间',
   primary key (id)
);
```

### 7、用户角色表

```json
{
	"version": 7,
	"author": "hxulin",
	"date": "2019-10-18"
}
```

```sql
create table sys_user_role
(
   id                   bigint not null comment '主键',
   uid                  bigint comment '用户编号',
   role_id              bigint comment '角色编号',
   primary key (id)
);
```

### 8、角色资源表

```json
{
	"version": 8,
	"author": "hxulin",
	"date": "2019-10-18"
}
```

```sql
create table sys_role_resource
(
   id                   bigint not null comment '主键',
   role_id              bigint comment '角色编号',
   resource_id          bigint comment '资源编号',
   primary key (id)
);
```

### 9、菜单资源表

```json
{
	"version": 9,
	"author": "hxulin",
	"date": "2019-10-18"
}
```

```sql
create table sys_menu_resource
(
   id                   bigint not null comment '主键',
   menu_id              bigint comment '菜单编号',
   resource_id          bigint comment '资源编号',
   primary key (id)
);
```

### 10、角色菜单表

```json
{
	"version": 10,
	"author": "hxulin",
	"date": "2019-10-18"
}
```

```sql
create table sys_role_menu
(
   id                   bigint not null comment '主键',
   role_id              bigint comment '角色编号',
   menu_id              bigint comment '菜单编号',
   primary key (id)
);
```

