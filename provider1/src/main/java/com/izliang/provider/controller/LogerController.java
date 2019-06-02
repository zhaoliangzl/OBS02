package com.izliang.provider.controller;

import com.alibaba.fastjson.JSONObject;
import com.izliang.provider.model.Loger;
import com.izliang.provider.repo.LoggerRepository;
import com.izliang.provider.utils.Result;
import com.izliang.provider.utils.Utils;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping(value = "/loger")
public class LogerController {

    @Autowired
    private LoggerRepository loggerRepository;

    @RequestMapping(value = "/log",method = RequestMethod.GET)
    public JSONObject log(@RequestParam("id")int id){

        //根据id查询最近7天的数据
        List<Loger> logerList = new ArrayList<>();



        Long time = System.currentTimeMillis();

        List<Loger> loger = loggerRepository.findRecentlyLogByTimeById(time,id);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();

        if(loger.size()<7){
            //填空
            for (int i = 0; i < 7; i++) {
                if(logerList.get(i) == null){
                    Loger loger1 = new Loger();
                    //过去七天
                    c.setTime(new Date());
                    c.add(Calendar.DATE, i*(-1));
                    Date d = c.getTime();
                    loger1.setTimeStr(format.format(d));
                    loger1.setTime(c.getTimeInMillis());
                    loger1.setSize(0);
                    loger1.setFlow(0);
                    loger1.setRequest(0);
                    logerList.add(loger1);
                }
            }
        }else{
            //直接倒序

        }
        Collections.reverse(logerList);

        return Result.Result(200,"",loger);
    }



}
