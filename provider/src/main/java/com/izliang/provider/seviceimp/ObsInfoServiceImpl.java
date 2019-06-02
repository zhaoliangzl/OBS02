package com.izliang.provider.seviceimp;
/*
 *  @package com.zliang.undercover.serviceimpl
 *  @author zliang
 *  @create 2018-12-21 0:34
 *  @description：
 *
 */


import com.izliang.provider.model.Clazz;
import com.izliang.provider.model.ObsInfo;
import com.izliang.provider.repo.ClazzRepository;
import com.izliang.provider.repo.OBSInfoRepository;
import com.izliang.provider.service.ClazzService;
import com.izliang.provider.service.ObsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = "obsInfoService")
public class ObsInfoServiceImpl implements ObsInfoService {

    @Autowired
    private OBSInfoRepository obsInfoRepository;

    @Override
    @Cacheable(value = "obsInfo",key="'obsInfo_'+#obsInfoId")
    public ObsInfo findOne(int obsInfoId){
        System.out.println("obsInfo = [查询id = obsinfo" + obsInfoId + "]");
        return obsInfoRepository.getOne(obsInfoId);
    }

    @Override
    @CachePut(value = "obsInfo",key="'obsInfo_'+#obsInfo.id")
    public ObsInfo save(ObsInfo obsInfo){
        System.out.println("这里更新了clazz"+obsInfo.toString());
        return obsInfoRepository.save(obsInfo);
    }


}
