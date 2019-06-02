package com.izliang.provider.model;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Entity
@Table(name = "zl_obs_dev_logger")
public class Loger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "dev_id")
    private int devId;

    @Column(name = "time")
    private Long time;

    @Column(name = "time_str")
    private String timeStr;

    @ApiModelProperty(value = "每日的流量")
    @Column(name = "flow")
    private long flow;

    @ApiModelProperty(value = "今日使用的存储")
    @Column(name = "size")
    private long size;

    @ApiModelProperty(value = "今日的请求次数")
    @Column(name = "request")
    private int request;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDevId() {
        return devId;
    }

    public void setDevId(int devId) {
        this.devId = devId;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    public long getFlow() {
        return flow;
    }

    public void setFlow(long flow) {
        this.flow = flow;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public int getRequest() {
        return request;
    }

    public void setRequest(int request) {
        this.request = request;
    }
}
