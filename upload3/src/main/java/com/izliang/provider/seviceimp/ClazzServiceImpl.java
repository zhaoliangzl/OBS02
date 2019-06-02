package com.izliang.provider.seviceimp;
/*
 *  @package com.zliang.undercover.serviceimpl
 *  @author zliang
 *  @create 2018-12-21 0:34
 *  @description：
 *
 */


import com.izliang.provider.model.Clazz;
import com.izliang.provider.model.Developer;
import com.izliang.provider.repo.ClazzRepository;
import com.izliang.provider.repo.DeveloperRepository;
import com.izliang.provider.service.ClazzService;
import com.izliang.provider.service.DeveloperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = "developerService")
public class ClazzServiceImpl implements ClazzService {

    @Autowired
    private ClazzRepository clazzRepository;

    @Override
    @Cacheable(value = "clazz",key="'clazz_'+#clazzId")
    public Clazz findOne(int clazzId){
        return clazzRepository.findOne(clazzId);
    }

    @Override
    @CachePut(value = "clazz",key="'clazz_'+#clazz.id")
    public Clazz save(Clazz clazz){
        System.out.println("这里更新了clazz"+clazz.toString());
        return clazzRepository.save(clazz);
    }


}
