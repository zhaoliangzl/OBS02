package com.izliang.provider.seviceimp;
/*
 *  @package com.zliang.undercover.serviceimpl
 *  @author zliang
 *  @create 2018-12-21 0:34
 *  @description：
 *
 */


import com.izliang.provider.model.Developer;
import com.izliang.provider.repo.DeveloperRepository;
import com.izliang.provider.service.DeveloperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = "developerService")
public class DeveloperServiceImpl implements DeveloperService {

    @Autowired
    private DeveloperRepository developerRepository;

    @Override
    @Cacheable(value = "developer",key="'developer_'+#developerId")
    public Developer findOne(int developerId){
        return developerRepository.findOne(developerId);
    }

    @Override
    @CachePut(value = "developer",key="'developer_'+#developer.id")
    public Developer save(Developer developer){
        System.out.println("这里更新了developer"+developer.toString());
        return developerRepository.save(developer);
    }


}
