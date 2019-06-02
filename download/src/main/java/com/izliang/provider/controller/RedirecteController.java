package com.izliang.provider.controller;


import com.izliang.provider.model.ObsInfo;
import com.izliang.provider.model.UploadInfo;
import com.izliang.provider.repo.OBSInfoRepository;
import com.izliang.provider.repo.UploadInfoRepository;
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
    private UploadInfoRepository uploadInfoRepository;

    @Autowired
    private OBSInfoRepository obsInfoRepository;

    @RequestMapping(value = "/test")
    public void test(HttpServletRequest req, HttpServletResponse resp)throws Exception{
        //使用重定向来均衡哥哥obs
        resp.sendRedirect("https://bbs.csdn.net/topics/390264878");
    }

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

            System.out.println(obsInfo1.toString());

            if(obsInfo1 == null){
                return "redirect:";
            }else{
                if(obsInfo1 != null && uploadInfo.getType() == null){
                    return "redirect:"+uploadInfo.getRealUrl();
                }else{
                    return "redirect:http://www.17sucai.com/preview/5276/2019-03-27/404/index.html";
                }
            }
        }else{
            return "redirect:wwwwwwwwwww";
        }

    }
}
