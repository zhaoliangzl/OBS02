package com.izliang.provider.controller;

import com.alibaba.fastjson.JSONObject;
import com.izliang.provider.model.Loger;
import com.izliang.provider.model.Notice;
import com.izliang.provider.model.ObsInfo;
import com.izliang.provider.model.res.LoggerAndDev;
import com.izliang.provider.repo.DeveloperRepository;
import com.izliang.provider.repo.LoggerRepository;
import com.izliang.provider.repo.NoticeRepository;
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

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/logger")
@Api(value = "开发者信息管理",tags = "developer",description = "对开发者的账号信息进行管理")
public class LogerController {

    @Autowired
    private LoggerRepository loggerRepository;

    @Autowired
    private DeveloperRepository developerRepository;

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

        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(page-1,pageSize,sort);
        Page<Loger> datas = loggerRepository.findAll(pageable);

        List<Loger> logerList = datas.getContent();

        List<LoggerAndDev> loggerAndDevs = new ArrayList<>();

        for (Loger loger:logerList) {

            String devName = developerRepository.getOne(loger.getDevId()).getName();

            LoggerAndDev loggerAndDev = new LoggerAndDev();
            loggerAndDev.setDevName(devName);
            loggerAndDev.setLoger(loger);
            loggerAndDevs.add(loggerAndDev);

        }

        return Result.ResultPage(200,"",loggerAndDevs,page,datas.getTotalPages(),(int) datas.getTotalElements());
    }
}
