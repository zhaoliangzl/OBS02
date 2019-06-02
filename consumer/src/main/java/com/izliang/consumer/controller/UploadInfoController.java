package com.izliang.consumer.controller;

import com.alibaba.fastjson.JSONObject;
import com.izliang.consumer.service.ServiceBFeignClient;
import com.izliang.consumer.service.UploadFeignClient;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/uploadinfo")
@Api(value = "uploadinfo上传记录",tags = "uploadinfo",description = "对上传的数据进行管理")
public class UploadInfoController {

    @Autowired
    private ServiceBFeignClient serviceBFeignClient;

    //全局分页变量
    @Value(value = "${self.page.size}")
    private int pageSize;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String open(
            @ApiParam("page")@RequestParam("page")int page
    ) {
        return serviceBFeignClient.getUploadInfoList(page);
    }

    @RequestMapping(value = "/listbydev",method = RequestMethod.GET)
    public String listByDev(
            @ApiParam("page")@RequestParam("page")int page,
            @ApiParam("id")@RequestParam("id")int id
    ) {

        return serviceBFeignClient.getUploadInfoListByDev(id,page);
    }


}
