# 债查查技术设计

关键字缩写：
   债查查： zcc; 债查查AMC模块：zcc-amc; 债查查单点登录模块: zcc-sso; 债查查投资人库模块: zcc-cust

# 技术栈版本
后台主要技术框架： springboot 最新， 数据库 mysql ; mongodb; 消息组件 kafka; 
前端: vue 



## zcc-amc

zcc-amc 是债查查之资产管理模块，目的是能录入资产包，债权，债权关联资产的信息

### 模块详细设计
用如下几个表来做具体的业务内容存储

 AMC_ASSET; AMC_CMPY; AMC_DEBT; AMC_DEBTOR; AMC_DEBTPACK; AMC_DEBT_CONTACTOR; AMC_INFO; AMC_ORIG_CREDITOR; CURT_INFO
 
 主关键信息存储在AMC_DEBT 和AMC_ASSET 表中
 他们的关系是 AMC_ASSET 是关联到 AMC_DEBT, AMC_DEBT可以关联到AMC_DEBTPACK
 其它表是辅助信息的存储
 图片信息存在阿里云OSS服务器上

#### 业务模块的权限管理
角色 
SYSTEM_ADMIN 系统管理员
AMC_ADMIN amc管理员
AMC_USER  amc用户
ZCC_CLIENT 债查查浏览用户

动作权限
ACT_CREATE(1, "create-draft", "创建")|ACT_SAVE(2, "save", "保存")|ACT_SUBMIT4PUB(3, "submit-pub", "提交审核")
|ACT_REVIEW_FAIL(4, "review-failed","审核未通过")|ACT_REVIEW_PASS(5, "review-pass","审核通过发布")
|ACT_OFF_SHELF(6, "off-shelf","下架")|ACT_SOLD(7,"sold","售出")|ACT_DEL(8, "del", "删除")
|PERM_CREATE_AMCADMIN("PERM_CRUD_AMCADMIN",6)|PERM_CREATE_AMCUSER("PERM_CRUD_AMCUSER",1)|

SYSTEM_ADMIN 拥有以上所有动作权限
AMC_ADMIN 不能做审核相关动作,不能创建AMC_ADMIN
AMC_USER 不能做审核相关动作, 不能创建AMC_ADMIN,AMC_USER

详见图片



在处理amc 内容发布方面有个流程管理,对此设计了 状态和动作变迁的关系

|当前状态				    |动作				            |变动后状态        |
|-------------------|-----------------------|----------------|
|					          |ACT_CREATE		          |DRAFT
|DRAFT				      |ACT_SAVE		            |DRAFT
|DRAFT_CHECK_FAILED	|ACT_SAVE		            |DRAFT
|UNSOLD_OFF_SHELF	  |ACT_SAVE		            |RECORD
|DRAFT_CHECK_WAIT	  |ACT_REVIEW_FAIL	      |DRAFT_CHECK_FAILED
|RECORD_CHECK_WAIT	|ACT_REVIEW_FAIL	      |RECORD_CHECK_FAILED
|DRAFT_CHECK_WAIT	  |ACT_REVIEW_PASS	      |PUBLISHED
|RECORD_CHECK_WAIT	|ACT_REVIEW_PASS	      |PUBLISHED
|DRAFT				      |ACT_SUBMIT4PUB	        |DRAFT_CHECK_WAIT
|RECORD				      |ACT_SUBMIT4PUB	        |RECORD_CHECK_WAIT
|UNSOLD_OFF_SHELF	  |ACT_SUBMIT4PUB	        |RECORD_CHECK_WAIT
|SOLD				        |ACT_OFF_SHELF	        |SOLD_OFF_SHELF
|PUBLISHED			    |ACT_OFF_SHELF	        |UNSOLD_OFF_SHELF
|PUBLISHED			    |ACT_SOLD		            |SOLD
|DRAFT				      |ACT_DEL			          |Physical DEL
|DRAFT_CHECK_FAILED	|ACT_DEL			          |DELETED
|UNSOLD_OFF_SHELF	  |ACT_DEL			          |DELETED
|RECORD				      |ACT_DEL			          |DELETED
|RECORD_CHECK_FAILED	|ACT_DEL			          |DELETED


流程图图片

## zcc-sso
zcc-sso 是整个项目的单点登录中心模块, 负责用户登录, 角色权限配置
在功能实现上采用了springboot oauth模块, 在用户登录成功后生成token, token里面包含用户权限信息,token采用jwt规范, 在zcc-amc模块里面可以进行权限校验和拦截
采用下面这些表对用户权限角色,用户信息进行存储

AMC_DEPT
AMC_PERMISSION
AMC_ROLE
AMC_ROLE_PERMISSION
AMC_USER
AMC_USER_ROLE
AMC_WECHAT_USER

### 角色配置
角色 
SYSTEM_ADMIN 系统管理员
AMC_ADMIN amc管理员
AMC_USER  amc用户
ZCC_CLIENT 债查查浏览用户

### 权限配置
在 AMC_PERMISSION

PERM_AMC_CRUD
PERM_AMC_REVIEW
PERM_AMC_VIEW
PERM_BASIC_INFO
PERM_CRUD_AMCADMIN
PERM_CRUD_AMCUSER
PERM_LAWYERAMC_VIEW


## zcc-cust
zcc-cust 投资人库是为了将另外一个系统爬虫系统里面找到的资产处置投资公告信息整理展现出来的模块,帮助业务人员查找潜在客户
采用下面表来存储投资人信息
CUST_REGION
CUST_TRD_CMPY
CUST_TRD_INFO
CUST_TRD_PERSON
CUST_TRD_PERSON_CMPY
CUST_TRD_SELLER
CUST_WECHAT_INFO

同步的时候采用了王伟爬虫系统2个接口
1. http://10.20.100.228:8085/debtCustomers/getJson?page=2&pageSize=10  --获取投资人/公司信息
2. http://10.20.100.228:8085/debtCustomerDebts/getJson?customerId=2e4b3351b10bac69bf424f196c15c965&page=1&pageSize=10 
 	--获取投资人/公司交易记录信息

同步流程
1. 调用上述2个接口的第一个接口,先获取投资人/公司列表
2. 查看ID是否已经在同步记录表中有记录,如果有的话转更新逻辑4,如果没有的话转新增逻辑3
3. 新增投资人/公司
4. 更新投资人/公司
5. 调用第二个接口, 获取对应的交易信息, 如果有该交易信息的话转更新逻辑7，如果没有的话转新增逻辑8
6. 新增交易信息
7. 更新交易信息

定时任务或者人工触发同步
1. 已经配置spring cron schedule模块, 可以灵活配置同步周期达到一个最终一致性
2. 有时候需要人工干预的话, 也有对应的接口可以直接通过curl或者swagger 调用触发


# 开发环境 测试环境 生产环境
## 开发环境 

git 分支 master
用application-dev.yml配置
目前采用的数据库是
10.20.100.70 - mysql
10.20.100.235/6 - mongodb

## 测试环境

git 分支 master
用application-test.yml配置
目前采用的数据库是
10.20.100.70 - mysql
10.20.100.235/6 - mongodb
不同的是后台部署在
10.20.100.236 /home/sunht/working/zcc
kafka:
10.20.100.238
前端部署在
10.20.100.236 /home/sunht/working/web/dist

## 生产环境

git 分支 prod
用application-prod.yml配置
目前采用的数据库是
10.20.100.238 - mysql
10.20.100.238 - mongodb
不同的是后台部署在
10.20.100.238 /home/chenwei/working/zcc
kafka:
10.20.100.238
前端部署在
10.20.100.238 /home/chenwei/working/zcc/web/dist
