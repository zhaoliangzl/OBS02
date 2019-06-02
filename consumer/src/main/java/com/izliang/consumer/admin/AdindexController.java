package com.izliang.consumer.admin;


import com.izliang.consumer.service.AdminFeignService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Title: FeignController
 * @ProjectName springcloud_feign
 * @Description: 测试Feign
 * @Author WeiShiHuai
 * @Date 2018/9/10 15:28
 * 注入FeignClient，调用feignClient的方法实现远程方法调用
 */
@RestController
@RequestMapping(value = "/adindex")
public class AdindexController {

    @Autowired
    private AdminFeignService adminFeignService;

    /**
     * 使用http://localhost:2222/getInfo访问，实际上A服务会通过FeignClient调用服务B提供的getInfo接口
     *
     * @param id
     * @return
     */
    @GetMapping("/welcome")
    String getList(@RequestParam("id")int id){
        return adminFeignService.welcome(id);
    }



}