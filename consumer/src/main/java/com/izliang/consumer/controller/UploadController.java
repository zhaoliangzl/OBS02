package com.izliang.consumer.controller;

import com.izliang.consumer.service.UploadFeignClient;
import com.izliang.consumer.utils.Utils;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

@RestController
@RequestMapping(value = "/upload")
public class UploadController {

    @Autowired
    private UploadFeignClient uploadFeignClient;

    @RequestMapping(value = "/common/{code}",method = RequestMethod.POST)
    String publicUpload(
            @ApiParam("code")@PathVariable("code") String code,
            @RequestParam("file") MultipartFile file,
            HttpServletRequest request
    ){
        String ip = Utils.getIpAddr(request);
        System.out.println("请求ip"+ip);
        System.out.println("code = [" + code + "], file = [" + file + "], request = [" + request + "]");
        return uploadFeignClient.publicUpload(code, file, ip);
    }

    @RequestMapping(value = "/private/{code}",method = RequestMethod.POST)
    String privateUpload(
            @ApiParam("secret")@PathParam("secret") String secret,
            @ApiParam("code")@PathVariable("code") String code,
            @RequestParam("file") MultipartFile file,
            HttpServletRequest request
    ){
        String ip = Utils.getIpAddr(request);
        System.out.println("secret = [" + secret + "], code = [" + code + "], file = [" + file + "], request = [" + request + "]");
        return uploadFeignClient.privateUpload(secret,code, file, ip);
    }



}
