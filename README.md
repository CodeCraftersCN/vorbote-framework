# VORBOTE-FRAMEWORK

[![996.icu](https://badgen.net/badge/anti/996/orange?icon=github)](https://996.icu)
[![sponsored by JetBrains s.r.o](https://badgen.net/badge/sponsored%20by/JetBrains)](https://www.jetbrains.com/community/opensource/?utm_campaign=opensource&utm_content=approved&utm_medium=email&utm_source=newsletter&utm_term=jblogo#support)
[![available at maven central](https://badgen.net/badge/available%20at/maven%20central/orange?icon=maven)](https://repo1.maven.org/maven2/cn/vorbote)
[![version 4.0.0](https://badgen.net/badge/version/4.0.0/red)](https://github.com/vorbote/vorbote-framework/releases/tag/v4.0.0)

> You could also read the **CHINESE** version of [README](README_zh-CN.md)

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



## Web Dev Suite



## Web Dev Spring Boot Starter

