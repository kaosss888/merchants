采用工具
-- JDK1.8
亮点：Lambda表达式和函数式接口方便开发
-- SpringBoot
涵盖了Spring的主要特性，也是微服务的基础
-- HBase
面向列的数据库运行在HDFS之上，能向其他关系型数据库一样提供随机读写的性能，能够存储海量数据并对外提供高性能读写特性，分布式数据存储
-- MySQL
-- Redis
缓存
-- Kafka
消息队列

*** 微服务 ***
*** 海量数据存储 *** （数据增长只需要增加存储空间，不影响存储性能）

1、业务系统开发思路
2、系统开发流程
3、提升架构能力
4、遇到问题可以提出解决或优化的方案

-------------------------------------------------------------------------------------
步骤：
需求规划（需求拆解、需求辩论等等）
技术选型
工程设计
工程实施（编码）
功能测试（功能测试、性能测试）
应用上线（自动化部署）

开发技术：例如利用Kafka消息队列实现优惠券从商户到用户的传递，MySQL存储商户信息、HBase存储用户信息、SpringBoot搭建项目、Redis缓存提高IO效率等等

测试用例：100%覆盖率

应用上线
--------------------------------------------------------------------------------------

应用技术分层
-- 框架层
JDK1.8
SpringBoot 自动配置、内嵌Tomcat
-- 存储层
MySQL（不适合存储**太多**数据）、HBase（列族式数据库，没有关系概念，行键排序，越散列热点出现概率越低效率越高，不支持事务但是行级别原子性）、Redis（键值对类型缓存数据库，内存数据库，单线程）
-- 消息队列
Kafka

--------------------------------------------------------------------------------------

HBase
列族式存储，列族是表的Schema的一部分，创建表之前需要指定列族但是不用指定列，列名都是以列族为前缀，访问控制、磁盘和内存使用等都是在列族层面进行。
列是列族的一部分，访问数据的单元是列。

table的组成：Table = RowKey（行键） + Family（列族） + Column（列） + Timestamp（时间版本） + Value（数据值）
数据存储模式
（Table，RowKey，Family，Column，Timestamp）-> Value

列数据属性：可以保存多个数据版本
数据存储原型
SortedMap<
          RowKey, List<
                     SortedMap<
                               Column, List<
                                          Value, Timestamp
                               >
                     >
          >
>
底层是两级的SortedMap，首先会对RowKey进行排序，再会对Column名称进行排序。

*** 行式存储维护大量的索引，维护成本比较高，不能做到线性扩展，但是对于随机读效率高，事务处理支持得好
*** 列式存储根据同一列数据相似性的原理利于对数据进行压缩，存储成本降低，列数据分开存储可以并行查询
单列或者比较少的列获取频率较高则使用列式存储，多列使用并行处理，大数据环境利于数据压缩和线性扩展，存储情景较多，事务使用率不高，不适合小数据量，不适合随机更新某些行。
行式存储最大的特点是关系解决方案，优势是联级事务处理能力，对于数据量不大使用行式。


--------------------------------------------------------------------------------------

卡包应用
卡券收集、聚合类应用

子系统：
-- 商户投放子系统（商户开放平台）
-- 用户应用子系统（用户使用入口）

优惠券使用方法：
-- 展示型
-- 兑换型（二级卡券）
-- token核销型

卡包应用扩展：
-- 纪念性卡券
-- 身份证件信息
-- 银行卡
-- 荣誉勋章

------------------------------------------------

需求：
商户投放子系统：
-- 接口身份验证
    -- 商户接口字段
        name                         商户名称 全局唯一
        logo_url                     商户logo
        business_license_url         商户营业执照
        phone                        商户联系电话
        address                      商户地址

    -- 优惠券接口字段
        id                           所属商户id
        title                        优惠券标题 全局唯一
        summary                      优惠券摘要
        desc                         优惠券详细信息
        limit                        最大个数限制
        has_token                    优惠券是否有token
        background                   优惠券背景色
        start                        优惠券开始使用时间
        end                          优惠券结束使用时间

用户引用子系统：
我的卡包
过期优惠券（领取过但是已过期）
优惠券库存（未领取）
用户反馈（优惠券相关问题、我的卡包相关问题）

-------------------------------------------------

应用架构设计

商户应用：
商户投放子系统
-- 商户注册
   商户信息写入到MySQL保存，会为商户分配一个唯一标识token，用于操作平台时的校验
-- 投放优惠券
   优惠券存放到Kafka中，由用户系统接收，如果优惠券有token会以文件的形式写入到redis中保存，需要使用token时会从redis分配给对应优惠券

