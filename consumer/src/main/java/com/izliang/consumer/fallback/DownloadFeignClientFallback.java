package com.izliang.consumer.fallback;


import com.izliang.consumer.model.Clazz;
import com.izliang.consumer.service.DownloadFeignClient;
import com.izliang.consumer.service.ServiceBFeignClient;
import com.izliang.consumer.service.UploadFeignClient;
import com.izliang.consumer.utils.Result;
import org.springframework.stereotype.Component;

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
public class DownloadFeignClientFallback implements DownloadFeignClient {

    @Override
    public String publicDwonload(String code) {
        return Result.Result(500,"断路器报错","").toJSONString();
    }
}