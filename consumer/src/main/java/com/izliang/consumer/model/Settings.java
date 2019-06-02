package com.izliang.consumer.model;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

/**
 * 链接上传
 *
 * **/

public class Settings {


    private Integer id;

    @ApiModelProperty(value = "公共上传域名")
    private String uploadUrl;

    @ApiModelProperty(value = "私有上传域名")
    private String privateUpload;

    @ApiModelProperty(value = "公共下载域名+路由")
    private String downLoadUrl;

    @ApiModelProperty(value = "私有下载域名+路由")
    private String privateDownLoadUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUploadUrl() {
        return uploadUrl;
    }

    public void setUploadUrl(String uploadUrl) {
        this.uploadUrl = uploadUrl;
    }

    public String getDownLoadUrl() {
        return downLoadUrl;
    }

    public void setDownLoadUrl(String downLoadUrl) {
        this.downLoadUrl = downLoadUrl;
    }

    public String getPrivateUpload() {
        return privateUpload;
    }

    public void setPrivateUpload(String privateUpload) {
        this.privateUpload = privateUpload;
    }

    public String getPrivateDownLoadUrl() {
        return privateDownLoadUrl;
    }

    public void setPrivateDownLoadUrl(String privateDownLoadUrl) {
        this.privateDownLoadUrl = privateDownLoadUrl;
    }
}
