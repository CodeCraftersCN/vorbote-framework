# 更新日志

## [4.0.0](https://github.com/zihluwang/vorbote-framework/releases/tag/v4.0.0) - 2022-12-01

- 将 SpringBoot 版本升级到[v3.0.0](https://github.com/spring-projects/spring-boot/releases/tag/v3.0.0)

## [4.0.1](https://github.com/zihluwang/vorbote-framework/releases/tag/v4.0.1) - 2022-12-02

- 修改部分代码以适应 JDK17 中的新功能。
- 移除被弃用方法 `cn.vorbote.simplejwt.AccessKeyUtil#renew(String token, int expireAfter)`。
- 修改方法 `cn.vorbote.core.utils.HashUtil#encryptToByteStream(Hash method, String key, String data)` 参数列表的顺序。

## [4.0.2](https://github.com/zihluwang/vorbote-framework/releases/tag/v4.0.2) - 2022-12-05

- 移除了阿里云官方 SDK 并迁移到使用 OkHttpClient 发送 Web API。
- 移除了腾讯云官方 SDK 并迁移到使用 OkHttpClient 发送 Web API。