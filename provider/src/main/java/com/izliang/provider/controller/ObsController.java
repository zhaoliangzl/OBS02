package com.izliang.provider.controller;


import com.alibaba.fastjson.JSONObject;
import com.izliang.provider.model.ObsInfo;
import com.izliang.provider.repo.OBSInfoRepository;
import com.izliang.provider.service.ObsInfoService;
import com.izliang.provider.utils.Result;
import com.izliang.provider.utils.Utils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/obs")
@Api(value = "OBS数据库数据",tags = "obs",description = "对OBS的密钥，地址等进行管理")
public class ObsController {

    @Autowired
    private OBSInfoRepository obsInfoRepository;

    @Autowired
    private OBSInfoRepository obsInfoService;

    //全局分页变量
    @Value(value = "${self.page.size}")
    private int pageSize;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @ApiOperation(
            value = "获取obs信息列表",notes = "获取obs信息列表",
            response = ObsInfo.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "返回obs数据的列表")
    })
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query",dataType = "int",name = "page",value = "页码",required = true),
    })
    public JSONObject open(
            @ApiParam("page")@RequestParam("page")int page
    ) {

        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest((page-1),pageSize,sort);
        Page<ObsInfo> datas = obsInfoRepository.findAll(pageable);

        return Result.ResultPage(200,"",datas.getContent(),page,datas.getTotalPages(),(int) datas.getTotalElements());
    }

    @RequestMapping(value = "/listforuser",method = RequestMethod.GET)
    @ApiOperation(
            value = "获取obs信息列表",notes = "获取obs信息列表",
            response = ObsInfo.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "返回obs数据的列表")
    })
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query",dataType = "int",name = "page",value = "页码",required = true),
            @ApiImplicitParam(paramType = "query",dataType = "int",name = "id",value = "用户id",required = true),
    })
    public JSONObject listforuser(
            @ApiParam("page")@RequestParam("page")int page,
            @ApiParam("id")@RequestParam("id")int id
    ) {

        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest((page-1),pageSize,sort);
        Page<ObsInfo> datas = obsInfoRepository.findPageObsInfosByUserId(id,pageable);

        return Result.ResultPage(200,"",datas.getContent(),page,datas.getTotalPages(),(int) datas.getTotalElements());
    }

    @RequestMapping(value = "/get",method = RequestMethod.GET)
    @ApiOperation(
            value = "获取单个obs数据",notes = "获取单个obs数据",
            response = ObsInfo.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "返回单个obs数据")
    })
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query",dataType = "int",name = "id",value = "信息id",required = true),
    })
    public JSONObject get(
            @ApiParam("id")@RequestParam("id")int id
    ) {

        ObsInfo obsInfo = obsInfoService.findOne(id);

        return Result.Result(200,"",obsInfo);
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ApiOperation(
            value = "添加单个obs数据",notes = "添加单个obs数据",
            response = ObsInfo.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "返回添加后的单个obs数据"),
            @ApiResponse(code = 405,message = "添加失败")
    })
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "name",value = "obs信息名称",required = true),
            @ApiImplicitParam(paramType = "query",dataType = "int",name = "userid",value = "用户id",required = true),
            @ApiImplicitParam(paramType = "query",dataType = "int",name = "clazzid",value = "分类id",required = true),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "ak",value = "accessKeyId",required = true),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "sk",value = "secretAccessKey",required = true),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "endpoint",value = "endpoint",required = true),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "bucketname",value = "桶名称",required = true),

            @ApiImplicitParam(paramType = "query",dataType = "long",name = "sharpSize",value = "共享区域大小",required = true),
            @ApiImplicitParam(paramType = "query",dataType = "long",name = "sharpFlowSize",value = "总的下行的流量",required = true),
            @ApiImplicitParam(paramType = "query",dataType = "boolean",name = "delivery",value = "审核状态",required = true),
    })
    public JSONObject add(
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


        ObsInfo obsInfo = new ObsInfo();
        obsInfo.setName(name);
        obsInfo.setUserId(userId);
        obsInfo.setClazzId(clazzId);
        obsInfo.setAccessKeyId(ak);
        obsInfo.setSecretAccessKey(sk);
        obsInfo.setEndpoint(endPonit);
        obsInfo.setBucketName(bucketname);
        obsInfo.setAvailable(false);
        obsInfo.setTime(Utils.getNowDate());
        if(delivery){
            obsInfo.setObsInfoStatus(2);
        }else{
            obsInfo.setObsInfoStatus(1);
        }
        obsInfo.setDelivery(delivery);
        obsInfo.setDownflow(sharpFlowSize);
        obsInfo.setSharpingSize(sharpSize);

        obsInfo.setUseSize(0);
        obsInfo.setRemainderSize(sharpSize);
        obsInfo.setDownflow(sharpFlowSize);
        obsInfo.setDownedFlow(0);
        obsInfo.setRequest(0);
        obsInfo.setPutNum(0);

        ObsInfo obsInfo1 = obsInfoService.save(obsInfo);
        if(obsInfo1 == null){
            return Result.Result(405,"添加失败","");
        }else{
            return Result.Result(200,"",obsInfo);
        }
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ApiOperation(
            value = "获取单个obs数据",notes = "获取单个obs数据",
            response = ObsInfo.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "返回单个obs数据")
    })
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query",dataType = "int",name = "id",value = "信息id",required = true),
    })
    public JSONObject update(
            @ApiParam("id")@RequestParam("id")int id
    ) {

        ObsInfo obsInfo = obsInfoService.findOne(id);

        return Result.Result(200,"",obsInfo);
    }



    @RequestMapping(value = "/del",method = RequestMethod.DELETE)
    @ApiOperation(
            value = "删除单个obs数据",notes = "删除单个obs数据",
            response = Result.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "删除成功")
    })
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query",dataType = "int",name = "id",value = "信息id",required = true),
    })
    public JSONObject del(
            @ApiParam("id")@RequestParam("id")int id
    ) {

        obsInfoRepository.delete(id);

        return Result.Result(200,"","");
    }

    //用户获取obs可以上传的信息列表
    @RequestMapping(value = "/getobslistforuser",method = RequestMethod.GET)
    @ApiOperation(
            value = "返回obs信息列表",notes = "返回obs信息列表",
            response = ObsInfo.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "返回数据")
    })
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query",dataType = "int",name = "id",value = "用户id",required = true),
    })
    public JSONObject getobslistforuser(
            @ApiParam("id")@RequestParam("id")int id
    ) {
        //返回用户所属的obs
        List<ObsInfo> obsInfo = obsInfoRepository.findObsInfosByUserId(id);

        return Result.Result(200,"",obsInfo);
    }

    //用户获取obs可以上传的信息列表
    @RequestMapping(value = "/obsprogress",method = RequestMethod.GET)
    @ApiOperation(
            value = "obs信息审核通过",notes = "obs信息审核通过",
            response = ObsInfo.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "审核成功")
    })
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query",dataType = "int",name = "id",value = "用户id",required = true),
    })
    public JSONObject obsprogress(
            @ApiParam("id")@RequestParam("id")int id
    ) {
        //返回用户所属的obs
        ObsInfo obsInfo = obsInfoService.findOne(id);
        System.out.println(obsInfo.getObsInfoStatus());
        if(obsInfo.getObsInfoStatus() == 2){
            obsInfo.setObsInfoStatus(3);
            obsInfoService.save(obsInfo);
            return Result.Result(200,"",obsInfo);
        }else{
            return Result.Result(405,"当前信息不需要审核不需要审核","");
        }
    }

    //用户获取obs可以上传的信息列表
    @RequestMapping(value = "/obstoclose",method = RequestMethod.GET)
    @ApiOperation(
            value = "obs禁用",notes = "obs禁用",
            response = ObsInfo.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "禁用成功")
    })
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query",dataType = "int",name = "id",value = "OBS id",required = true),
    })
    public JSONObject obsToClose(
            @ApiParam("id")@RequestParam("id")int id
    ) {
        //返回用户所属的obs
        ObsInfo obsInfo = obsInfoService.findOne(id);

        System.out.println(obsInfo);

        System.out.println(obsInfo.toString());

        if(obsInfo.getObsInfoStatus() == 3){
            obsInfo.setObsInfoStatus(7);
            obsInfo.setAvailable(false);
            obsInfoService.save(obsInfo);
            return Result.Result(200,"",obsInfo);
        }else{
            return Result.Result(405,"当前不属于待审核状态","");
        }
    }

    //用户获取obs可以上传的信息列表
    @RequestMapping(value = "/obstoopen",method = RequestMethod.GET)
    @ApiOperation(
            value = "obs启用",notes = "obs启用",
            response = ObsInfo.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "禁用成功")
    })
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query",dataType = "int",name = "id",value = "OBS id",required = true),
    })
    public JSONObject obsToOpen(
            @ApiParam("id")@RequestParam("id")int id
    ) {
        //返回用户所属的obs
        ObsInfo obsInfo = obsInfoService.findOne(id);

        System.out.println(obsInfo);

        System.out.println(obsInfo.toString());

        if(obsInfo.getObsInfoStatus() != 1){
            obsInfo.setObsInfoStatus(3);
            obsInfo.setAvailable(true);
            obsInfoService.save(obsInfo);
            return Result.Result(200,"",obsInfo);
        }else{
            return Result.Result(405,"当前用户没有提交","");
        }
    }


    //用户获取obs可以上传的信息列表
    @RequestMapping(value = "/obstoshen",method = RequestMethod.GET)
    @ApiOperation(
            value = "obs进行审核",notes = "obs提交审核，用户由待提交审核转为提交审核",
            response = ObsInfo.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "审核成功")
    })
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query",dataType = "int",name = "id",value = "OBS id",required = true),
    })
    public JSONObject obsToShen(
            @ApiParam("id")@RequestParam("id")int id
    ) {
        //返回用户所属的obs
        ObsInfo obsInfo = obsInfoService.findOne(id);

        System.out.println(obsInfo);

        System.out.println(obsInfo.toString());
        System.out.println(obsInfo.getObsInfoStatus());
        if(obsInfo.getObsInfoStatus() == 1){
            System.out.println("发现");
            obsInfo.setObsInfoStatus(2);
            obsInfoService.save(obsInfo);
            return Result.Result(200,"",obsInfo);
        }else{
            return Result.Result(405,"当前不属于待审核状态","");
        }
    }


}
