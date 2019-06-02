package com.izliang.consumer.controller;

import com.alibaba.fastjson.JSONObject;
import com.izliang.consumer.service.ServiceBFeignClient;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Random;

@RestController
@RequestMapping(value = "/developerlogin")
public class DeveloperLoginController {

    @Autowired
    private ServiceBFeignClient serviceBFeignClient;

    //开发者登陆
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(
            @ApiParam("username")@RequestParam("username")String username,
            @ApiParam("password")@RequestParam("password")String password
    ) {
        return serviceBFeignClient.Developerlogin(username,password);
    }

    //开发者注册
    //开发者登陆
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public String add(
            @ApiParam("username")@RequestParam("username")String username,
            @ApiParam("email")@RequestParam("email")String email,
            @ApiParam("password")@RequestParam("password")String password,
            @ApiParam("name")@RequestParam("name")String name,
            @ApiParam("code")@RequestParam("code")String code
    ) {
        return serviceBFeignClient.registerDeveloper(username,email,password,name,code);
    }


    @RequestMapping(value = "/getcheckcode",method = RequestMethod.GET)
    public String getCheckCode(
            @ApiParam("email")@RequestParam("email")String email
    ){
        return serviceBFeignClient.getCheckCode(email);
    }

    /*
     * 用户忘记密码的邮箱验证码获取
     *
     * **/
    @RequestMapping(value = "/getcheckmail",method = RequestMethod.GET)
    public String getcheckmail(
            @ApiParam("email")@RequestParam("email")String email
    ){
        return serviceBFeignClient.getcheckmail(email);
    }

    @RequestMapping(value = "/getcheckmail2",method = RequestMethod.POST)
    public String getcheckmail2(
            @ApiParam("email")@RequestParam("email")String email,
            @ApiParam("code")@RequestParam("code")String code
    ){
        return serviceBFeignClient.getcheckmail2(email,code);
    }

    //用户修改密码
    @RequestMapping(value = "/changepwd",method = RequestMethod.POST)
    public String changepwd(
            @ApiParam("email")@RequestParam("email")String email,
            @ApiParam("code")@RequestParam("code")String code,
            @ApiParam("password")@RequestParam("password")String password
    ){
        return serviceBFeignClient.changepwd(email,code,password);
    }


}
