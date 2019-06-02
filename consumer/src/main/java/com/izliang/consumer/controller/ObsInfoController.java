package com.izliang.consumer.controller;


import com.alibaba.fastjson.JSONObject;
import com.izliang.consumer.service.ServiceBFeignClient;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/obs")
public class ObsInfoController {

    @Autowired
    private ServiceBFeignClient serviceBFeignClient;


    //全局分页变量
    @Value(value = "${self.page.size}")
    private int pageSize;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String open(
            @ApiParam("page")@RequestParam("page")int page
    ) {
        return serviceBFeignClient.getObsInfoList(page);
    }

    @RequestMapping(value = "/listforuser",method = RequestMethod.GET)
    public String listforuser(
            @ApiParam("page")@RequestParam("page")int page,
            @ApiParam("id")@RequestParam("id")int id
    ) {
        return serviceBFeignClient.getObsInfoListForUserByPage(page,id);
    }

    @RequestMapping(value = "/get",method = RequestMethod.GET)
    public String get(
            @ApiParam("id")@RequestParam("id")int id
    ) {
        return serviceBFeignClient.getObsInfo(id);
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public String add(
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
    ) {
        //在添加前，需要检查是否用重复的ak,sk,以及clazzid
        return serviceBFeignClient.addObsInfo(name,userId,clazzId,ak,sk,endPonit,bucketname,sharpSize,sharpFlowSize,delivery);

    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public String update(
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
    ) {

        return serviceBFeignClient.updateObsInfo(id,name,userId,clazzId,ak,sk,endPonit,bucketname,sharpSize,sharpFlowSize,delivery);
    }
    @RequestMapping(value = "/del",method = RequestMethod.DELETE)
    public String del(
            @ApiParam("id")@RequestParam("id")int id
    ) {
        return serviceBFeignClient.delObsInfo(id);
    }

    //用户获取obs可以上传的信息列表
    @RequestMapping(value = "/getobslistforuser",method = RequestMethod.GET)
    public String getobslistforuser(
            @ApiParam("id")@RequestParam("id")int id
    ) {
        return serviceBFeignClient.getObsInfoListByUser(id);
    }

    //用户获取obs可以上传的信息列表
    @RequestMapping(value = "/obsprogress",method = RequestMethod.GET)
    public String obsprogress(
            @ApiParam("id")@RequestParam("id")int id
    ) {
        return serviceBFeignClient.ObsProgress(id);
    }

    //用户获取obs可以上传的信息列表
    @RequestMapping(value = "/obstoclose",method = RequestMethod.GET)
    public String obsToClose(
            @ApiParam("id")@RequestParam("id")int id
    ) {
        return serviceBFeignClient.ObsClose(id);
    }

    @RequestMapping(value = "/obstoopen",method = RequestMethod.GET)
    public String obsToOpen(
            @ApiParam("id")@RequestParam("id")int id
    ) {
        return serviceBFeignClient.ObsToOpen(id);
    }


    //用户获取obs可以上传的信息列表
    @RequestMapping(value = "/obstoshen",method = RequestMethod.GET)
    public String obsToShen(
            @ApiParam("id")@RequestParam("id")int id
    ) {
        return serviceBFeignClient.ObsToShen(id);
    }



}
