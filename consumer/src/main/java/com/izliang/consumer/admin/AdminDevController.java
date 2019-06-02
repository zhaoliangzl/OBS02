package com.izliang.consumer.admin;



import com.izliang.consumer.service.AdminFeignService;
import com.izliang.consumer.service.ServiceBFeignClient;
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
@RequestMapping(value = "/addev")
public class AdminDevController {

    @Autowired
    private AdminFeignService adminFeignService;

    /**
     * 使用http://localhost:2222/getInfo访问，实际上A服务会通过FeignClient调用服务B提供的getInfo接口
     *
     * @param page
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    String getList(@RequestParam("page")int page){
        return adminFeignService.developerList(page);
    }

    @RequestMapping(value = "/get",method = RequestMethod.GET)
    String get(
            @ApiParam("用户id")@RequestParam("id")int id
    ){
        return adminFeignService.getDeveloper(id);
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    String update(
            @ApiParam("用户id")@RequestParam("id")int id,
            @ApiParam("当月流量总计")@RequestParam("totalFlow")long totalFlow,
            @ApiParam("用户存储大小")@RequestParam("totalSize")long totalSize,
            @ApiParam("当月使用流量")@RequestParam("useFlow")long useFlow,
            @ApiParam("用户已经使用的存储大小")@RequestParam("useSize")long useSize
    ){
        return adminFeignService.update(id,totalFlow,totalSize,useFlow,useSize);
    }



}