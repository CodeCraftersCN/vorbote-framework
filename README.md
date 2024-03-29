# VORBOTE-FRAMEWORK

[![996.icu](https://badgen.net/badge/anti/996/orange?icon=github)](https://996.icu)
[![sponsored by JetBrains s.r.o](https://badgen.net/badge/sponsored%20by/JetBrains)](https://www.jetbrains.com/community/opensource/?utm_campaign=opensource&utm_content=approved&utm_medium=email&utm_source=newsletter&utm_term=jblogo#support)
[![available at maven central](https://badgen.net/badge/available%20at/maven%20central/orange?icon=maven)](https://repo1.maven.org/maven2/cn/vorbote)
[![version 4.0.3](https://badgen.net/badge/version/4.0.0/red)](https://github.com/vorbote/vorbote-framework/releases/tag/v4.0.0)
![Spring Boot 3.0.0](https://badgen.net/badge/Spring%20Boot/3.0.0/orange)

> This project has been moved to [JDevKit](https://github.com/CodeCraftersCN/jdevkit)

> 中文版请[点击这里](README_zh-CN.md)

## Core

This module is the central part of this opensource project, including all the common utilities used in the project.

### Package `constants`

Including enum data of hash algorithms supporting Hash Utility.

### Package `exceptions`

Including all the exception will be thrown in the project.

### Package `time`

Providing convenient and well capability time API refer to **.Net** platform

### Package `utils`

- `BranchUtil`: A branch util, supporting branches by **lambda** methods, to reduce `if ... else ...` code blocks.
  
  The way to use:

  ```java
  // This is how we replace if and else when the boolean expression including a &&
  BranchUtil.allMatch(Boolean... | Supplier<Boolean>... expressions).handle(Runnable | Supplier<?> ifBranch, Runnable | Supplier<?> elseBranch);
  
  // This is how we replace if and else when the boolean expression including a ||
  BranchUtil.anyMatch(Boolean... | Supplier<Boolean>... expressions).handle(Runnable | Supplier<?> ifBranch, Runnable | Supplier<?> elseBranch);
  ```

- `CalculationUtil`: A high precision calculation utility provided by a video uploader called "程序员小山与 Bug"
([User page on bilibili.com](https://space.bilibili.com/473631007), [User Page on GitHub](https://github.com/sunzsh) and
[User Page on Douyin.com](https://www.douyin.com/user/MS4wLjABAAAAE8pu3OvkCMpd_mbHotBKV8Bth4LLl4OA_rVSIAz2Zq2xK22KVBfRlNtegdFy8wtm)) 
on bilibili.com (aka "B站" in Chinese), providing chained high-precision calculation methods via class `BigDecimal`.

    The way to use:

  ```java
  var expression = CalculationUtil.startOf(Number aNumber);
  
  // Addition
  expression.add(Number otherNumber[, Integer beforeOperateScale]);
  
  // Subtraction
  expression.subtract(Number otherNumber[, Integer beforeOperateScale]);
  
  // Multiplication
  expression.multiply(Number otherNumber[, Integer beforeOperateScale]);
  
  // Division
  // Either of the following two methods can be used as long as the decimals calculated by the division method are 
  // exhaustive.
  expression.divide(Number otherNumber[, Integer beforeOperateScale]);
  // If the division works out to an infinite number of decimals (i.e. infinite recurring decimals and infinite 
  // non-recurring decimals) then the following method needs to be used.
  expression.divideWithScale(Number otherNumber, Integer scale[, Integer beforeOperateScale]);
  
  // Recommanded：Gets the result of the BigDecimal type and can be specified to the specified number of decimal places 
  // by means of the scale parameter.
  expression.getValue([int scale]);
  
  // Get result of Integer type (precision may be lost)
  expression.getInteger();
  
  // Get result of Long type (precision may be lost)
  expression.getLong();
  
  // Gets the result of the Double type, which can be specified to the specified number of decimal places by means of 
  // the scale parameter.
  expression.getDouble([int scale]);
  ```

- `CollectionUtil`: A tool class used to perform checks on Collection collections.

  The way to use:

  ```java
  // Check whether the collection is empty.
  CollectionUtil.isEmpty(Collection<?> collection);
  
  // Check whether the collection is empty, equivalent to `!CollectionUtil.isEmpty(Collection<?> collection);`
  CollectionUtil.isNotEmpty(Collection<?> collection);
  ```

- `HashUtil`: A tool class for hashing or encrypting Strings.
  
  The way to use:

  ```java
  // Encoding by Base64.
  HashUtil.base64Encode(String value);
  
  // Decoding by Base64.
  HashUtil.base64Decode(String value);
  
  // Hashing using MD2, MD5, SHA1, SHA224, SHA384, SHA512 methods.
  HashUtil.encrypt(cn.vorbote.core.constants.Hash hashMethod, String value);
  
  // Encrypt the string using the RC4 algorithm with secret (this method returns String)
  HashUtil.encrypt(cn.vorbote.core.constants.Hash.RC4, String secret, String data);
      
  // Encrypt the string using the RC4 algorithm with secret (this method returns byte[])
  HashUtil.encryptToByteStream(cn.vorbote.core.constants.Hash.RC4, String data, String secret);
  
  // Decrypt the string using the RC4 algorithm with secret (this method returns String)
  HashUtil.decrypt(cn.vorbote.core.constants.Hash.RC4, String secret, String encrtyptedString);
  ```

- `MapUtil`: Converts a Map to an arbitrary Object.
  
  The way to use:

  ```java
  // Converting an Object to a Map, requires that each field in the object has a corresponding getter.
  MapUtil.objectToMap(Object object);
  
  // Converting a Map to an Object， requires that each field name in the Object corresponds to a key in the Map and has
  // a corresponding setter.
  MapUtil.mapToObject(Map<String, Object> map, Class<T> classOfRequiredObject);
  ```

- `SnowFlake`: The snowflake algorithm, introduced by Twitter, is used to generate unique IDs.
  
  The way to use:

  ```java
  var idGenerator = new SnowFlake([long startEpoch, ]long workerId, long dataCentreId);
  var generatedId = idGenerator.nextId();
  ```

- `StringUtil`: Tool class for String processing.

## iCal

This module is implemented from the [RFC 5545: Internet Calendaring and Scheduling Core Object Specification (iCalendar)](https://www.rfc-editor.org/rfc/rfc5545) 
and allows Java developers to use this module to sync a wide range of calendars to calendar apps for most operating 
systems on the app store.

The way to use:
```java
var calendar = new cn.vorbote.ical.Calendar()
    .setName(String calendarName)                // Set the display name of this calendar.
    .setProductName(String productName)          // Set the name of the product that provides this calendar.
    .setMethod('REQUEST' | 'GET')                // Set the fetch method, REQUEST pairs are introduced by other calendars, this configuration is optional.
    .setCompanyName(String companyName)          // Set the company name, this configuration is optional.
    .setDomainName(String domainName);           // Setting the domain name, which will override the domain name of subsequently added calendar events, is optional.

var event = new Event()
    .setUid(long)                                   // Set a unique ID for the event.
    .setSummary(String)                             // Set a summary of the event, usually the title that is displayed in the Calendar App.
    .setClassification(Classification)              // Set the classification of this event, this configuration is optional.
    .setComment(String comment)                     // Set the comments for the event, this configuration is optional.
    .setDescription(String)                         // Set the description of the event, this configuration is optional.
    .setDomainName(String)                          // Sets the domain name in the event UID, which is ignored if the domain name for the entire calendar is set; this configuration is optional.
    .setDuration(TimeSpan)                          // Set the duration of the event, this configuration is ignored if the end time of the event is set.
    .setEnd(DateTime)                               // Sets the end time of the event.
    .setLocation(String location)                   // Set the location of the event, this configuration is optional.
    .setPercentComplete(Integer)                    // Set the progress of the event, the value needs to be between 0 and 100, this configuration is optional.
    .setPriority(Integer)                           // Set the priority of the event, this configuration needs to be between 0 and 9. 0 is the undefined priority, the default item; 1 is the highest priority and 9 is the lowest; this configuration is optional.
    .setStart(DateTime)                             // Sets the start time of the event.
    .setTimezone(Timezone)                          // Sets the time zone of the event. This configuration is optional and we generally recommend that you do not set this option unless it is specifically necessary.
    .setUrl(String)                                 // Set the URL of the event-related information, this configuration is optional.
    .addCategories(String... | Collection<String>)  // Adding events to multiple categories, this configuration is optional.
    .addCategory(String);                           // Add the event to the specified category, this configuration is optional.

// If you have more than one event, you can repeat the steps above to create and set the event several times. We 
// recommend that you add events using anonymous objects.
calendar.addEvent(event);

// This step will parse your calendar into an iCalendar format that meets the definition of the RFC 5545 document, 
// allowing users to manage events from various devices.
var calendarEventString = calendar.resolve();
```

## Message Sender

Message Sender enables users to send SMS across different cloud service platforms with zero configuration by providing 
a series of unified APIs.

### Message Sender :: Aliyun

The service is integrated with the SMS delivery service of the AliCloud platform.

The way to use:
```java
// Step 1. Create a sender.
var sender = new AliyunSender(AliyunRegion region, String sign, String keyId, String keySecret[, ObjectMapper objectMapper]);

// Step 2. Build a Send SMS Request.
var request = MessageRequest.createRequest(String templateId, String receiver, Map<String, Object> params);

// Step 3. Send the SMS.
var response = sender.send(request);

// Step 4. Check the response from the server of Aliyun.
response.getMessage(); // check the response message from the server
response.getCode();    // check the response code from the server
```

### Message Sender :: Tencent Cloud

The service is integrated with the SMS delivery service of the Tencent Cloud platform.

The way to use:
```java
// Step 1. Create a sender.
var sender = new TencentSender(TencentRegion region, String sign, String appId, String keyId, String keySecret[, ObjectMapper objectMapper]);

// Step 2. Build a Send SMS Request.
var request = MessageRequest.createRequest(String templateId, String receiver, List<String> params);

// Step 3. Send the SMS.
var response = sender.send(request);

// Step 4. Check the response from the server of Tencent.
response.getMessage(); // check the response message from the server
response.getCode();    // check the response code from the server
```

## Message Sender :: Spring Boot Starter

By integrating Message Sender into the Spring Boot Starter, developers can easily implement the use of the sending 
service in SpringBoot through the configuration of the `yaml` file.

## Simple JWT

Simple JWT implemented simpe functions to process **Json Web Token** by encapsulating `com.auth0:java-jwt` lib.

The way to use:

```java
// Step 1. Create a token utility.
final var accessKeyUtil = new AccessKeyUtil(JwtAlgorithm algorithm, String secret, String issuer);

// Step 2. Create a token.
// Build the token through the data in Map<String, Object>.
var token = accessKeyUtil.createToken(TimeSpan expire, String subject, String[] audience, Map<String, Object> claims);
// Build the token through the data in a Java Bean.
var token2 = accessKeyUtil.createTokenWithBean(TimeSpan expire, String subject, String[] audience, Object bean);

// Step 3. Verify or resolve the token.
// Verify the token.
accessKeyUtil.verify(String token);
// Resolve the token.
DecodedJWT information = accessKeyUtil.info(String token);
// Resolves the Payload in the token directly to an object of the specified type.
T bean = accessKeyUtil.getBean(String token, Class<T> requiredType);

// Step 4. Renew the token.
var newToken = accessKeyUtil.renew(String token, TimeSpan expireAfter);
var newToken2 = accessKeyUtil.renewWithBean(String token, TimeSpan expireAfter, Class<?> requiredType);
```

### Remove some unwanted information in the token in create the token with beans.

Generally, it is much more simple to create a token with a java bean. However, it is possible that the bean may contain data that is not required or cannot be placed in the token. Due to the nature of the JWT, we can parse out all the information contained in the token directly online via the [JWT official website](https://jwt.io) without knowing the authentication secret, as shown below.

![image-20221201204851870](https://dist.cq.vorbote.cn/images/typora-images/image-20221201204851870.png) 

Obvisouly, we have measures to handle this problem. As you can see in the class `User` in follow codes .

```java
public final class User {
    private String username;
    private String password;
    
    // no-arg constructor and all-arg constructors...
    
    // getters and setters...
}
```

We can see that the field `password` cannot appear in the token anyway, so we just need to make the following changes to this class.

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

The required change is shown in the code above and is tagged with the number **(1)**, **(2)**. The annotation `@JwtIgnore` tells the token parser to ignore this field.

## Web Dev Suite

This module is dedicated to providing a comfortable Java Web development experience for all developers.

### Package `constants`

It contains web response codes commonly used in web development and is provided in the form of enumerated data for developers to use.

### Package `filter`

#### `CorsFilter`

During the development process, we have found that many developers often fail to obtain the data they need due to [Cross-Origin Resource Sharing (CORS)](https://developer.mozilla.org/en-US/docs/Web/HTTP/CORS) policy that made the browser cancelled the data fetching process. With this `CorsFilter`, developers can easily solve this problem.

##### The way to use

If you develop your web app on `Tomcat` server directly, you could add thest configuration to file `/WEB-INF/web.xml` and made this filter be the **first** filter configuration.

```xml
<!-- As the angle brackets have a special meaning in the xml code, the required fields have been replaced with round brackets. -->
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

If you are developing this app with **Spring Framework**, then you can register this filter into the **Spring** container and set its order to the first to setup.

## Web Dev Spring Boot Starter

This SpringBoot Starter integrated **Simple JWT** and **Web Dev Suite**, and can move all the configurations into `application.yml` file or `application.properties` file.

# How to use these module?

##  Prerequisites

Due to the use of version 6.x of `Spring Framework` and version 3.x of `SpringBoot`, you'll need **JDK 17** or **later** version to run it. If you want to use this framework in JDK 16 or previous version, please have a look at version 3.x of this framework.

## Installation

### For Maven Users

It is quite simple. Add the following codes to the `<dependencies>` node in the pom.xml file and run import task, then you can use the module.

```xml
<dependency>
	<groupId>cn.vorbote</groupId>
    <artifactId>(module-name)</artifactId>
    <version>4.0.0</version>
</dependency>
```

### For Gradle Users

Add the following codes to `dependencies` node in `build.gradle` file and run the import task, you can use the module.

```groovy
implementation 'cn.vorbote:vorbote-webdev-spring-boot-starter:4.0.0'
```

### For Those Who Want to Add a Library Directory

1. Download `jar` package from [GitHub Releases](https://github.com/zihluwang/vorbote-framework/releases) page.
2. Move the `jar` package to your lib directory.
3. Add the directory to `build path`.

### For Those Who Want to Build

1. Fork the repository.
2. Make sure you have the environment of JDK 17 or later version and Maven 3.6.x or later version.
3. Run the command `mvn clean install -P snapshot` to install the lib to local `maven` repository.

# How to help us?

When you found some problems when using any of these modules, you can report to us by [submitting a GitHub Issue](https://github.com/zihluwang/vorbote-framework/issues/new?assignees=theodorehills&labels=bug&template=bugreport-en-.md&title=[BUG]). Or if you are able to handle this problem by yourself, we are looking forward to receiving your Pull Request!
