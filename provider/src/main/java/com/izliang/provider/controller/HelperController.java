package com.izliang.provider.controller;

import com.alibaba.fastjson.JSONObject;
import com.izliang.provider.model.Helper;
import com.izliang.provider.model.HelperClass;
import com.izliang.provider.model.HelperClassAndHelpers;
import com.izliang.provider.repo.HelperClazzRepository;
import com.izliang.provider.repo.HelperRepository;
import com.izliang.provider.utils.Result;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/helper")
@Api(value = "帮助文档接口",tags = "helper",description = "帮助文档接口")
public class HelperController {

    @Autowired
    private HelperRepository helperRepository;

    @Autowired
    private HelperClazzRepository helperClazzRepository;

    @RequestMapping(value = "/all",method = RequestMethod.GET)
    @ApiOperation(
            value = "获取所有的帮助文档",notes = "获取所有的帮助文档",
            response = Helper.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "获取所有的帮助文档")
    })
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query",dataType = "int",name = "page",value = "页码",required = true),
    })
    public JSONObject all(
    ) {

        List<HelperClass> helperClasses = helperClazzRepository.findAll();

        List<HelperClassAndHelpers> helperClassAndHelpers = new ArrayList<>();

        for (HelperClass helperClass:helperClasses) {

            HelperClassAndHelpers helperClassAndHelpers1 = new HelperClassAndHelpers();
            helperClassAndHelpers1.setHelperClass(helperClass);
            helperClassAndHelpers1.setHelpers(helperRepository.findAllByHelperClassId(helperClass.getId()));

            helperClassAndHelpers.add(helperClassAndHelpers1);
        }


        return Result.Result(200,"",helperClassAndHelpers);
    }


}
