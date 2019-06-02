package com.izliang.consumer.controller;


import com.alibaba.fastjson.JSONObject;
import com.izliang.consumer.service.ServiceBFeignClient;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sun.dc.pr.PRError;

@RestController
@RequestMapping(value = "/adminlogin")
public class AdminLoginController {

    @Autowired
    private ServiceBFeignClient serviceBFeignClient;


    @RequestMapping(value = "/login",method = RequestMethod.POST)
    String login(
            @RequestParam("username")String username, @RequestParam("password")String password

    ){
        System.out.println("username = [" + username + "], password = [" + password + "]");
        return serviceBFeignClient.adminLogin(username,password);
    }


}
