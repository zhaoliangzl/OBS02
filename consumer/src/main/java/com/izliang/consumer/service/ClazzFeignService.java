package com.izliang.consumer.service;


import com.alibaba.fastjson.JSONObject;
import com.izliang.consumer.fallback.ServiceBFeignClientFallback;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Title: ServiceBFeignClient
 * @ProjectName springcloud_feign
 * @Description: FeignClient
 * @Author WeiShiHuai
 * @Date 2018/9/10 15:15
 * 1. FeignClient必须是一个接口interface
 * 2. 必须加上@FeignClient指定需要调用哪个服务的接口
 * 3. Feign默认集成了Ribbon，所以通过Feign也可以实现服务的负载均衡调用(轮询方式)。
 *
 * Feign的实现的过程大致如下：
a. 首先通过@EnableFeignClients注解开启FeignClient
b. 根据Feign的规则实现接口，并加@FeignClient注解
c. 程序启动后，会进行包扫描，扫描所有的@ FeignClient的注解的类，并将这些信息注入到ioc容器中。
d. 当接口的方法被调用，通过jdk的代理，来生成具体的RequestTemplate
e. RequestTemplate在生成Request
f. Request交给Client去处理，其中Client可以是HttpUrlConnection、HttpClient也可以是Okhttp
g. 最后Client被封装到LoadBalanceClient类，这个类结合类Ribbon做到了负载均衡
 *
 */

//@FeignClient注解通过value指定调用的服务名称，对应application.yml的application-name，如本例为eureka-feign-service-b
//通过fallback指定远程服务调用失败的回调方法，也叫服务降级处理,回调类必须实现使用@FeignClient标识的接口（implements ServiceBFeignClient）
//使用@FeignClient("eureka-feign-service-b")注解来绑定该接口对应feign-service-b服务
@FeignClient(value = "eureka-feign-service-b", fallback = ServiceBFeignClientFallback.class)
public interface ClazzFeignService {

    /**
     * 编写Feign接口简便的方法：把具体需要远程调用的服务（如服务B）中的方法复制过来，去掉实现即可。
     *
     * @param
     * @return
     */



}