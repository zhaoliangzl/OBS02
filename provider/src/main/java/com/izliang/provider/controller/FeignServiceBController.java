package com.izliang.provider.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Title: FeignServiceBController
 * @ProjectName springcloud_feign
 * @Description: 服务BController
 * @Author WeiShiHuai
 * @Date 2018/9/10 15:07
 */
@RestController
public class FeignServiceBController {

    private static Logger logger = LoggerFactory.getLogger(FeignServiceBController.class);

    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping("/getInfo")
    public String getInfo(@RequestParam("name") String name) {
        ServiceInstance serviceInstance = discoveryClient.getLocalServiceInstance();
        String host = serviceInstance.getHost();
        Integer port = serviceInstance.getPort();
        String info = "hello, name = " + name + ", host = " + host + ", port = " + port;
        logger.info(info);
        return info;
    }

}