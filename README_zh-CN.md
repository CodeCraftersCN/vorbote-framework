# VORBOTE-FRAMEWORK

[![996.icu](https://badgen.net/badge/anti/996/orange?icon=github)](https://996.icu)
[![sponsored by JetBrains s.r.o](https://badgen.net/badge/sponsored%20by/JetBrains)](https://www.jetbrains.com/community/opensource/?utm_campaign=opensource&utm_content=approved&utm_medium=email&utm_source=newsletter&utm_term=jblogo#support)
[![available at maven central](https://badgen.net/badge/available%20at/maven%20central/orange?icon=maven)](https://repo1.maven.org/maven2/cn/vorbote)
[![version 4.0.0](https://badgen.net/badge/version/4.0.0/red)](https://github.com/vorbote/vorbote-framework/releases/tag/v4.0.0)

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

Simple JWT 通过对 `com.auth0:java-jwt` 库进行封装实现了简易的 JWT 令牌功能。

使用方式：

```java
// Step 1. 创建令牌实用工具
final var accessKeyUtil = new AccessKeyUtil(JwtAlgorithm algorithm, String secret, String issuer);

// Step 2. 创建令牌
// 通过 Map<String, Object> 创建令牌
var token = accessKeyUtil.createToken(TimeSpan expire, String subject, String[] audience, Map<String, Object> claims);
// 通过 Java Bean 创建令牌
var token2 = accessKeyUtil.createTokenWithBean(TimeSpan expire, String subject, String[] audience, Object bean);

// Step 3. 验证或解析令牌
// 验证令牌
accessKeyUtil.verify(String token);
// 解析令牌
DecodedJWT = accessKeyUtil.info(String token);
// 直接将令牌中的 Payload 解析为指定类型的对象
T bean = accessKeyUtil.getBean(String token, Class<T> requiredType);

// Step 4. 更新令牌
var newToken = accessKeyUtil.renew(String token, TimeSpan expireAfter);
var newToken2 = accessKeyUtil.renewWithBean(String token, TimeSpan expireAfter, Class<?> requiredType);
```

### 去除令牌中的部分信息

一般来说，通过 Java Bean 创建令牌的方式更为简单，但是有可能这个 Bean 会包含一些不需要或者不能够放在令牌中的数据。由于 JWT 的特性，我们无需知道验证的 secret 就可以直接通过 [JWT 官网](https://jwt.io)在线解析出令牌所包含的所有信息，如下图所示。

![image-20221201204851870](https://dist.cq.vorbote.cn/images/typora-images/image-20221201204851870.png) 

例如下面这个 `User` 类。

```java
public final class User {
    private String username;
    private String password;
    
    // no-arg constructor and all-arg constructors...
    
    // getters and setters...
}
```

我们可以发现，字段 `password` 是无论如何也不能出现在令牌中的，因此，我们只需要对这个类进行如下的修改。

```java
import cn.vorbote.simplejwt.annotations.JwtIgnore;      // (1)

public final class User {
    private String username;
    @JwtIgnore                                          // (2)
    private String password;
    
    // no-arg constructor and all-arg constructors...
    
    // getters and setters...
}
```

需要改动的地方如上述代码所示，被标记了 **(1)**，**(2)** 的编号。通过 `@JwtIgnore` 标记即可告知令牌解析器忽略这个字段。

## Web Dev Suite

该模块致力于给各位开发者提供舒适的 Java Web 开发体验。

### 包 `constants`

其包含了在 Web 开发中常用到的 Web 响应码，并且以枚举数据的形式提供给各位开发者使用。

### 包 `filter`

#### `CorsFilter`

在开发过程中，我们发现了许多开发者在开发前后端分离式项目时，经常碰到浏览器会由于[跨源资源共享](https://developer.mozilla.org/zh-CN/docs/Web/HTTP/CORS)问题导致无法通过浏览器的验证而无法获取到需要的数据。通过这个 `CorsFilter` ，开发者可以简单轻松的解决这个问题。

##### 使用方式

如果您直接使用 `Tomcat` 进行开发，那么您需要前往 `/WEB-INF/web.xml` 中添加如下配置：

```xml
<!-- 由于尖括号本身在 xml 代码中有特殊含义，因此必填项改为使用圆括号代替 -->
<web-app>
    <filter>
        <filter-name>CorsFilter</filter-name>
        <filter-class>cn.vorbote.web.filter.CorsFilter</filter-class>
        <init-param>
            <param-name>allowCredentials</param-name>
            <param-value>(true | false)</param-value>
        </init-param>
        <init-param>
            <param-name>allowOrigin</param-name>
            <param-value>[one or more domains | ip addresses]</param-value>
        </init-param>
        <init-param>
            <param-name>allowHeaders</param-name>
            <param-value>[one or more request header names]</param-value>
        </init-param>
        <init-param>
            <param-name>allowMethods</param-name>
            <param-value>[one or more request methods]</param-value>
        </init-param>
        <init-param>
            <param-name>exposeHeaders</param-name>
            <param-value>[one or more header names to expose]</param-value>
        </init-param>
    </filter>
    <filter-mapping>
    	<filter-name>CorsFilter</filter-name>
        <url-pattern>(the path pattern to handle, normally /**)</url-pattern>
    </filter-mapping>
</web-app>
```

如果您使用 **Spring Framework** 进行开发，那么只需要将这个 CorsFilter 注册到 **Spring** 容器中并将其 Order 调整为最先，即可完成配置。

## Web Dev Spring Boot Starter

该 SpringBoot Starter 集成了 Simple JWT 与 Web Dev Suite ，可以将这两个模块中的配置迁移到 SpringBoot 的 `application.yml` 或 `application.properties` 文件中。

# 如何使用这些模块？

##  前提要求

由于这些模块使用了 **Spring Framework** 6.x 版本以及 **SpringBoot 3.x** 版本，因此你需要 **JDK 17 或者更高版本**来运行它。如果你想在 JDK 16 或更早版本的 JDK 中使用，请查看我们的 3.x 版本。

## 安装方式

### 使用 Maven 的用户

通过将如下代码添加到 `pom.xml` 文件中的 `<dependencies>` 节点中，并运行导入程序，Maven 会自动将打包好的 jar 文件下载到您的本地 maven 仓库中并完成导入。

```xml
<dependency>
  <groupId>cn.vorbote</groupId>
  <artifactId>(module-name)</artifactId>
  <version>4.0.0</version>
</dependency>
```

### 使用 Gradle 的用户

通过将如下代码添加到 `build.gradle` 文件中的 `dependencies` 节点中，并运行导入程序，Maven 会自动将打包好的 jar 文件下载到您的本地 maven 仓库中并完成导入。

```groovy
implementation 'cn.vorbote:<module-name>:4.0.0'
```

### 对于想将 `jar` 包添加到库文件夹的用户

1. 从 [GitHub Releases](https://github.com/zihluwang/vorbote-framework/releases) 下载最新版本的 `jar` 包。
2. 将 `jar` 包移动到您的库文件夹中。
3. 将文件夹添加到 `Build Path`中。

### 对于想自己构建的用户

1. Fork 这个仓库。
2. 确保您拥有 JDK 17 或更高版本及 Maven 3.6.x 或更高版本。
3. 使用命令 `mvn clean install -P snapshot` 进行构建并安装到本地 `maven` 仓库。

# 如何帮助我们？

当你在使用模块的时候发现了任何我们模块的问题，请放心大胆的[提交GitHub Issue](https://github.com/zihluwang/vorbote-framework/issues/new?assignees=theodorehills&labels=bug&template=bug--.md&title=[BUG])
。当然，如果您可以自己解决这个问题，我们也很期待收到您的 Pull Request。
