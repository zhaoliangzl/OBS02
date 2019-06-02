package com.izliang.provider.model;


import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

/**
 * obs的测试
 *
 * */
@Entity
@Table(name = "zl_obs_test")
public class TestReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ApiModelProperty(value = "obs信息id")
    @Column(name = "obs_id")
    private int obsId;

    @ApiModelProperty(value = "测试报告类型")
    @Column(name = "type")
    private String type;

    @ApiModelProperty(value = "测试时间")
    @Column(name = "time")
    private String time;

    @ApiModelProperty(value = "测试结果")
    @Column(name = "result",length = 9999)
    private String result;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getObsId() {
        return obsId;
    }

    public void setObsId(int obsId) {
        this.obsId = obsId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ObsTest{" +
                "id=" + id +
                ", obsId=" + obsId +
                ", type='" + type + '\'' +
                ", time=" + time +
                ", result='" + result + '\'' +
                '}';
    }
}
