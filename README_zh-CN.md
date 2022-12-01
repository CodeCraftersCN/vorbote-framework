# VORBOTE-FRAMEWORK

[![996.icu](https://badgen.net/badge/anti/996/orange?icon=github)](https://996.icu)[![sponsored by JetBrains s.r.o](https://badgen.net/badge/sponsored by/JetBrains)](https://www.jetbrains.com/community/opensource/?utm_campaign=opensource&utm_content=approved&utm_medium=email&utm_source=newsletter&utm_term=jblogo#support)[![](https://badgen.net/badge/available at/maven central/orange?icon=maven)](https://repo1.maven.org/maven2/cn/vorbote)[![](https://badgen.net/badge/version/4.0.0/red)](https://github.com/vorbote/vorbote-framework/releases/tag/v4.0.0)

## Core

 这个模块是整个开源框架的核心部分，其中包含了所有的通用工具类。

### 包 constants

包含了为 Hash 工具类提供支持的 Hash 算法枚举数据。

### 包 exceptions

包含了所有将会被抛出的异常类。

### 包 time

参照 .Net 平台提供的使用方便、兼容性良好的时间计算 API。

### 包 utils

- `BranchUtil`: 分支工具类，通过 **Lambda** 支持分支，减少 `if ... else ...` 代码片段。
  使用方法：

  ```java
  // 以下是用来替代 if 中包含 && 的方法
  BranchUtil.allMatch(Boolean... | Supplier<Boolean>... expressions).handle(Runnable | Supplier<?> ifBranch, Runnable | Supplier<?> elseBranch);
  
  // 以下是用来替代 if 中包含 || 的方法
  BranchUtil.anyMatch(Boolean... | Supplier<Boolean>... expressions).handle(Runnable | Supplier<?> ifBranch, Runnable | Supplier<?> elseBranch);
  ```

- `CalculationUtil`: 由 B 站 UP 主 程序员小山与 Bug （ [b 站主页](https://space.bilibili.com/473631007)、 [GitHub 主页](https://github.com/sunzsh)、[抖音主页](https://www.douyin.com/user/MS4wLjABAAAAE8pu3OvkCMpd_mbHotBKV8Bth4LLl4OA_rVSIAz2Zq2xK22KVBfRlNtegdFy8wtm)）提供的高精度计算工具类，通过 `BigDecimal` 提供链式高精度计算方法。
  使用方法：

  ```java
  var expression = CalculationUtil.startOf(Number aNumber);
  
  // 加法
  expression.add(Number otherNumber[, Integer beforeOperateScale]);
  
  // 减法
  expression.subtract(Number otherNumber[, Integer beforeOperateScale]);
  
  // 乘法
  expression.multiply(Number otherNumber[, Integer beforeOperateScale]);
  
  // 除法
  // 只要除法算出来的小数是有穷尽的，则可以使用下面两种方法中的任意一种。
  expression.divide(Number otherNumber[, Integer beforeOperateScale]);
  // 如果除法算出来的小数是无穷尽的（即无限循环小数和无限不循环小数），则需要使用下面的方法。
  expression.divideWithScale(Number otherNumber, Integer scale[, Integer beforeOperateScale]);
  
  // 推荐：获取 BigDecimal 类型的结果，可以通过 scale 参数指定精确到指定的小数位数。
  expression.getValue([int scale]);
  
  // 获取 Integer 类型的结果（可能会丢失精度）
  expression.getInteger();
  
  // 获取 Long 类型的结果（可能会丢失精度）
  expression.getLong();
  
  // 获取 Double 类型的结果，可以通过 scale 参数指定精确到指定的小数位数。
  expression.getDouble([int scale]);
  ```

- `CollectionUtil`: 用来对 Collection 集合进行检查的工具类。
  使用方法：

  ```java
  // 判断集合是否为空
  CollectionUtil.isEmpty(Collection<?> collection);
  
  // 判断集合是否为非空，等效于 !CollectionUtil.isEmpty(Collection<?> collection);
  CollectionUtil.isNotEmpty(Collection<?> collection);
  ```

- `HashUtil`: 用来对 String 进行哈希运算或加密运算的工具类。
  使用方法：

  ```java
  // Base64 编码
  HashUtil.base64Encode(String value);
  
  // Base64 解码
  HashUtil.base64Decode(String value);
  
  // 使用 MD2, MD5, SHA1, SHA224, SHA384, SHA512 进行哈希运算
  HashUtil.encrypt(cn.vorbote.core.constants.Hash hashMethod, String value);
  
  // 使用 RC4 算法配合 secret 对字符串进行加密（该方法返回 String ）
  HashUtil.encrypt(cn.vorbote.core.constants.Hash.RC4, String secret, String data);
      
  // 使用 RC4 算法配合 secret 对字符串进行加密（该方法返回 byte[] ）
  HashUtil.encryptToByteStream(cn.vorbote.core.constants.Hash.RC4, String data, String secret);
  
  // 使用 RC4 算法配合 secret 对字符串进行解密（该方法返回 String ）
  HashUtil.decrypt(cn.vorbote.core.constants.Hash.RC4, String secret, String encrtyptedString);
  ```

- `MapUtil`: 将 Map 与任意 Object 进行转换。
  使用方法：

  ```java
  // 将 Object 转换为 Map，要求 object 中的每个字段都需要有对应的 getter。
  MapUtil.objectToMap(Object object);
  
  // 将 Map 转换为 Object，要求 Object 中的每个字段名都需要与 Map 中的 key 对应，并且有对应的 setter。
  MapUtil.mapToObject(Map<String, Object> map, Class<T> classOfRequiredObject);
  ```

- `SnowFlake`: 由 Twitter 公司研究的雪花算法，用于生成唯一 ID。
  使用方法：

  ```java
  var idGenerator = new SnowFlake([long startEpoch, ]long workerId, long dataCentreId);
  var generatedId = idGenerator.nextId();
  ```

- `StringUtil`: 用于 String 处理的工具类。

## iCal

该模块实现自 [RFC 5545: Internet Calendaring and Scheduling Core Object Specification (iCalendar)](https://www.rfc-editor.org/rfc/rfc5545)，方便广大 Java 开发者通过该仓库将各种各样的日程同步到适用于市面大部分操作系统中的日历 App 中。

使用方法如下：
```java
var calendar = new cn.vorbote.ical.Calendar()
    .setName(String calendarName) 				// 设置日历的显示名称。
    .setProductName(String productName)			// 设置提供该日历的产品的名称。
    .setMethod('REQUEST' | 'GET')				// 设置获取方式， REQUEST 对应由其他日历引入，该配置是可选的。
    .setCompanyName(String companyName)			// 设置公司名称，该配置是可选的。
    .setDomainName(String domainName);			// 设置域名，会覆盖后续添加的日历事件的域名，该配置是可选的。

var event = new Event()
    .setUid(long uid)							// 设置事件的唯一 ID。
    .setSummary(String summary)					// 设置事件的摘要，一般是显示在日历 App 中的标题。
    .setClassification(Classification cl)		// 设置事件的分级，该配置是可选的。
    .setComment(String comment)					// 设置事件的评论，该配置是可选的。
    .setDescription(String)						// 设置事件的描述，该配置是可选的。
    .setDomainName(String)						// 设置事件 UID 中的域名，如果设置了整个日历的域名，则该配置会被忽略；该配置是可选的。
    .setDuration(TimeSpan)						// 设置事件的时间长度，该配置在设置了事件的结束时间的情况下会被忽略。
    .setEnd(DateTime)							// 设置事件的结束时间。
    .setLocation(String location)				// 设置事件的地点，该配置是可选的。
    .setPercentComplete(Integer)				// 设置事件的进度，该数值需要在0-100之间，该配置是可选的。
    .setPriority(Integer)						// 设置事件的优先级，该配置需要在0-9之间。0是未被定义的优先级，即缺省项；1是最高优先级，9是最低优先级；该配置是可选的。
    .setStart(DateTime)							// 设置事件的开始时间。
    .setTimezone(Timezone)						// 设置事件的时区。该配置是可选的，除非是特别必要，否则我们一般推荐您不要设置这个选项。
    .setUrl(String)								// 设置事件相关信息的 URL ，该配置是可选的。
    .addCategories(String... | Collection<String>)// 将事件添加到多个分类中，该配置是可选的。
    .addCategory(String)						// 将事件添加到指定的分类中，该配置是可选的。

calendar.addEvent(event);
// 如果您有多个事件，可以多次重复上述创建并设置事件的步骤。我们更加推荐您使用匿名对象的方式添加事件。

var calendarEventString = calendar.resolve();	// 该步骤将会将您的日历解析成满足 RFC 5545 文档定义的 iCalendar 格式，以便用户通过各种设备进行管理事件。
```

## Message Sender

Message Sender 通过提供一系列统一的 API 让用户实现通过零配置在不同云服务平台上发送 SMS。

### Message Sender :: Aliyun

该服务集成了阿里云平台的短信发送服务。

使用方式：
```java
// Step 1. 创建短信发送工具
var sender = new AliyunSender(AliyunRegion region, String sign, String keyId, String keySecret[, ObjectMapper objectMapper]);

// Step 2. 构建发送请求
var request = MessageRequest.createRequest(String templateId, String receiver, Map<String, Object> params);

// Step 3. 发送短信
var response = sender.send(request);

// Step 4. 查看服务器响应以及发送状态
response.getMessage(); // 查看云平台的响应消息
response.getCode();    // 查看云平台的响应码
```

### Message Sender :: Tencent Cloud

该服务集成了腾讯云平台的短信发送服务。

使用方式：
```java
// Step 1. 创建短信发送工具
var sender = new TencentSender(TencentRegion region, String sign, String appId, String keyId, String keySecret[, ObjectMapper objectMapper]);

// Step 2. 构建发送请求
var request = MessageRequest.createRequest(String templateId, String receiver, List<String> params);

// Step 3. 发送短信
var response = sender.send(request);

// Step 4. 查看服务器响应以及发送状态
response.getMessage(); // 查看云平台的响应消息
response.getCode();    // 查看云平台的响应码
```

## Message Sender :: Spring Boot Starter

通过将 Message Sender 集成到 Spring Boot Starter ，开发者可以非常轻松的通过 `yaml` 文件的配置来实现在 SpringBoot 中使用发送消息的服务。

## Simple JWT



## Web Dev Suite



## Web Dev Spring Boot Starter



