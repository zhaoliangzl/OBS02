package com.izliang.provider.login;


import com.alibaba.fastjson.JSONObject;

import com.izliang.provider.model.Admin;
import com.izliang.provider.repo.AdminRepository;
import com.izliang.provider.utils.Result;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Random;

@RestController
@RequestMapping(value = "/adminlogin")
@Api(value = "管理员登陆",tags = "adminlogin",description = "管理员登陆")
public class AdminLoginController {

    @Autowired
    private AdminRepository adminRepository;

    //开发者登陆
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ApiOperation(
            value = "管理员登陆",notes = "管理员登陆",
            response = Result.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "登陆成功"),
            @ApiResponse(code = 404,message = "用户不存在或者账号密码错误")
    })
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "username",value = "用户名",required = true),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "password",value = "密码",required = true),
    })
    public JSONObject login(
            @ApiParam("username")@RequestParam("username")String username,
            @ApiParam("password")@RequestParam("password")String password,
            HttpSession session
    ) {

        Admin developer = adminRepository.findAdminByUsernameAndPassword(username, password);

        if(developer == null){
            return Result.Result(404,"管理员不存在或者账号密码错误","");
        }else{
            session.setAttribute("admin_username",developer.getUsername());
            session.setAttribute("admin_id",developer.getId());
            return Result.Result(200,"",developer);
        }
    }



}