用户应用：
用户应用子系统
HBase
-- 用户表           存储卡包用户的基本信息
-- 用户优惠券表      记录用户领取的优惠券
-- 商户优惠券表      存储商户在商户系统上投放的优惠券
-- 反馈信息表        存储用户的反馈信息

Service
Pass、Feedback、id生成器

**************
缓存层设计
**************
PassTemplate Token存储方案
init 商户投放的token used 用户已使用的token  --->  Redis（set类型保存）
优惠券id对应redis中的key，value为set结构，保存所有的token，pop分配token给用户
同时加入到uesd文件中，提升I/O效率

商户信息
使用Hashmap存储商户信息到redis中，field是商户id，value是商户的序列化信息

**************
常用工具类
**************
Apache DigestUtils
commons-codec提供了常用的编码解码工具类（Base64、MD5、URL等）
使用的DigestUtils用来简化原生JDK的MessageDigest类的常用操作来计算一个字符串的MD5值
线程安全类， md5Hex(String data): MD5字符串
原生MessageDigest只能被调用一次，一旦被调用之后会被重置到初始状态，使用上稍显复杂并且在多线程环境下不安全

Apache RandomStringUtils
commons-lang
生成随机数字、字母的工具类
RandomStringUtils.random(5)  -- 产生5位长度的随机字符串
RandomStringUtils.random(5, new char[] {'a', 'b', '2', '4'})  -- 使用指定的字符生成5位长度的随机字符串
RandomStringUtils.randomAplhanumeric(5)  -- 生成指定长度的字母和数字的随机组合字符串
RandomStringUtils.randomNumeric(5)  -- 生成随机数字字符串
RandomStringUtils.randomAlphabetic(5)  -- 生成[a-z]字符串，包含大小写
RandomStringUtils.randomAscii(4)  -- 生成ACSII32到126组成的随机字符串

Apache DateUtils
commons-lang
日期操作
Date parseDate(String str, String ...parsePatterns)  -- 解析日期时间字符串日期时间Date对象
Date addYears(Date date, int amount)  -- 得到date日期时间后（前）amount年后的日期时间
Date addDays(Date date, int amount)  -- 同addYears相似，对天数进行加减
Date addHours(Date date, int amount)  -- 同addYears相似，对小时进行加减
boolean isSameDay(Date date1, Date date2)  -- 判断两个日期是否为同一天

Google Guava Enums
com.google.guava
扩展枚举类
static Field getField(Enum<?> enumValue)
返回enumValue定义的Field

static <T extends Enum<T>> Option<T> getIfPresent(Class<T> enumClass, String value)
返回Enum类型的值，使用Enum.valueOf(java.lang.Class<T>, java.lang.String)

static <T extends Enum<T>> Converter<String, T> stringConverter(Class<T> enumClass)
返回一个converter对象转换成string以及给定Enum类型的值，使用Enum.valueOf(Class, String)与Enum.name()

starter-hbase
com.spring4all spring-boot-starter-hbase
自定义的HBase的starter，为HBase的query、update等操作提供简易的API并集成Spring-boot的autoConfiguration
对HBase的原始API提供了很多封装，并且提供了很多ORM功能，方便操作HBase表

**************
日志处理
**************
记录日志 系统的行为
分析日志 用户的行为 （action、userId、timestamp、remoteIp、info）

**************
异常处理
**************
@ControllerAdvice 定义统一的异常处理类
@ExceptionHandler 定义函数针对的异常类型
Restful Api返回

---------------------------------------------------------------
**************
表结构
**************

---------商户投放子系统表结构---------
商户信息
(MySQL)
id, name, logo_url, business_license_url, phone, address, is_audit(是否通过审核标志)

优惠券信息
(HBase)
表名
pb:passtemplate
列族
b(基本属性列族), o(限制条件列族)
b -- id(对应商户id), 
     title, 
     summary, 
     desc, 
     has_token, 
     background
o -- limit, 
     start, 
     end

token保存在文件中

---------用户应用子系统---------
Pass -- 用户优惠券信息
(HBase)
表名
pb:pass
列族
i
i -- user_id(优惠券所属user_id), 
     template_id(标记passtemplate在HBase中的行键，用于映射具体优惠券信息), 
     token(如果优惠券不带有token可以填充为-1来标记不存在), 
     assigned_date(标记优惠券的领取日期), 
     con_date(优惠券消费日期，-1代表没有被消费)

Feedback -- 返回信息
(HBase)
表名
pb:feedback
列族
i
i -- user_id,
     type(评论类型，app/pass),
     template_id(优惠券的行键),
     comment


id生成器
user表
(HBase)
表名
pb:user
列族
b, o
b -- name,
     age,
     sex
o -- phone,
     address