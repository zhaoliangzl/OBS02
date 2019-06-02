package com.izliang.consumer.admin;



import com.alibaba.fastjson.JSONObject;
import com.izliang.consumer.service.AdminFeignService;
import com.izliang.consumer.service.ServiceBFeignClient;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
@RequestMapping(value = "/adclazz")
public class AdClazzController {

    @Autowired
    private AdminFeignService adminFeignService;

    /**
     * 使用http://localhost:2222/getInfo访问，实际上A服务会通过FeignClient调用服务B提供的getInfo接口
     *
     * @param page
     * @return
     */
    @GetMapping("/list")
    String getList(@RequestParam("page")int page){
        return adminFeignService.clazzList(page);
    }

    @GetMapping("/get")
    String alllist(
            @RequestParam("id")int id
    ){
        return adminFeignService.getClazz(id);
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public String update(
            @ApiParam("分类id")@RequestParam("id")int id,
            @ApiParam("分类名称")@RequestParam("name")String name,
            @ApiParam("分类标识")@RequestParam("mark")String mark
    ) {
        return adminFeignService.clazzUpdate(id, name, mark);
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public String add(
            @ApiParam("分类名称")@RequestParam("name")String name,
            @ApiParam("分类标识")@RequestParam("mark")String mark
    ) {
        return  adminFeignService.clazzAdd(name,mark);
    }

    @RequestMapping(value = "/del",method = RequestMethod.DELETE)
    public String del(
            @ApiParam("id")@RequestParam("id")int id
    ) {
        return adminFeignService.delClazz(id);
    }


}