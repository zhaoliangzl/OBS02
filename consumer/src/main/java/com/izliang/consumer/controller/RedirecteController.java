package com.izliang.consumer.controller;


import com.izliang.consumer.model.Developer;
import com.izliang.consumer.model.ObsInfo;
import com.izliang.consumer.model.UploadInfo;
import com.izliang.consumer.repo.DeveloperRepository;
import com.izliang.consumer.repo.OBSInfoRepository;
import com.izliang.consumer.repo.UploadInfoRepository;
import com.izliang.consumer.service.DownloadFeignClient;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
* 重定向下载器
* 包含下载数据统计
* **/
@Controller
@RequestMapping(value = "/download")
public class RedirecteController {


    @Autowired
    private DownloadFeignClient downloadFeignClient;


    @Autowired
    private OBSInfoRepository obsInfoRepository;

    @Autowired
    private UploadInfoRepository uploadInfoRepository;

    @Autowired
    private DeveloperRepository developerRepository;

//    @RequestMapping(value = "/test")
//    public void test(HttpServletRequest req, HttpServletResponse resp)throws Exception{
//        //使用重定向来均衡哥哥obs
//        resp.sendRedirect("https://bbs.csdn.net/topics/390264878");
//    }

    /**
     * 重定向下载器
     * */

    @RequestMapping(value = "/common/{code}",method = RequestMethod.GET)
    public String common(
            @ApiParam("code")@PathVariable("code") String code
    ){
        System.out.println("code = [" + code + "]");
        //code解密
        UploadInfo uploadInfo = uploadInfoRepository.findUploadInfoByUploadSecret(code);
        if(uploadInfo != null){

            ObsInfo obsInfo = obsInfoRepository.getOne(uploadInfo.getObsInfoId());

            obsInfo.setRequest(obsInfo.getRequest()+1);
            obsInfo.setDownedFlow(obsInfo.getDownedFlow()+uploadInfo.getSize());

            ObsInfo obsInfo1= obsInfoRepository.save(obsInfo);



            if(obsInfo1 == null){
                return "redirect:redirect:http://www.17sucai.com/preview/5276/2019-03-27/404/index.html";
            }else{
                if(obsInfo1 != null && uploadInfo.getType() == null){
                    Developer developer = developerRepository.findOne(uploadInfo.getDevId());
                    developer.setUseFlow(developer.getUseFlow()+uploadInfo.getSize());
                    developer.setToadyFlow(developer.getToadyFlow()+uploadInfo.getSize());
                    developer.setTodayRequest(developer.getTodayRequest()+1);
                    developerRepository.save(developer);
                    return "redirect:"+uploadInfo.getRealUrl();
                }else{
                    return "redirect:http://www.17sucai.com/preview/1266961/2018-09-23/3/demo.html";
                }
            }
        }else{
            return "redirect:redirect:http://www.17sucai.com/preview/5276/2019-03-27/404/index.html";
        }

    }

    @RequestMapping(value = "/private/{code}",method = RequestMethod.GET)
    public String privateR(
            @ApiParam("code")@PathVariable("code") String code
    ){
        System.out.println("code = [" + code + "]");
        //code解密
        UploadInfo uploadInfo = uploadInfoRepository.findUploadInfoByUploadSecret(code);
        if(uploadInfo != null){

            ObsInfo obsInfo = obsInfoRepository.getOne(uploadInfo.getObsInfoId());

            obsInfo.setRequest(obsInfo.getRequest()+1);
            obsInfo.setDownedFlow(obsInfo.getDownedFlow()+uploadInfo.getSize());

            ObsInfo obsInfo1= obsInfoRepository.save(obsInfo);


            System.out.println(obsInfo1.toString());

            if(obsInfo1 == null){
                return "redirect:redirect:http://www.17sucai.com/preview/5276/2019-03-27/404/index.html";
            }else{
                if(obsInfo1 != null && uploadInfo.getType() == true){
                    Developer developer = developerRepository.findOne(uploadInfo.getDevId());
                    developer.setUseFlow(developer.getUseFlow()+uploadInfo.getSize());
                    developer.setToadyFlow(developer.getToadyFlow()+uploadInfo.getSize());
                    developer.setTodayRequest(developer.getTodayRequest()+1);
                    developerRepository.save(developer);
                    return "redirect:"+uploadInfo.getRealUrl();
                }else{
                    return "redirect:http://www.17sucai.com/preview/1266961/2018-09-23/3/demo.html";
                }
            }
        }else{
            return "redirect:redirect:http://www.17sucai.com/preview/5276/2019-03-27/404/index.html";
        }

    }
}
