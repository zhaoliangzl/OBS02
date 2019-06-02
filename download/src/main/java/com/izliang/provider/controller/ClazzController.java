package com.izliang.provider.controller;


import com.alibaba.fastjson.JSONObject;

import com.izliang.provider.model.Clazz;
import com.izliang.provider.model.ObsInfo;
import com.izliang.provider.repo.ClazzRepository;
import com.izliang.provider.service.ClazzService;
import com.izliang.provider.utils.Result;
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

@RestController
@RequestMapping(value = "/clazz")
@Api(value = "OBS分类管理",tags = "clazz",description = "对OBS分类进行管理")
public class ClazzController {

    @Autowired
    private ClazzService clazzService;

    @Autowired
    private ClazzRepository clazzRepository;

    //全局分页变量
    @Value(value = "${self.page.size}")
    private int pageSize;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @ApiOperation(
            value = "获取obs信息列表",notes = "获取OBS分类信息列表",
            response = ObsInfo.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "返回OBS分类数据的列表")
    })
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query",dataType = "int",name = "page",value = "页码",required = true),
    })
    public JSONObject open(
            @ApiParam("page")@RequestParam("page")int page
    ) {

        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Pageable pageable = new PageRequest(page,pageSize,sort);
        Page<Clazz> datas = clazzRepository.findAll(pageable);

        return Result.ResultPage(200,"",datas.getContent(),page,datas.getTotalPages(),(int) datas.getTotalElements());
    }

    @RequestMapping(value = "/alllist",method = RequestMethod.GET)
    @ApiOperation(
            value = "获取obs信息列表(无分页)",notes = "获取OBS分类信息列表",
            response = ObsInfo.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "返回OBS分类数据的列表")
    })
    @ApiImplicitParams(value = {
    })
    public JSONObject getalllist(
    ) {
        return Result.Result(200,"",clazzRepository.findAll());
    }

    @RequestMapping(value = "/get",method = RequestMethod.GET)
    @ApiOperation(
            value = "获取单个OBS分类数据",notes = "获取单个OBS分类数据",
            response = ObsInfo.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "返回单个OBS分类数据")
    })
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query",dataType = "int",name = "id",value = "信息id",required = true),
    })
    public JSONObject get(
            @ApiParam("id")@RequestParam("id")int id
    ) {

        Clazz clazz = clazzService.findOne(id);

        return Result.Result(200,"",clazz);
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ApiOperation(
            value = "添加单个OBS分类数据",notes = "添加单个OBS分类数据",
            response = ObsInfo.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "返回添加后的单个obs分类数据")
    })
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "name",value = "OBS分类名称",required = true),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "mark",value = "OBS分类备注",required = false),
    })
    public JSONObject add(
            @ApiParam("name")@RequestParam("name")String name,
            @ApiParam("mark")@RequestParam("mark")String mark
    ) {

        Clazz clazz = new Clazz();
        clazz.setName(name);
        clazz.setMark(mark);

        Clazz clazz1 = clazzService.save(clazz);

        return Result.Result(200,"",clazz1);
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ApiOperation(
            value = "修改单个obs分类数据",notes = "修改单个obs分类数据",
            response = ObsInfo.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "返回修改后的单个obs分类数据"),
            @ApiResponse(code = 404,message = "obs分类数据没有找到"),
            @ApiResponse(code = 405,message = "obs分类数据修改失败")
    })
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query",dataType = "int",name = "id",value = "信息id",required = true),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "name",value = "obs分类名称",required = true),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "mark",value = "obs分类备注",required = false),
    })
    public JSONObject update(
            @ApiParam("id")@RequestParam("id")int id,
            @ApiParam("name")@RequestParam("name")String name,
            @ApiParam("mark")@RequestParam("mark")String mark
    ) {
        Clazz clazz = clazzService.findOne(id);
        if(clazz == null){
            return Result.Result(404,"","obs分类数据没有找到");
        }else{
            clazz.setName(name);
            clazz.setMark(mark);
            Clazz clazz1 = clazzService.save(clazz);
            if(clazz1 == null){
                return Result.Result(200,"","obs分类数据修改失败");
            }else{
                return Result.Result(200,"",clazz1);
            }
        }
    }



    @RequestMapping(value = "/del",method = RequestMethod.DELETE)
    @ApiOperation(
            value = "删除单个obs分类数据",notes = "删除单个obs分类数据",
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

        clazzRepository.delete(id);

        return Result.Result(200,"","");
    }





}
