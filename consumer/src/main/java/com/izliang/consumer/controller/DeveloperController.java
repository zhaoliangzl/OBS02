package com.izliang.consumer.controller;

import com.alibaba.fastjson.JSONObject;
import com.izliang.consumer.service.ServiceBFeignClient;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/developer")
public class DeveloperController {

    @Autowired
    private ServiceBFeignClient serviceBFeignClient;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String open(
            @ApiParam("page")@RequestParam("page")int page
    ) {
        return serviceBFeignClient.developerList(page);
    }

    @RequestMapping(value = "/get",method = RequestMethod.GET)
    public String get(
            @ApiParam("id")@RequestParam("id")int id
    ) {
        return serviceBFeignClient.getDeveloper(id);
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public String update(
            @ApiParam("id")@RequestParam("id")int id
    ) {
        return serviceBFeignClient.updateDeveloper(id);
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public String add(
            @ApiParam("id")@RequestParam("id")int id
    ) {
        return serviceBFeignClient.addDeveloper(id);
    }

    @RequestMapping(value = "/del",method = RequestMethod.DELETE)
    public String del(
            @ApiParam("id")@RequestParam("id")int id
    ) {
        return serviceBFeignClient.delDeveloper(id);
    }

    /**
     * 获取开发者的资源上传链接
     *
     * **/
    @RequestMapping(value = "/getuploadlink",method = RequestMethod.GET)
    public String getUploadLink(
            @ApiParam("id")@RequestParam("id")int id
    ) {
        return serviceBFeignClient.getUploadLink(id);
    }

    /**
     * 开发者生成对应的上传链接
     * */
    @RequestMapping(value = "/generateuploadlink",method = RequestMethod.GET)
    public String generateUploadLink(
            @ApiParam("id")@RequestParam("id")int id
    ) {
        return serviceBFeignClient.generateUploadLink(id);
    }



}
