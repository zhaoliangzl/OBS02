package com.izliang.provider.model;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

/**
 * 链接上传
 *
 * **/

@Entity
@Table(name = "zl_obs_settings")
public class Settings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ApiModelProperty(value = "公共上传域名")
    @Column(name = "upload")
    private String uploadUrl;

    @ApiModelProperty(value = "私有上传域名")
    @Column(name = "private_upload")
    private String privateUpload;

    @ApiModelProperty(value = "公共下载域名+路由")
    @Column(name = "download_url")
    private String downLoadUrl;

    @ApiModelProperty(value = "私有下载域名+路由")
    @Column(name = "private_download_url")
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
