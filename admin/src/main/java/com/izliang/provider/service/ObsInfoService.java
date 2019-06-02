package com.izliang.provider.service;
/*
 *  @package com.zliang.undercover.service
 *  @author zliang
 *  @create 2018-12-21 1:45
 *  @description：
 *
 */


import com.izliang.provider.model.Clazz;
import com.izliang.provider.model.ObsInfo;


public interface ObsInfoService {

    ObsInfo findOne(int id);

    ObsInfo save(ObsInfo obsInfo);

}
