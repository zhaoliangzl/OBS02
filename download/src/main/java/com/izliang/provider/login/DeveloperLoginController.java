package com.izliang.provider.login;


import com.alibaba.fastjson.JSONObject;
import com.google.common.util.concurrent.UncheckedTimeoutException;

import com.izliang.provider.model.Developer;
import com.izliang.provider.model.tmp.TmpForSession;
import com.izliang.provider.repo.DeveloperRepository;
import com.izliang.provider.repo.TmpForSessionRepository;
import com.izliang.provider.service.DeveloperService;
import com.izliang.provider.service.MailService;
import com.izliang.provider.utils.Result;
import com.izliang.provider.utils.Utils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Random;

@RestController
@RequestMapping(value = "/developerlogin")
@Api(value = "开发者登陆",tags = "developerlogin",description = "开发者登陆")
public class DeveloperLoginController {

    @Autowired
    private DeveloperRepository developerRepository;

    @Autowired
    private DeveloperService developerService;

    //邮件发送服务
    @Autowired
    private MailService mailService;

    @Autowired
    private TmpForSessionRepository tmpForSessionRepository;

    //开发者登陆
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ApiOperation(
            value = "开发者登陆",notes = "开发者登陆",
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

        Developer developer = developerRepository.findDeveloperByUsernameAndPassword(username, password);

        if(developer == null){
            return Result.Result(404,"用户不存在或者账号密码错误","");
        }else{
            session.setAttribute("dev_username",developer.getUsername());
            session.setAttribute("dev_name",developer.getName());
            session.setAttribute("dev_id",developer.getId());
            return Result.Result(200,"",developer);
        }
    }

