package com.izliang.provider.model;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.izliang.provider.config.CustomDateSerializer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Entity
@Table(name = "zl_obs_info")
public class ObsInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ApiModelProperty(value = "OBSx信息名称")
    @Column(name = "name")
    private String name;

    @ApiModelProperty(value = "连接密钥id")
    @Column(name = "accessKeyId")
    private String accessKeyId;

    @ApiModelProperty(value = "连接密钥密钥")
    @Column(name = "secretAccessKey")
    private String secretAccessKey;

    @ApiModelProperty(value = "endpoint")
    @Column(name = "endpoint")
    private String endpoint;

    @ApiModelProperty(value = "桶名称")
    @Column(name = "bucket_name")
    private String bucketName;

    @ApiModelProperty(value = "用户id")
    @Column(name = "user_id")
    private int userId;

    @ApiModelProperty(value = "添加时间")
    @Column(name = "time")
    private String time;

    @ApiModelProperty(value = "可用性")
    @Column(name = "available")
    private Boolean available;

    @ApiModelProperty(value = "分类Id")
    @Column(name = "clazz_id")
    private int clazzId;

    /**
     * 状态标志：
     *  1：只是提交，不可用
     *  2：待审核
     *  3. 正在工作
     *  4. 存储空间超限
     *  5. 访问流量超限
     *  6. 审核没有通过
     *  7. 禁用该obs
     *  8.
     * */
    @ApiModelProperty(value = "最近的审核报告id")
    @Column(name = "test_report_id")
    private Integer testReportId;

    @ApiModelProperty(value = "obs状态")
    @Column(name = "obs_info_status")
    public int obsInfoStatus;

    @ApiModelProperty(value = "审核状态")
    @Column(name = "delivery")
    private boolean delivery;

    @ApiModelProperty(value = "备注")
    @Column(name = "mark")
    private String mark;

    @ApiModelProperty(value = "共享区域大小")
    @Column(name = "sharping_size")
    private long sharpingSize;

    @ApiModelProperty(value = "已经使用的大小")
    @Column(name = "use_size")
    private long useSize;

    @ApiModelProperty(value = "剩余容量大小")
    @Column(name = "remainder_size")
    private long remainderSize;

    @ApiModelProperty(value = "请求次数")
    @Column(name = "request_time")
    private long request;

    @ApiModelProperty(value = "已经下行的流量")
    @Column(name = "downed_flow")
    private long downedFlow;

    @ApiModelProperty(value = "总的下行的流量")
    @Column(name = "down_flow")
    private long downflow;

    @ApiModelProperty(value = "obs的上传次数")
    @Column(name = "put_num")
    private Integer putNum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getSecretAccessKey() {
        return secretAccessKey;
    }

    public void setSecretAccessKey(String secretAccessKey) {
        this.secretAccessKey = secretAccessKey;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public int getClazzId() {
        return clazzId;
    }

    public void setClazzId(int clazzId) {
        this.clazzId = clazzId;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public long getSharpingSize() {
        return sharpingSize;
    }

    public void setSharpingSize(long sharpingSize) {
        this.sharpingSize = sharpingSize;
    }

    public long getUseSize() {
        return useSize;
    }

    public void setUseSize(long useSize) {
        this.useSize = useSize;
    }

    public long getRemainderSize() {
        return remainderSize;
    }

    public void setRemainderSize(long remainderSize) {
        this.remainderSize = remainderSize;
    }

    public long getRequest() {
        return request;
    }

    public void setRequest(long request) {
        this.request = request;
    }

    public long getDownedFlow() {
        return downedFlow;
    }

    public void setDownedFlow(long downedFlow) {
        this.downedFlow = downedFlow;
    }

    public long getDownflow() {
        return downflow;
    }

    public void setDownflow(long downflow) {
        this.downflow = downflow;
    }

    public int getObsInfoStatus() {
        return obsInfoStatus;
    }

    public void setObsInfoStatus(int obsInfoStatus) {
        this.obsInfoStatus = obsInfoStatus;
    }

    public boolean isDelivery() {
        return delivery;
    }

    public void setDelivery(boolean delivery) {
        this.delivery = delivery;
    }

    public Integer getTestReportId() {
        return testReportId;
    }

    public void setTestReportId(Integer testReportId) {
        this.testReportId = testReportId;
    }

    public Integer getPutNum() {
        return putNum;
    }

    public void setPutNum(Integer putNum) {
        this.putNum = putNum;
    }

    public ObsInfo() {
    }

    @Override
    public String toString() {
        return "ObsInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", accessKeyId='" + accessKeyId + '\'' +
                ", secretAccessKey='" + secretAccessKey + '\'' +
                ", endpoint='" + endpoint + '\'' +
                ", bucketName='" + bucketName + '\'' +
                ", userId=" + userId +
                ", time='" + time + '\'' +
                ", available=" + available +
                ", clazzId=" + clazzId +
                ", testReportId=" + testReportId +
                ", obsInfoStatus=" + obsInfoStatus +
                ", delivery=" + delivery +
                ", mark='" + mark + '\'' +
                ", sharpingSize=" + sharpingSize +
                ", useSize=" + useSize +
                ", remainderSize=" + remainderSize +
                ", request=" + request +
                ", downedFlow=" + downedFlow +
                ", downflow=" + downflow +
                ", putNum=" + putNum +
                '}';
    }
}
