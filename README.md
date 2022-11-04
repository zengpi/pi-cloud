# pi-cloud

## 简介

pi-cloud 是基于 Spring Cloud 2021 & Spring Cloud Alibaba 2021、Spring Boot 2.6、MyBatisPlus、Spring Ahorization Server 0.3.1 等最新主流技术栈构建的后台管理系统。

## 特性

- 基于 Spring Cloud 2021 & Spring Cloud Alibaba 2021 提供微服务开发的一站式解决方案。
- 基于 Spring Authorization Server 0.3.1、OAuth 2.0 Resource Server、JWT 的统一认证鉴权。
- 只保留核心功能，无过度自定义封装，易于学习和功能扩展。

## 预览

| ![image-20221104164240613.png](https://gitee.com/linjiabin100/pi-cloud-resource/raw/master/imgs/image-20221104164240613.png) | ![image-20221104164357964](https://gitee.com/linjiabin100/pi-cloud-resource/raw/master/imgs/image-20221104164357964.png) |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| ![image-20221104164416152](https://gitee.com/linjiabin100/pi-cloud-resource/raw/master/imgs/image-20221104164416152.png) | ![image-20221104164428444](https://gitee.com/linjiabin100/pi-cloud-resource/raw/master/imgs/image-20221104164428444.png) |

## 源码

|      | Gitee                                           | GitHub                                     |
| ---- | ----------------------------------------------- | ------------------------------------------ |
| 后端 | https://gitee.com/linjiabin100/pi-cloud.git     | https://github.com/zengpi/pi-cloud.git     |
| 前端 | https://gitee.com/linjiabin100/pi-cloud-web.git | https://github.com/zengpi/pi-cloud-web.git |

## 参考文档

一定要阅读 pi-cloud 的 [参考文档](https://www.yuque.com/docs/share/b503de99-4dad-4df3-8a2e-facdeca57c9e) ，它描述了开发、运行 pi-cloud 的必要信息以及核心原理。

阅读 [个人博客](https://www.cnblogs.com/zn-pi/) 也是一个不错的选择，它是对文档很好的一个补充，阅读它会对项目有更深的理解。

## 项目模块

```
- pi-cloud
	- pi-common					公共模块
		- pi-common-feign		OpenFeign
		- pi-common-mybatis		MyBatis
		- pi-common-redis		Redis
		- pi-common-util		工具
		- pi-common-security	安全
		- pi-common-web			Web
	- pi-core					系统核心模块
		- pi-auth 				授权服务【8007】
		- pi-admin 				管理服务【8017】
			- pi-admin-api		服务调用公共 API 模块
			- pi-admin-biz		业务处理模块
		- pi-gateway 			网关服务【9731】
```

## 快速开始

### 系统要求

在开始之前，您需要确保您的计算机上安装了必要的环境。

pi-cloud 需要 Java 8，同时支持 Maven 3.5 及以上版本。实际上，pi-cloud 是在 Jdk 1.8.0_161下开发的。

此外，要保证 pi-cloud 的正常运行，还需要在您的计算机中至少存在：Nacos、MySQL、Redis。下表是以上环境版本清单：

|                                                          | 版本      |
| -------------------------------------------------------- | --------- |
| * Jdk                                                    | 1.8.0_161 |
| * [Maven](https://www.cnblogs.com/zn-pi/p/16850827.html) | 3.6.3     |
| * MySQL                                                  | 8.0.31    |
| * Redis                                                  | 6.2.7     |
| * Nacos                                                  | 2.1.0     |

安装方式请点击上表对应链接或查看官网。

**注意：项目中 MySQL 默认用户名密码均为 root，Redis 需要密码 123456。**

### 项目下载

```bash
$ git clone https://github.com/zengpi/pi-cloud.git
```

### 修改配置

pi-cloud 中的 ip 均设置为 pi，如果不想修改它，您需要修改 hosts 文件。Windows 中 hosts 文件位于 C:\Windows\System32\drivers\etc\hosts，打开该文件，在文件末尾追加：

```tex
192.168.126.128 pi
192.168.126.128 pi-redis
192.168.126.128 pi-nacos
```

其中，192.168.126.128 为我的虚拟机的 ip，您需要将它修改为你自己的。

为方便修改，您还可以选择使用工具 [SwitchHosts](https://swh.app/zh)，可按指引进行操作。

**注意：如果 hosts 文件无法写入内容，请将该文件属性的只读复选框取消勾选，具体是：右键 hosts -> 属性 -> 取消勾选“只读”复选框**

### 导入 Nacos

打开浏览器，访问 http://pi-nacos:8848，输入用户名密码（nacos/nacos），在配置管理的配置列表中，点击导入配置，选择项目目录下的 `doc/nacos_config_export_xxx.zip`，确认即可。

如果你的 MySQL 和 Redis 的密码与系统要求中的不一致，您需要修改它们。

分别编辑 pi-admin、pi-auth 和 pi-gateway，找到类似下面的配置，修改成符合你的要求的配置：

```yaml
spring:
    dataSource:
        type: com.alibaba.druid.pool.DruidDataSource
        username: root
        password: root
        url: jdbc:mysql://pi:3306/pi
    redis:
        host: pi-redis
        password: 123456
```

### 导入数据库

新建数据库 pi，并将项目目录下的 `doc/pi-cloud_1.0.0-SNAPSHOT.sql` 导入到新建的数据库中。

### 启动项目

核心服务启动位置：

```tex
pi-core/pi-admin/pi-admin-boot/**/AdminApp.java
pi-core/pi-auth/**/AuthApp.java
pi-core/pi-gateway/**/Gateway.java
```

