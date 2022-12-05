# CHANGELOG

## [4.0.0](https://github.com/zihluwang/vorbote-framework/releases/tag/v4.0.0) - 2022-12-01

- Upgraded Spring framework to [3.0.0]([Release v3.0.0 · spring-projects/spring-boot (github.com)](https://github.com/spring-projects/spring-boot/releases/tag/v3.0.0))

## [4.0.1](https://github.com/zihluwang/vorbote-framework/releases/tag/v4.0.1) - 2022-12-02

- Made some code changes to fit features in JDK 17.
- Removed deprecated method `cn.vorbote.simplejwt.AccessKeyUtil#renew(String token, int expireAfter)`
- Changed the order of the parameters in method `cn.vorbote.core.utils.HashUtil#encryptToByteStream(Hash method, String key, String data)`

## [4.0.2](https://github.com/zihluwang/vorbote-framework/releases/tag/v4.0.2) - 2022-12-05

- Removed the official Aliyun SDK and migrated to using OkHttpClient to send the Web API.
- Removed the official Tencent Cloud SDK and migrated to using OkHttpClient to send the Web API.