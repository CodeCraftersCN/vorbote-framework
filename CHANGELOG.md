# CHANGELOG

## [4.1.0.EAP](https://github.com/zihluwang/vorbote-framework/releases/tag/v4.1.0.EAP) - 2022-12-29

### Changes

- Changed configurations of Request Method in CORS module from String to Enumerations.

## [3.8.0](https://github.com/zihluwang/vorbote-framework/releases/tag/v3.8.0) - 2022-12-23

### Changes to compatibility

- Compatible with **JDK 8** and **Spring Boot 2.7.x**

## [4.0.3](https://github.com/zihluwang/vorbote-framework/releases/tag/v4.0.3) - 2022-12-19

### Fixed

- Fixed the issue that all components cannot being auto configured when using all Spring Boot Starters.

> Spring Team deprecated to configure an AutoConfigurer by specify it in the file `META-INF/spring.factories` in Spring 
> Boot 2.7.x and removed it in version 3.x.

## [4.0.2](https://github.com/zihluwang/vorbote-framework/releases/tag/v4.0.2) - 2022-12-05 [YANKED]

- Removed the official Aliyun SDK and migrated to using OkHttpClient to send the Web API.
- Removed the official Tencent Cloud SDK and migrated to using OkHttpClient to send the Web API.

## [4.0.1](https://github.com/zihluwang/vorbote-framework/releases/tag/v4.0.1) - 2022-12-02 [YANKED]

- Made some code changes to fit features in JDK 17.
- Removed deprecated method `cn.vorbote.simplejwt.AccessKeyUtil#renew(String token, int expireAfter)`
- Changed the order of the parameters in method `cn.vorbote.core.utils.HashUtil#encryptToByteStream(Hash method, String key, String data)`

## [4.0.0](https://github.com/zihluwang/vorbote-framework/releases/tag/v4.0.0) - 2022-12-01 [YANKED]

- Upgraded Spring framework to [3.0.0]([Release v3.0.0 · spring-projects/spring-boot (github.com)](https://github.com/spring-projects/spring-boot/releases/tag/v3.0.0))


