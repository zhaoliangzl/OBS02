package com.izliang.consumer.fallback;

import com.izliang.consumer.model.*;
import com.izliang.consumer.service.AdminFeignService;
import com.izliang.consumer.utils.Result;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class AdminFeignClientFallback implements AdminFeignService {

    @Override
    public String developerList(int page) {
        return Result.Result(500,"断路器报错","").toJSONString();
    }

    @Override
    public String getDeveloper(int id) {
        return Result.Result(500,"断路器报错","").toJSONString();
    }

    @Override
    public String update(int id, long totalFlow, long totalSize, long useFlow, long useSize) {
        return Result.Result(500,"断路器报错","").toJSONString();
    }

    @Override
    public String clazzList(int page) {
        ArrayList<Clazz> arrayList = new ArrayList<>();
        return Result.ResultPage(500,"断路器报错",arrayList,page,0,0).toJSONString();

    }

    @Override
    public String getClazz(int id) {
        return Result.Result(500,"断路器报错","").toJSONString();
    }

    @Override
    public String delClazz(int id) {
        return Result.Result(500,"断路器报错","").toJSONString();
    }

    @Override
    public String clazzUpdate(int id, String name, String mark) {
        return Result.Result(500,"断路器报错","").toJSONString();
    }

    @Override
    public String clazzAdd(String name, String mark) {
        return Result.Result(500,"断路器报错","").toJSONString();
    }

    @Override
    public String helperList(int page) {
        ArrayList<Helper> arrayList = new ArrayList<>();
        return Result.ResultPage(500,"断路器报错",arrayList,page,0,0).toJSONString();

    }

    @Override
    public String getHelper(int id) {
        return Result.Result(500,"断路器报错","").toJSONString();
    }

    @Override
    public String updateHelper(int id, String title, int helperClassId, String content) {
        return Result.Result(500,"断路器报错","").toJSONString();
    }

    @Override
    public String addHelper(String title, int helperClassId, String content) {
        return Result.Result(500,"断路器报错","").toJSONString();
    }

    @Override
    public String delHelper(int id) {
        return Result.Result(500,"断路器报错","").toJSONString();
    }

    @Override
    public String helperClassList(int page) {
        ArrayList<HelperClass> arrayList = new ArrayList<>();
        return Result.ResultPage(500,"断路器报错",arrayList,page,0,0).toJSONString();

    }

    @Override
    public String getHelperClass(int id) {
        return Result.Result(500,"断路器报错","").toJSONString();
    }

    @Override
    public String updateHelperClass(int id, String name) {
        return Result.Result(500,"断路器报错","").toJSONString();
    }

    @Override
    public String addHelperClass(String name) {
        return Result.Result(500,"断路器报错","").toJSONString();
    }

    @Override
    public String delHelperClass(int id) {
        return Result.Result(500,"断路器报错","").toJSONString();
    }

    @Override
    public String helperClassAllList() {
        return Result.Result(500,"断路器报错","").toJSONString();
    }

    @Override
    public String NoticeList(int page) {
        ArrayList<Notice> arrayList = new ArrayList<>();
        return Result.ResultPage(500,"断路器报错",arrayList,page,0,0).toJSONString();

    }

    @Override
    public String getNotice(int id) {
        return Result.Result(500,"断路器报错","").toJSONString();
    }

    @Override
    public String updateNotice(int id, String title, String content,Boolean enable) {
        return Result.Result(500,"断路器报错","").toJSONString();
    }

    @Override
    public String addNotice(String title, String content,Boolean enable) {
        return Result.Result(500,"断路器报错","").toJSONString();
    }

    @Override
    public String delNotice(int id) {
        return Result.Result(500,"断路器报错","").toJSONString();
    }

    @Override
    public String NoticeListByDev(int page) {
        ArrayList<Notice> arrayList = new ArrayList<>();
        return Result.ResultPage(500,"断路器报错",arrayList,page,0,0).toJSONString();
    }

    @Override
    public String LoggerList(int page) {
        return Result.Result(500,"断路器报错","").toJSONString();
    }

    @Override
    public String welcome(int id) {
        return Result.Result(500,"断路器报错","").toJSONString();
    }
}
