package com.izliang.consumer.controller;



import com.alibaba.fastjson.JSONObject;
import com.izliang.consumer.service.ClazzFeignService;
import com.izliang.consumer.service.ServiceBFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping(value = "/clazz")
public class ClazzController {

    @Autowired
    private ServiceBFeignClient serviceBFeignClient;

    /**
     * 使用http://localhost:2222/getInfo访问，实际上A服务会通过FeignClient调用服务B提供的getInfo接口
     *
     * @param page
     * @return
     */
    @GetMapping("/list")
    String getList(@RequestParam("page")int page){
        return serviceBFeignClient.getList(page);
    }

    @GetMapping("/alllist")
    String alllist(){
        return serviceBFeignClient.alllist();
    }


}