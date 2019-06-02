package com.izliang.consumer.service;


import com.alibaba.fastjson.JSONObject;
import com.izliang.consumer.fallback.AdminFeignClientFallback;
import io.swagger.annotations.*;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
@FeignClient(value = "eureka-feign-service-admin", fallback = AdminFeignClientFallback.class)
public interface AdminFeignService {

    /**
     * 编写Feign接口简便的方法：把具体需要远程调用的服务（如服务B）中的方法复制过来，去掉实现即可。
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/developer/list",method = RequestMethod.GET)
    String developerList( @ApiParam("page")@RequestParam("page")int page);

    @RequestMapping(value = "/developer/get",method = RequestMethod.GET)
    public String getDeveloper(
            @ApiParam("id")@RequestParam("id")int id
    );

    @RequestMapping(value = "/developer/update",method = RequestMethod.POST)
    public String update(
            @ApiParam("用户id")@RequestParam("id")int id,
            @ApiParam("当月流量总计")@RequestParam("totalFlow")long totalFlow,
            @ApiParam("用户存储大小")@RequestParam("totalSize")long totalSize,
            @ApiParam("当月使用流量")@RequestParam("useFlow")long useFlow,
            @ApiParam("用户已经使用的存储大小")@RequestParam("useSize")long useSize
    );

    @RequestMapping(value = "/clazz/list",method = RequestMethod.GET)
    String clazzList( @ApiParam("page")@RequestParam("page")int page);

    @RequestMapping(value = "/clazz/get",method = RequestMethod.GET)
    public String getClazz(
            @ApiParam("id")@RequestParam("id")int id
    );

    @RequestMapping(value = "/clazz/del",method = RequestMethod.DELETE)
    public String delClazz(
            @ApiParam("id")@RequestParam("id")int id
    );

    @RequestMapping(value = "/clazz/update",method = RequestMethod.POST)
    public String clazzUpdate(
            @ApiParam("分类id")@RequestParam("id")int id,
            @ApiParam("分类名称")@RequestParam("name")String name,
            @ApiParam("分类标识")@RequestParam("mark")String mark
    );
    @RequestMapping(value = "/clazz/add",method = RequestMethod.POST)
    public String clazzAdd(
            @ApiParam("分类名称")@RequestParam("name")String name,
            @ApiParam("分类标识")@RequestParam("mark")String mark
    );

    @RequestMapping(value = "/helper/list",method = RequestMethod.GET)
    public String helperList(
            @ApiParam("page")@RequestParam("page")int page
    );

    @RequestMapping(value = "/helper/get",method = RequestMethod.GET)
    public String getHelper(
            @ApiParam("id")@RequestParam("id")int id
    );

    @RequestMapping(value = "/helper/update",method = RequestMethod.POST)
    public String updateHelper(
            @ApiParam("文档id")@RequestParam("id")int id,
            @ApiParam("文档标题")@RequestParam("title")String title,
            @ApiParam("文档分类")@RequestParam("helperClassId")int helperClassId,
            @ApiParam("文档内容")@RequestParam("content")String content
    );

    @RequestMapping(value = "/helper/add",method = RequestMethod.POST)
    public String addHelper(
            @ApiParam("文档标题")@RequestParam("title")String title,
            @ApiParam("文档分类")@RequestParam("helperClassId")int helperClassId,
            @ApiParam("文档内容")@RequestParam("content")String content
    );

    @RequestMapping(value = "/helper/del",method = RequestMethod.DELETE)
    public String delHelper(
            @ApiParam("id")@RequestParam("id")int id
    );



    @RequestMapping(value = "/helperclazz/list",method = RequestMethod.GET)
    public String helperClassList(
            @ApiParam("page")@RequestParam("page")int page
    );

    @RequestMapping(value = "/helperclazz/alllist",method = RequestMethod.GET)
    public String helperClassAllList(
    );


    @RequestMapping(value = "/helperclazz/get",method = RequestMethod.GET)
    public String getHelperClass(
            @ApiParam("id")@RequestParam("id")int id
    );

    @RequestMapping(value = "/helperclazz/update",method = RequestMethod.POST)
    public String updateHelperClass(
            @ApiParam("文档id")@RequestParam("id")int id,
            @ApiParam("文档内容")@RequestParam("name")String name
    );

    @RequestMapping(value = "/helperclazz/add",method = RequestMethod.POST)
    public String addHelperClass(
            @ApiParam("文档内容")@RequestParam("name")String name
    );

    @RequestMapping(value = "/helperclazz/del",method = RequestMethod.DELETE)
    public String delHelperClass(
            @ApiParam("id")@RequestParam("id")int id
    );


    @RequestMapping(value = "/notice/list",method = RequestMethod.GET)
    public String NoticeList(
            @ApiParam("page")@RequestParam("page")int page
    );

    @RequestMapping(value = "/logger/list",method = RequestMethod.GET)
    public String LoggerList(
            @ApiParam("page")@RequestParam("page")int page
    );

    @RequestMapping(value = "/notice/listbydev",method = RequestMethod.GET)
    public String NoticeListByDev(
            @ApiParam("page")@RequestParam("page")int page
    );


    @RequestMapping(value = "/notice/get",method = RequestMethod.GET)
    public String getNotice(
            @ApiParam("id")@RequestParam("id")int id
    );

    @RequestMapping(value = "/notice/update",method = RequestMethod.POST)
    public String updateNotice(
            @ApiParam("文档id")@RequestParam("id")int id,
            @ApiParam("公告标题")@RequestParam("title")String title,
            @ApiParam("文档内容")@RequestParam("content")String content,
            @ApiParam("是否显示")@RequestParam("enable")Boolean  enable
    );

    @RequestMapping(value = "/notice/add",method = RequestMethod.POST)
    public String addNotice(
            @ApiParam("公告标题")@RequestParam("title")String title,
            @ApiParam("文档内容")@RequestParam("content")String content,
            @ApiParam("是否显示")@RequestParam("enable")Boolean  enable
    );

    @RequestMapping(value = "/notice/del",method = RequestMethod.DELETE)
    public String delNotice(
            @ApiParam("id")@RequestParam("id")int id
    );

    @RequestMapping(value = "/welcome/index",method = RequestMethod.GET)
    public String welcome(
            @ApiParam("id")@RequestParam("id")int id
    );

}