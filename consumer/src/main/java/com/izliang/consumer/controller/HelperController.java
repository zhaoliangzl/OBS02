package com.izliang.consumer.controller;

import com.alibaba.fastjson.JSONObject;
import com.izliang.consumer.service.ServiceBFeignClient;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/helper")
@Api(value = "帮助文档接口",tags = "helper",description = "帮助文档接口")
public class HelperController {

    @Autowired
    private ServiceBFeignClient serviceBFeignClient;

    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public String allHelper(
    ) {
        return serviceBFeignClient.allHelper();
    }


}
