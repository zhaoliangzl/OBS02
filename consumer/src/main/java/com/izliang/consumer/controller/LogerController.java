package com.izliang.consumer.controller;

import com.alibaba.fastjson.JSONObject;
import com.izliang.consumer.service.ServiceBFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/loger")
public class LogerController {

    @Autowired
    private ServiceBFeignClient serviceBFeignClient;

    @RequestMapping(value = "/log",method = RequestMethod.GET)
    public String log(@RequestParam("id")int id){
        return serviceBFeignClient.log(id);
    }



}
