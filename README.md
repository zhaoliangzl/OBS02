# OBS02

SpringCloud分布式系统上传文件、fegin声明式服务调用，更新

eurake-server为eruake服务器，主要有配置

consumer为消费者服务器，所有接口进行fegin声明式调用，复写了负载均衡算法

provider为用户信息微服务，所有用户端接口都使用该微服务

admin为管理员信息微服务，所有管理员接口使用该服务

upload为上传微服务，所有上传相关的微服务均在此工程

task为日志微服务，负责定时分析用户信息和日志