    //开发者注册
    //开发者登陆
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @ApiOperation(
            value = "开发者注册",notes = "开发者注册,用户需要提交code，邮箱发送邮件后，将code存储到session中，再从session中获取code，是否匹配，此处还有检测开发者是否已经注册",
            response = Result.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "开发者注册成功"),
            @ApiResponse(code = 402,message = "邮箱验证码不存在，请重新获取邮箱验证码"),
            @ApiResponse(code = 403,message = "邮箱验证码错误"),
            @ApiResponse(code = 404,message = "开发者注册失败，用户名可能重复"),
            @ApiResponse(code = 405,message = "注册失败")
    })
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "username",value = "用户名",required = true),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "password",value = "密码",required = true),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "name",value = "姓名",required = true),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "email",value = "邮箱",required = true),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "code",value = "邮箱验证码",required = true),
    })
    public JSONObject add(
            @ApiParam("username")@RequestParam("username")String username,
            @ApiParam("email")@RequestParam("email")String email,
            @ApiParam("password")@RequestParam("password")String password,
            @ApiParam("name")@RequestParam("name")String name,
            @ApiParam("code")@RequestParam("code")String code
    ) {

        //验证邮箱
        TmpForSession tmpForSession = tmpForSessionRepository.findFirstByEmailOrderByIdDesc(email);

        String codeSession = tmpForSession.getCode();
        System.out.println("获取到的session"+codeSession);
        if(codeSession != null){
            if(code.equals(codeSession)){
                //检查用户名是否已经被注册过
                int flag = developerRepository.countAllByUsername(username);
                flag += developerRepository.countAllByUsername(email);
                if(flag>0){
                    //已经注册过了
                    return Result.Result(404,"","开发者注册失败，用户名,邮箱可能重复");
                }else{
                    //可以注册
                    Developer developer = new Developer();
                    developer.setUsername(username);
                    developer.setPassword(password);
                    developer.setName(name);
                    developer.setEamil(email);
                    developer.setTime(Utils.getNowDate());
                    developer.setTotalSize(50*1024*1024);
                    developer.setUseSize(0);
                    developer.setTotalFlow(100*1024*1024);
                    developer.setUseFlow(0);

                    Developer developer1 = developerRepository.save(developer);

                    if(developer1 == null){
                        return Result.Result(405,"注册失败","");
                    }else {
                        return Result.Result(200,"",developer1);
                    }
                }
            }else {
                return Result.Result(403,"邮箱验证码错误","");
            }
        }else{
            return Result.Result(402,"邮箱验证码不存在，请重新获取邮箱验证码","");
        }
    }


    @RequestMapping(value = "/getcheckcode",method = RequestMethod.GET)
    @ApiOperation(
            value = "邮箱验证码发送，并且存储在session中",notes = "邮箱验证码获取",
            response = Result.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "验证码获取成功"),
            @ApiResponse(code = 405,message = "验证码获取失败")
    })
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "email",value = "用户邮箱",required = true)
    })
    public JSONObject getCheckCode(
            @ApiParam("email")@RequestParam("email")String email
    ){
        String checkCode = String.valueOf(new Random().nextInt(899999) + 100000);
        String message = "您的注册验证码为："+checkCode;
        try {
            mailService.sendSimpleMail(email, "注册验证码-欢迎您使用Obs云存储关系系统", message);
        }catch (Exception e){
            return Result.Result(405,"验证码获取失败，邮件发送失败","");
        }

        TmpForSession tmpForSession1 = new TmpForSession();
        tmpForSession1.setCode(checkCode);
        tmpForSession1.setEmail(email);
        tmpForSessionRepository.save(tmpForSession1);

        return Result.Result(200,"","");
    }

    /*
    * 用户忘记密码的邮箱验证码获取
    *
    * **/
    @RequestMapping(value = "/getcheckmail",method = RequestMethod.GET)
    @ApiOperation(
            value = "用户忘记密码的邮箱验证码获取",notes = "用户找回自己的密码，修改密码，需要查询用户是否在系统中有邮箱，如果有发送邮箱验证码，如果没有返回错误",
            response = Result.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "验证码获取成功"),
            @ApiResponse(code = 404,message = "该邮箱不存在"),
            @ApiResponse(code = 405,message = "验证码获取失败")
    })
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "email",value = "用户邮箱",required = true)
    })
    public JSONObject getcheckmail(
            @ApiParam("email")@RequestParam("email")String email,
            HttpSession session
    ){

        Integer num = developerRepository.countAllByEamil(email);
        System.out.println(num);
        if(num>0){
            Developer developer = developerRepository.findDeveloperByEamil(email);
            System.out.println(developer.toString());
            String checkCode = String.valueOf(new Random().nextInt(899999) + 100000);
            String message = "您的注册验证码为："+checkCode;
            try {
                mailService.sendSimpleMail(email, "找回密码验证码-欢迎您使用Obs云存储关系系统", message);
            }catch (Exception e){
                return Result.Result(405,"验证码获取失败，邮件发送失败","");
            }

            System.out.println("发送=>"+checkCode);
            session.setAttribute(email.trim() ,checkCode);
            return Result.Result(200,"",developer);
        }else{
            //没有找到用户有该邮箱
            return Result.Result(404,"该邮箱不存在","");
        }
    }

    @RequestMapping(value = "/getcheckmail2",method = RequestMethod.POST)
    @ApiOperation(
            value = "用户忘记密码的邮箱验证码获取",notes = "用户找回自己的密码，修改密码，需要查询用户是否在系统中有邮箱，如果有发送邮箱验证码，如果没有返回错误",
            response = Result.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "验证码获取成功"),
            @ApiResponse(code = 404,message = "该邮箱不存在"),
            @ApiResponse(code = 405,message = "验证的邮箱和发送验证码的邮箱不一致"),
            @ApiResponse(code = 406,message = "邮箱验证码错误")
    })
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "email",value = "用户邮箱",required = true),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "code",value = "用户提交的邮箱验证码",required = true)
    })
    public JSONObject getcheckmail2(
            @ApiParam("email")@RequestParam("email")String email,
            @ApiParam("code")@RequestParam("code")String code,
            HttpSession session
    ){

        Integer num = developerRepository.countAllByEamil(email);

        if(num>0){
            String c = (String) session.getAttribute(email.trim());
            if(c == null){
                //验证码不存在
                return Result.Result(405,"验证的邮箱和发送验证码的邮箱不一致","");
            }else{
                if(c.equals(code)){
                    Developer developer = developerRepository.findDeveloperByEamil(email);
                    return Result.Result(200,"",developer);
                }else{
                    return Result.Result(406,"邮箱验证码错误","");
                }
            }
        }else{
            //没有找到用户有该邮箱
            return Result.Result(404,"该邮箱不存在","");
        }
    }

    //用户修改密码
    @RequestMapping(value = "/changepwd",method = RequestMethod.POST)
    @ApiOperation(
            value = "用户忘记密码的邮箱验证码获取",notes = "用户找回自己的密码，修改密码，需要查询用户是否在系统中有邮箱，如果有发送邮箱验证码，如果没有返回错误",
            response = Result.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "验证码获取成功"),
            @ApiResponse(code = 404,message = "该邮箱不存在"),
            @ApiResponse(code = 405,message = "验证的邮箱和发送验证码的邮箱不一致"),
            @ApiResponse(code = 406,message = "邮箱验证码错误"),
            @ApiResponse(code = 407,message = "修改密码失败")
    })
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "email",value = "用户邮箱",required = true),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "code",value = "用户提交的邮箱验证码",required = true),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "password",value = "用户新密码",required = true)
    })
    public JSONObject changepwd(
            @ApiParam("email")@RequestParam("email")String email,
            @ApiParam("code")@RequestParam("code")String code,
            @ApiParam("password")@RequestParam("password")String password,
            HttpSession session
    ){

        Integer num = developerRepository.countAllByEamil(email);

        if(num>0){
            String c = (String) session.getAttribute(email.trim());
            if(c == null){
                //验证码不存在
                return Result.Result(405,"验证的邮箱和发送验证码的邮箱不一致","");
            }else{
                if(c.equals(code)){
                    Developer developer = developerRepository.findDeveloperByEamil(email);
                    developer.setPassword(password);
                    Developer developer1 = developerRepository.save(developer);
                    if(developer1 == null){
                        return Result.Result(406,"修改密码失败","");
                    }else{
                        return Result.Result(200,"",developer);
                    }
                }else{
                    return Result.Result(406,"邮箱验证码错误","");
                }
            }
        }else{
            //没有找到用户有该邮箱
            return Result.Result(404,"该邮箱不存在","");
        }
    }


}
