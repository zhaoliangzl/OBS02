package com.izliang.consumer.controller;



import com.izliang.consumer.service.ServiceBFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Title: FeignController
 * @ProjectName springcloud_feign
 * @Description: 测试Feign
 * @Author WeiShiHuai
 * @Date 2018/9/10 15:28
 * 注入FeignClient，调用feignClient的方法实现远程方法调用
 */
@RestController
public class FeignController {

    private static Logger logger = LoggerFactory.getLogger(FeignController.class);

    @Autowired
    private ServiceBFeignClient serviceBFeignClient;

    /**
     * 使用http://localhost:2222/getInfo访问，实际上A服务会通过FeignClient调用服务B提供的getInfo接口
     *
     * @param name
     * @return
     */
    @GetMapping("/getInfo")
    public String getInfo(@RequestParam("name") String name) {
        String info = serviceBFeignClient.getInfo(name);
        logger.info(info);
        return info;
    }

}