package com.izliang.provider.controller;

import com.alibaba.fastjson.JSONObject;
import com.izliang.provider.model.Clazz;
import com.izliang.provider.model.HelperClass;
import com.izliang.provider.model.ObsInfo;
import com.izliang.provider.repo.ClazzRepository;
import com.izliang.provider.repo.HelperClazzRepository;
import com.izliang.provider.repo.HelperRepository;
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
@RequestMapping(value = "/helperclazz")
@Api(value = "开发者信息管理",tags = "developer",description = "对开发者的账号信息进行管理")
public class HelperClazzController {

    @Autowired
    private HelperClazzRepository helperClazzRepository;

    @Autowired
    private HelperRepository helperRepository;

    //开发者修改自己的信息

    //全局分页变量
    @Value(value = "${self.page.size}")
    private int pageSize;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @ApiOperation(
            value = "获取开发者信息列表",notes = "获取开发者信息列表",
            response = ObsInfo.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "返回开发者信息的列表")
    })
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query",dataType = "int",name = "page",value = "页码",required = true),
    })
    public JSONObject open(
            @ApiParam("page")@RequestParam("page")int page
    ) {

        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Pageable pageable = new PageRequest(page-1,pageSize,sort);
        Page<HelperClass> datas = helperClazzRepository.findAll(pageable);

        return Result.ResultPage(200,"",datas.getContent(),page,datas.getTotalPages(),(int) datas.getTotalElements());
    }

    @RequestMapping(value = "/alllist",method = RequestMethod.GET)
    @ApiOperation(
            value = "获取开发者信息列表",notes = "获取开发者信息列表",
            response = ObsInfo.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "返回开发者信息的列表")
    })
    @ApiImplicitParams(value = {
    })
    public JSONObject allList(
    ) {
        return Result.Result(200,"",helperClazzRepository.findAll());
    }

    @RequestMapping(value = "/get",method = RequestMethod.GET)
    @ApiOperation(
            value = "获取开发者信息数据",notes = "获取单个开发者信息数据",
            response = ObsInfo.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "返回单个开发者信息数据")
    })
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query",dataType = "int",name = "id",value = "信息id",required = true),
    })
    public JSONObject get(
            @ApiParam("id")@RequestParam("id")int id
    ) {

        HelperClass clazz = helperClazzRepository.findOne(id);

        return Result.Result(200,"",clazz);
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ApiOperation(
            value = "更新帮助文档分类",notes = "更新帮助文档分类",
            response = ObsInfo.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "返回更新后的帮助文档分类"),
            @ApiResponse(code = 404,message = "没有数据"),
            @ApiResponse(code = 405,message = "保存出错"),
    })
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query",dataType = "int",name = "id",value = "信息id",required = true),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "name",value = "分类名称",required = true),
    })
    public JSONObject update(
            @ApiParam("分类id")@RequestParam("id")int id,
            @ApiParam("分类名称")@RequestParam("name")String name
    ) {


        HelperClass clazz = helperClazzRepository.getOne(id);
        if(clazz !=null){
            clazz.setName(name);

            HelperClass clazz1= helperClazzRepository.save(clazz);

            if(clazz1 != null){
                return Result.Result(200,"",clazz1);
            }else{
                return Result.Result(405,"更新出错","");
            }
        }else{
            return Result.Result(404,"该数据不存在","");
        }

    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ApiOperation(
            value = "添加帮助文档分类",notes = "添加帮助文档分类",
            response = ObsInfo.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "返回添加成功的帮助文档分类"),
            @ApiResponse(code = 405,message = "保存出错")
    })
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "name",value = "分类名称",required = true),
    })
    public JSONObject add(
            @ApiParam("分类名称")@RequestParam("name")String name
    ) {

        HelperClass clazz = new HelperClass();

        clazz.setName(name);

        HelperClass clazz1 = helperClazzRepository.save(clazz);

        if(clazz1 != null){
            return Result.Result(200,"",clazz1);
        }else{
            return Result.Result(405,"保存出错","");
        }
    }

    @RequestMapping(value = "/del",method = RequestMethod.DELETE)
    @ApiOperation(
            value = "删除单个帮助文档分类",notes = "删除单个帮助文档分类",
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

        helperClazzRepository.delete(id);

        helperRepository.deleteAllByHelperClassId(id);

        return Result.Result(200,"","");
    }





}
