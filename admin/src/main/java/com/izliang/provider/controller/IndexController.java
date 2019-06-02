package com.izliang.provider.controller;

import com.alibaba.fastjson.JSONObject;
import com.izliang.provider.model.Admin;
import com.izliang.provider.model.res.ResIndex;
import com.izliang.provider.repo.*;
import com.izliang.provider.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/welcome")
public class IndexController {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private DeveloperRepository developerRepository;

    @Autowired
    private OBSInfoRepository obsInfoRepository;

    @Autowired
    private NoticeRepository noticeRepository;

    @Autowired
    private ClazzRepository clazzRepository;

    @Autowired
    private LoggerRepository loggerRepository;

    @Autowired
    private HelperRepository helperRepository;

    @Autowired
    private HelperClazzRepository helperClazzRepository;

    @Autowired
    private UploadInfoRepository uploadInfoRepository;

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public JSONObject index(@RequestParam("id")int id){

        //首页返回数据
        /**
         * 首页返回用户的数量
         * 首页返回OBS信息的数量
         * 首页返回OBS总的存储容量
         * 首页返回OBS当月总的数据流量
         * 首页返回OBS当月使用的存储容量
         * 首页返回OBS当月使用的数据流量
         * 返回用户的存储容量和系统可用的所有OBS的容量
         * 返回用户的当月流量和系统可用的所有的当月流量
         *
         * 返回系统的
         *
         * */

        ResIndex resIndex  = new ResIndex();

        int userNum = developerRepository.countAll();
        resIndex.setUserNum(userNum);

        //可用obs信息数量
        int obsNum = obsInfoRepository.findAllEnbaleNum();
        resIndex.setObsNum(obsNum);

        //当月下载流量
        long usedFlow = obsInfoRepository.findAllUseFlowSize();
        resIndex.setUsedFlow(usedFlow);

        long totalFlow = obsInfoRepository.findAllFlowSie();
        resIndex.setTotalFlow(totalFlow);

        //当前存储容量
        long usedSize = obsInfoRepository.findAllUsedSize();
        System.out.println("当前存储容量"+usedSize);
        resIndex.setUsedSize(usedSize);

        long totalSize = obsInfoRepository.findAllSaveSize();
        resIndex.setTotalSize(totalSize);

        //所用用户开辟的存储空间大小
        long useTotalSize = developerRepository.findAllTotalSize();
        resIndex.setUserApplySize(useTotalSize);

        //所有用户使用的空间
        long findAllUsedSize = developerRepository.findAllUsedSize();
        resIndex.setUserUseSize(findAllUsedSize);


        double num1 = (double)usedSize/(double)totalSize;
        System.out.println("1"+num1);
        resIndex.setNum1(num1);
        double num2 = (double)usedFlow/(double)totalFlow;
        resIndex.setNum2(num2);
        double num3 = (double)findAllUsedSize/(double)useTotalSize;
        resIndex.setNum3(num3);
        Admin admin = adminRepository.findOne(id);
        resIndex.setAdmin(admin);
        //当前公告条数
        int noticeNum = noticeRepository.countAll();
        resIndex.setNoticeNum(noticeNum);

        //当前存储文件总数
        int applyNum = uploadInfoRepository.findAllObsInfoNum();
        resIndex.setApplyNum(applyNum);
        //实际存储文件数量
        int realNum = uploadInfoRepository.findReadFileNum();
        resIndex.setUserApplySize(realNum);
        //当前日志数量
        int logNum = loggerRepository.countAll();
        resIndex.setLogNum(logNum);
        //当前obs分类数量
        int obsClassNum = clazzRepository.countAll();
        resIndex.setObsClassNum(obsClassNum);
        //当前帮助文档分类数
        int helperClassNum =helperClazzRepository.countAll();
        resIndex.setHelperClassNum(helperClassNum);
        //当前帮助文档总数
        int helperNum = helperRepository.countAll();
        resIndex.setHelperNum(helperNum);

        return Result.Result(200,"",resIndex);
    }

}
