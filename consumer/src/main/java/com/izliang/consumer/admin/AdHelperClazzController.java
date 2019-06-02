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
@RequestMapping(value = "/adhelperclass")
public class AdHelperClazzController {

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
        return adminFeignService.helperClassList(page);
    }

    @GetMapping("/alllist")
    String getAllList(){
        return adminFeignService.helperClassAllList();
    }

    @GetMapping("/get")
    String alllist(
            @RequestParam("id")int id
    ){
        return adminFeignService.getHelperClass(id);
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public String update(
            @ApiParam("分类id")@RequestParam("id")int id,
            @ApiParam("分类名称")@RequestParam("name")String name
    ) {
        return adminFeignService.updateHelperClass(id, name);
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public String add(
            @ApiParam("分类名称")@RequestParam("name")String name
    ) {
        return  adminFeignService.addHelperClass(name);
    }

    @RequestMapping(value = "/del",method = RequestMethod.DELETE)
    public String del(
            @ApiParam("id")@RequestParam("id")int id
    ) {
        return adminFeignService.delHelperClass(id);
    }


}