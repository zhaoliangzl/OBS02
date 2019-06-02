package com.izliang.consumer.fallback;


import com.izliang.consumer.model.Clazz;
import com.izliang.consumer.service.ServiceBFeignClient;
import com.izliang.consumer.service.UploadFeignClient;
import com.izliang.consumer.utils.Result;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
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
public class UploadFeignClientFallback implements UploadFeignClient {

    @Override
    public String publicUpload(String code, MultipartFile file, String id) {

        return Result.Result(500,"断路器报错","").toJSONString();
    }

    @Override
    public String privateUpload(String secret,String code, MultipartFile file, String id) {
        return Result.Result(500,"断路器报错","").toJSONString();
    }
}