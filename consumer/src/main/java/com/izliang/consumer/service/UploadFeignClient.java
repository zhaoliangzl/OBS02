package com.izliang.consumer.service;

import com.izliang.consumer.config.FeignMultipartSupportConfig;
import com.izliang.consumer.fallback.ServiceBFeignClientFallback;
import com.izliang.consumer.fallback.UploadFeignClientFallback;
import feign.form.spring.SpringFormEncoder;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;


@FeignClient(value = "eureka-feign-service-upload", fallback = UploadFeignClientFallback.class, configuration = FeignMultipartSupportConfig.class)
public interface UploadFeignClient {


    @RequestMapping(value = "/upload/common/{code}",method = RequestMethod.POST,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String publicUpload(
            @ApiParam("code")@PathVariable("code") String code,
            @RequestPart("file") MultipartFile file,
            @RequestParam("ip") String ip
    );

    @RequestMapping(value = "/upload/private/{code}",method = RequestMethod.POST,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String privateUpload(
            @ApiParam("secret")@RequestParam("secret") String secret,
            @ApiParam("code")@PathVariable("code") String code,
            @RequestPart("file") MultipartFile file,
            @RequestParam("ip") String ip
    );


}
