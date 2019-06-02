package com.izliang.consumer.fallback;


import com.alibaba.fastjson.JSONObject;
import com.izliang.consumer.model.Clazz;
import com.izliang.consumer.model.Developer;
import com.izliang.consumer.service.ServiceBFeignClient;
import com.izliang.consumer.utils.Result;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.awt.print.Pageable;
import java.util.ArrayList;

/**
 * @Title: ServiceBFeignClientFallback
 * @ProjectName springcloud_feign
 * @Description: FeignClient失败回调方法
 * @Author WeiShiHuai
 * @Date 2018/9/10 15:22
 * FeignClient失败回调方法必须实现使用@FeignClient标识的接口（implements ServiceBFeignClient），实现其中的方法
 */
@Component
public class ServiceBFeignClientFallback implements ServiceBFeignClient {

    /**
     * 当服务B由于某种原因使得服务调用不成功时会执行该回调方法
     *
     * @param name
     * @return
     */
    @Override
    public String getInfo(String name) {
        return "sorry " + name + ", feign client error";
    }

    /*
    * 断路器分页
    * **/
    @Override
    public String getList(int page) {
        ArrayList<Clazz> arrayList = new ArrayList<>();
        return Result.ResultPage(500,"断路器报错",arrayList,page,0,0).toJSONString();
    }

    @Override
    public String alllist() {
        return Result.Result(500,"断路器报错","").toJSONString();
    }

    @Override
    public String adminLogin(String username,String password) {
        return Result.Result(500,"断路器报错","").toJSONString();
    }

    @Override
    public String getObsInfoList(int page) {
        ArrayList<Clazz> arrayList = new ArrayList<>();
        return Result.Result(500,"断路器报错","").toJSONString();
    }

    @Override
    public String getObsInfoListForUserByPage(int page, int id) {
        ArrayList<Clazz> arrayList = new ArrayList<>();
        return Result.ResultPage(500,"断路器报错",arrayList,page,0,0).toJSONString();
    }

    @Override
    public String getObsInfo(int id) {
        return Result.Result(500,"断路器报错","").toJSONString();
    }

    @Override
    public String addObsInfo(String name, int userId, int clazzId, String ak, String sk, String endPonit, String bucketname, long sharpSize, long sharpFlowSize, boolean delivery) {
        return Result.Result(500,"断路器报错","").toJSONString();
    }

    @Override
    public String updateObsInfo(int id,String name, int userId, int clazzId, String ak, String sk, String endPonit, String bucketname, long sharpSize, long sharpFlowSize, boolean delivery) {
        return Result.Result(500,"断路器报错","").toJSONString();
    }

    @Override
    public String delObsInfo(int id) {
        return Result.Result(500,"断路器报错","").toJSONString();
    }

    @Override
    public String getObsInfoListByUser(int id) {
        return Result.Result(500,"断路器报错","").toJSONString();
    }

    @Override
    public String ObsProgress(int id) {
        return Result.Result(500,"断路器报错","").toJSONString();
    }

    @Override
    public String ObsClose(int id) {
        return Result.Result(500,"断路器报错","").toJSONString();
    }

    @Override
    public String ObsToShen(int id) {
        return Result.Result(500,"断路器报错","").toJSONString();
    }

    @Override
    public String ObsToOpen(int id) {
        return Result.Result(500,"断路器报错","").toJSONString();
    }

    @Override
    public String developerList(int page) {
        ArrayList<Developer> arrayList = new ArrayList<>();
        return Result.ResultPage(500,"断路器报错",arrayList,page,0,0).toJSONString();

    }

    @Override
    public String getDeveloper(int id) {
        return Result.Result(500,"断路器报错","").toJSONString();
    }

    @Override
    public String updateDeveloper(int id) {
        return Result.Result(500,"断路器报错","").toJSONString();
    }

    @Override
    public String addDeveloper(int id) {
        return Result.Result(500,"断路器报错","").toJSONString();
    }

    @Override
    public String delDeveloper(int id) {
        return Result.Result(500,"断路器报错","").toJSONString();
    }

    @Override
    public String getUploadLink(int id) {
        return Result.Result(500,"断路器报错","").toJSONString();
    }

    @Override
    public String generateUploadLink(int id) {
        return Result.Result(500,"断路器报错","").toJSONString();
    }

    @Override
    public String Developerlogin(String username, String password) {
        return Result.Result(500,"断路器报错","").toJSONString();
    }

    @Override
    public String registerDeveloper(String username, String email, String password, String name, String code) {
        System.out.println("username = [" + username + "], email = [" + email + "], password = [" + password + "], name = [" + name + "], code = [" + code + "]");
        return Result.Result(500,"断路器报错","").toJSONString();
    }

    @Override
    public String getCheckCode(String email) {
        System.out.println("email = [" + email + "]");
        return Result.Result(200,"断路器报错","").toJSONString();
    }

    @Override
    public String getcheckmail(String email) {
        return Result.Result(500,"断路器报错","").toJSONString();
    }

    @Override
    public String getcheckmail2(String email, String code) {
        return Result.Result(500,"断路器报错","").toJSONString();
    }

    @Override
    public String changepwd(String email, String code, String password) {
        return Result.Result(500,"断路器报错","").toJSONString();
    }

    @Override
    public String getUploadInfoListByDev(int id, int page) {
        return Result.Result(500,"断路器报错","").toJSONString();
    }

    @Override
    public String getUploadInfoList(int page) {
        return Result.Result(500,"断路器报错","").toJSONString();
    }

    @Override
    public String allHelper() {
        return Result.Result(500,"断路器报错","").toJSONString();
    }

    @Override
    public String log(int id) {
        return Result.Result(500,"断路器报错","").toJSONString();
    }
}