package com.izliang.consumer.service;


import com.alibaba.fastjson.JSONObject;
import com.izliang.consumer.fallback.ServiceBFeignClientFallback;
import io.swagger.annotations.*;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Random;

/**
 * @Title: ServiceBFeignClient
 * @ProjectName springcloud_feign
 * @Description: FeignClient
 * @Author WeiShiHuai
 * @Date 2018/9/10 15:15
 * 1. FeignClient必须是一个接口interface
 * 2. 必须加上@FeignClient指定需要调用哪个服务的接口
 * 3. Feign默认集成了Ribbon，所以通过Feign也可以实现服务的负载均衡调用(轮询方式)。
 *
 * Feign的实现的过程大致如下：
a. 首先通过@EnableFeignClients注解开启FeignClient
b. 根据Feign的规则实现接口，并加@FeignClient注解
c. 程序启动后，会进行包扫描，扫描所有的@ FeignClient的注解的类，并将这些信息注入到ioc容器中。
d. 当接口的方法被调用，通过jdk的代理，来生成具体的RequestTemplate
e. RequestTemplate在生成Request
f. Request交给Client去处理，其中Client可以是HttpUrlConnection、HttpClient也可以是Okhttp
g. 最后Client被封装到LoadBalanceClient类，这个类结合类Ribbon做到了负载均衡
 *
 */

//@FeignClient注解通过value指定调用的服务名称，对应application.yml的application-name，如本例为eureka-feign-service-b
//通过fallback指定远程服务调用失败的回调方法，也叫服务降级处理,回调类必须实现使用@FeignClient标识的接口（implements ServiceBFeignClient）
//使用@FeignClient("eureka-feign-service-b")注解来绑定该接口对应feign-service-b服务
@FeignClient(value = "eureka-feign-service-b", fallback = ServiceBFeignClientFallback.class)
public interface ServiceBFeignClient {

    /**
     * 编写Feign接口简便的方法：把具体需要远程调用的服务（如服务B）中的方法复制过来，去掉实现即可。
     *
     * @param name
     * @return
     */
    @RequestMapping("/getInfo")
    String getInfo(@RequestParam("name") String name);

    @RequestMapping("/clazz/list")
    String getList(@RequestParam("page") int page);

    @RequestMapping("/clazz/alllist")
    String alllist();

    @RequestMapping(value = "/adminlogin/login",method = RequestMethod.POST)
    String adminLogin(@RequestParam("username")String username, @RequestParam("password")String password);

    @RequestMapping(value = "/obs/list",method = RequestMethod.GET)
    String getObsInfoList(@RequestParam("page") int page);

    @RequestMapping("/obs/listforuser")
    String getObsInfoListForUserByPage(@RequestParam("page") int page,@RequestParam("id") int id);

    @RequestMapping("/obs/get")
    String getObsInfo(@RequestParam("id") int id);

    @RequestMapping("/obs/add")
    String addObsInfo(
            @ApiParam("name")@RequestParam("name")String name,
            @ApiParam("userid")@RequestParam("userid")int userId,
            @ApiParam("clazzid")@RequestParam("clazzid")int clazzId,
            @ApiParam("ak")@RequestParam("ak")String ak,
            @ApiParam("sk")@RequestParam("sk")String sk,
            @ApiParam("endpoint")@RequestParam("endpoint")String endPonit,
            @ApiParam("bucketname")@RequestParam("bucketname")String bucketname,
            @ApiParam("sharpSize")@RequestParam("sharpSize")long sharpSize,
            @ApiParam("sharpFlowSize")@RequestParam("sharpFlowSize")long sharpFlowSize,
            @ApiParam("delivery")@RequestParam("delivery")boolean delivery
    );

    @RequestMapping("/obs/update")
    String updateObsInfo(
            @ApiParam("id")@RequestParam("id")int id,
            @ApiParam("name")@RequestParam("name")String name,
            @ApiParam("userid")@RequestParam("userid")int userId,
            @ApiParam("clazzid")@RequestParam("clazzid")int clazzId,
            @ApiParam("ak")@RequestParam("ak")String ak,
            @ApiParam("sk")@RequestParam("sk")String sk,
            @ApiParam("endpoint")@RequestParam("endpoint")String endPonit,
            @ApiParam("bucketname")@RequestParam("bucketname")String bucketname,
            @ApiParam("sharpSize")@RequestParam("sharpSize")long sharpSize,
            @ApiParam("sharpFlowSize")@RequestParam("sharpFlowSize")long sharpFlowSize,
            @ApiParam("delivery")@RequestParam("delivery")boolean delivery
    );

