package com.izliang.consumer.model;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;

@Entity
@Table(name = "zl_obs_upload_info")
public class UploadInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ApiModelProperty(value = "原文件名")
    @Column(name = "original_name")
    private String originalName;

    @ApiModelProperty(value = "新文件名")
    @Column(name = "real_name")
    private String real_name;

    @ApiModelProperty(value = "上传时间")
    @Column(name = "upload_time")
    private String uploadTime;

    @ApiModelProperty(value = "上传IP")
    @Column(name = "upload_ip")
    private String uploadIp;

    @ApiModelProperty(value = "上传令牌")
    @Column(name = "upload_secret")
    private String uploadSecret;

    @ApiModelProperty(value = "真实地址")
    @Column(name = "real_url")
    private String realUrl;

    @ApiModelProperty(value = "文件大小")
    @Column(name = "size")
    private Long size;

    @ApiModelProperty(value = "文件访问权限")
    @Column(name = "enable")
    private Boolean enable;

    @ApiModelProperty(value = "实际访问地址")
    @Column(name = "download_url")
    private String downloadUrl;

    @ApiModelProperty(value = "sk")
    @Column(name = "sk")
    private String sk;

    @ApiModelProperty(value = "ak")
    @Column(name = "ak")
    private String ak;

    @ApiModelProperty(value = "endpoint")
    @Column(name = "endpoint")
    private String endpoint;

    @ApiModelProperty(value = "bucketname")
    @Column(name = "bucket_name")
    private String bucketName;

    @ApiModelProperty(value = "文件的md5值")
    @Column(name = "md5")
    private String md5;

    @ApiModelProperty(value = "obs的id")
    @Column(name = "obs_info_id")
    private int obsInfoId;

    @ApiModelProperty(value = "文件类型：私有的？公有的？如果为true则为私有的，如果未false或者null则为公有的")
    @Column(name = "type")
    private Boolean type;

    @ApiModelProperty(value = "文件上传人的id")
    @Column(name = "dev_id")
    private int devId;

//    @ApiModelProperty(value = "存放的")
//    @Column(name = "size")
//    private Long size;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getUploadIp() {
        return uploadIp;
    }

    public void setUploadIp(String uploadIp) {
        this.uploadIp = uploadIp;
    }

    public String getUploadSecret() {
        return uploadSecret;
    }

    public void setUploadSecret(String uploadSecret) {
        this.uploadSecret = uploadSecret;
    }

    public String getRealUrl() {
        return realUrl;
    }

    public void setRealUrl(String realUrl) {
        this.realUrl = realUrl;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getSk() {
        return sk;
    }

    public void setSk(String sk) {
        this.sk = sk;
    }

    public String getAk() {
        return ak;
    }

    public void setAk(String ak) {
        this.ak = ak;
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

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public int getObsInfoId() {
        return obsInfoId;
    }

    public void setObsInfoId(int obsInfoId) {
        this.obsInfoId = obsInfoId;
    }

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    public int getDevId() {
        return devId;
    }

    public void setDevId(int devId) {
        this.devId = devId;
    }

    @Override
    public String toString() {
        return "UploadInfo{" +
                "id=" + id +
                ", originalName='" + originalName + '\'' +
                ", real_name='" + real_name + '\'' +
                ", uploadTime='" + uploadTime + '\'' +
                ", uploadIp='" + uploadIp + '\'' +
                ", uploadSecret='" + uploadSecret + '\'' +
                ", realUrl='" + realUrl + '\'' +
                ", size=" + size +
                ", enable=" + enable +
                ", downloadUrl='" + downloadUrl + '\'' +
                ", sk='" + sk + '\'' +
                ", ak='" + ak + '\'' +
                ", endpoint='" + endpoint + '\'' +
                ", bucketName='" + bucketName + '\'' +
                ", md5='" + md5 + '\'' +
                ", obsInfoId=" + obsInfoId +
                ", type=" + type +
                ", devId=" + devId +
                '}';
    }
}
