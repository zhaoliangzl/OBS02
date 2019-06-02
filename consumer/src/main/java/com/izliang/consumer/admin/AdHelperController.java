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
@RequestMapping(value = "/adhelper")
public class AdHelperController {

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
        return adminFeignService.helperList(page);
    }

    @GetMapping("/get")
    String alllist(
            @RequestParam("id")int id
    ){
        return adminFeignService.getHelper(id);
    }

    @RequestMapping(value = "/del",method = RequestMethod.DELETE)
    public String del(
            @ApiParam("id")@RequestParam("id")int id
    ) {
        return adminFeignService.delHelper(id);
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public String update(
            @ApiParam("分类id")@RequestParam("id")int id,
            @ApiParam("文档标题")@RequestParam("title")String title,
            @ApiParam("文档分类")@RequestParam("helperClassId")int helperClassId,
            @ApiParam("文档内容")@RequestParam("content")String content
    ) {
        return adminFeignService.updateHelper(id, title, helperClassId, content);
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public String add(
            @ApiParam("文档标题")@RequestParam("title")String title,
            @ApiParam("文档分类")@RequestParam("helperClassId")int helperClassId,
            @ApiParam("文档内容")@RequestParam("content")String content
    ) {

        System.out.println("title = [" + title + "], helperClassId = [" + helperClassId + "]");
        return  adminFeignService.addHelper(title, helperClassId, content);
    }




}