    @RequestMapping("/obs/del")
    String delObsInfo(@RequestParam("id") int id);

    @RequestMapping("/obs/getobslistforuser")
    String getObsInfoListByUser(@RequestParam("id") int id);

    @RequestMapping("/obs/obsprogress")
    String ObsProgress(@RequestParam("id") int id);

    @RequestMapping("/obs/obstoclose")
    String ObsClose(@RequestParam("id") int id);

    @RequestMapping("/obs/obstoshen")
    String ObsToShen(@RequestParam("id") int id);

    @RequestMapping("/obs/obstoopen")
    String ObsToOpen(@RequestParam("id") int id);

    @RequestMapping(value = "/developer/list",method = RequestMethod.GET)
    String developerList( @ApiParam("page")@RequestParam("page")int page);

    @RequestMapping(value = "/developer/get",method = RequestMethod.GET)
    public String getDeveloper(
            @ApiParam("id")@RequestParam("id")int id
    );

    @RequestMapping(value = "/developer/update",method = RequestMethod.POST)

    public String updateDeveloper(
            @ApiParam("id")@RequestParam("id")int id
    );

    @RequestMapping(value = "/developer/add",method = RequestMethod.POST)

    public String addDeveloper(
            @ApiParam("id")@RequestParam("id")int id
    );

    @RequestMapping(value = "/developer/del",method = RequestMethod.DELETE)

    public String delDeveloper(
            @ApiParam("id")@RequestParam("id")int id
    );
    /**
     * 获取开发者的资源上传链接
     *
     * **/
    @RequestMapping(value = "/developer/getuploadlink",method = RequestMethod.GET)

    public String getUploadLink(
            @ApiParam("id")@RequestParam("id")int id
    );
    /**
     * 开发者生成对应的上传链接
     * */
    @RequestMapping(value = "/developer/generateuploadlink",method = RequestMethod.GET)

    public String generateUploadLink(
            @ApiParam("id")@RequestParam("id")int id
    );


    //开发者登陆
    @RequestMapping(value = "/developerlogin/login",method = RequestMethod.POST)
    public String Developerlogin(
            @ApiParam("username")@RequestParam("username")String username,
            @ApiParam("password")@RequestParam("password")String password
    );

    //开发者注册
    //开发者登陆
    @RequestMapping(value = "/developerlogin/register",method = RequestMethod.POST)
    public String registerDeveloper(
            @ApiParam("username")@RequestParam("username")String username,
            @ApiParam("email")@RequestParam("email")String email,
            @ApiParam("password")@RequestParam("password")String password,
            @ApiParam("name")@RequestParam("name")String name,
            @ApiParam("code")@RequestParam("code")String code
    );


    @RequestMapping(value = "/developerlogin/getcheckcode",method = RequestMethod.GET)
    public String getCheckCode(
            @ApiParam("email")@RequestParam("email")String email
    );

    /*
     * 用户忘记密码的邮箱验证码获取
     *
     * **/
    @RequestMapping(value = "/developerlogin/getcheckmail",method = RequestMethod.GET)
    public String getcheckmail(
            @ApiParam("email")@RequestParam("email")String email
    );

    @RequestMapping(value = "/developerlogin/getcheckmail2",method = RequestMethod.POST)
    public String getcheckmail2(
            @ApiParam("email")@RequestParam("email")String email,
            @ApiParam("code")@RequestParam("code")String code
    );

    //用户修改密码
    @RequestMapping(value = "/developerlogin/changepwd",method = RequestMethod.POST)
    public String changepwd(
            @ApiParam("email")@RequestParam("email")String email,
            @ApiParam("code")@RequestParam("code")String code,
            @ApiParam("password")@RequestParam("password")String password
    );

    @RequestMapping(value = "/uploadinfo/listbydev",method = RequestMethod.GET)
    public String getUploadInfoListByDev(
            @ApiParam("id")@RequestParam("id")int id,
            @ApiParam("page")@RequestParam("page")int page
    );

    @RequestMapping(value = "/uploadinfo/list",method = RequestMethod.GET)
    public String getUploadInfoList(
            @ApiParam("page")@RequestParam("page")int page
    );

    @RequestMapping(value = "/helper/all",method = RequestMethod.GET)
    public String allHelper();


    @RequestMapping(value = "/loger/log",method = RequestMethod.GET)
    public String log(@RequestParam("id")int id);

}