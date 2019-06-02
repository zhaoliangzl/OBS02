package com.izliang.consumer.service;

import com.izliang.consumer.config.FeignMultipartSupportConfig;
import com.izliang.consumer.fallback.DownloadFeignClientFallback;
import com.izliang.consumer.fallback.UploadFeignClientFallback;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@FeignClient(value = "eureka-feign-service-download", fallback = DownloadFeignClientFallback.class)
public interface DownloadFeignClient {

    @RequestMapping(value = "/download/common/{code}",method = RequestMethod.GET)
    String publicDwonload(
            @ApiParam("code") @PathVariable("code") String code
    );

}
