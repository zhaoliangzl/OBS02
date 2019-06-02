package com.izliang.provider.controller;

import com.izliang.provider.model.Developer;
import com.izliang.provider.model.Loger;
import com.izliang.provider.repo.DeveloperRepository;
import com.izliang.provider.repo.LoggerRepository;
import com.izliang.provider.utils.Utils;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Cron表达式参数分别表示：
 * <p>
 * 秒（0~59） 例如0/5表示每5秒
 * 分（0~59）
 * 时（0~23）
 * 日（0~31）的某天，需计算
 * 月（0~11）
 * 周几（ 可填1-7 或 SUN/MON/TUE/WED/THU/FRI/SAT）
 *
 * @Scheduled：除了支持灵活的参数表达式cron之外，还支持简单的延时操作，例如 fixedDelay ，fixedRate 填写相应的毫秒数即可。
 */


@Component
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class SaticScheduleTask {

    @Autowired
    private DeveloperRepository developerRepository;

    @Autowired
    private LoggerRepository loggerRepository;

    //3.添加定时任务
    //每天1点执行日志记录信息
    @Scheduled(cron = "0 0 0 0/1 * ?")
    //或直接指定时间间隔，例如：5秒
    //@Scheduled(fixedRate=5000)
    private void configureTasks() {
        System.err.println("执行静态定时任务时间: " + LocalDateTime.now());
        //定时任务
        int num = developerRepository.countAll();
        int DevIndex = 0;
        Developer developer;
        for (int i = 0; i < num; i++) {

            //开始查询第一个
            boolean f = true;
            int index = 1;
            while (f) {
                developer = developerRepository.findOne(index++);
                if (developer == null) {
                } else {
                    Loger loger = new Loger();
                    loger.setDevId(developer.getId());
                    loger.setTime(System.currentTimeMillis());
                    loger.setTimeStr(Utils.getNowDate());
                    loger.setSize(developer.getTodaySize());
                    loger.setFlow(developer.getToadyFlow());
                    loger.setRequest(developer.getTodayRequest());

                    Loger l = loggerRepository.save(loger);

                    developer.setToadyFlow(0);
                    developer.setTodayRequest(0);
                    developer.setTodaySize((long) 0);
                    developerRepository.save(developer);
                    f = false;
                }
            }
        }


    }
}