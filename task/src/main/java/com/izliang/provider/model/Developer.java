package com.izliang.provider.model;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import javafx.scene.chart.ValueAxis;

import javax.persistence.*;
import javax.print.attribute.standard.MediaSize;

@Entity
@Table(name = "developer")
public class Developer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ApiModelProperty(value = "开发者账号")
    @Column(name = "username",unique = true)
    private String username;

    @ApiModelProperty(value = "开发者密码")
    @Column(name = "password")
    private String password;

    @ApiModelProperty(value = "开发者姓名")
    @Column(name = "name")
    private String name;

    @ApiModelProperty(value = "开发者最近的登陆时间")
    @Column(name = "time")
    private String time;

    @ApiModelProperty(value = "开发者的邮箱")
    @Column(name = "eamil")
    private String eamil;

    @ApiModelProperty(value = "开发者对应的资源上传链接")
    @Column(name = "public_upload_link")
    private String publicUploadLink;

    @ApiModelProperty(value = "私有开发者上传链接")
    @Column(name = "private_upload_link")
    private String privateUpladLink;

    @ApiModelProperty(value = "私有开发者密钥")
    @Column(name = "private_secret")
    private String privateSecret;

    @ApiModelProperty(value = "用户可用的存储容量")
    @Column(name = "total_size")
    private long totalSize;

    @ApiModelProperty(value = "用户已经使用的存储容量")
    @Column(name = "use_size")
    private long useSize;

    @ApiModelProperty(value = "本月流量总计")
    @Column(name = "total_flow")
    private long totalFlow;

    @ApiModelProperty(value = "本月已经使用的流量")
    @Column(name = "use_flow")
    private long useFlow;

    @ApiModelProperty(value = "今日请求次数")
    @Column(name = "toady_request")
    private int todayRequest;

    @ApiModelProperty(value = "今日使用的流量")
    @Column(name = "today_flow")
    private long toadyFlow;

    @ApiModelProperty(value = "今日已经上传的流量")
    @Column(name = "today_size")
    private Long todaySize;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getEamil() {
        return eamil;
    }

    public void setEamil(String eamil) {
        this.eamil = eamil;
    }

    public String getPublicUploadLink() {
        return publicUploadLink;
    }

    public void setPublicUploadLink(String publicUploadLink) {
        this.publicUploadLink = publicUploadLink;
    }

    public String getPrivateUpladLink() {
        return privateUpladLink;
    }

    public void setPrivateUpladLink(String privateUpladLink) {
        this.privateUpladLink = privateUpladLink;
    }

    public String getPrivateSecret() {
        return privateSecret;
    }

    public void setPrivateSecret(String privateSecret) {
        this.privateSecret = privateSecret;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

    public long getUseSize() {
        return useSize;
    }

    public void setUseSize(long useSize) {
        this.useSize = useSize;
    }

    public long getTotalFlow() {
        return totalFlow;
    }

    public void setTotalFlow(long totalFlow) {
        this.totalFlow = totalFlow;
    }

    public long getUseFlow() {
        return useFlow;
    }

    public void setUseFlow(long useFlow) {
        this.useFlow = useFlow;
    }


    public int getTodayRequest() {
        return todayRequest;
    }

    public void setTodayRequest(int todayRequest) {
        this.todayRequest = todayRequest;
    }

    public long getToadyFlow() {
        return toadyFlow;
    }

    public void setToadyFlow(long toadyFlow) {
        this.toadyFlow = toadyFlow;
    }

    public Long getTodaySize() {
        return todaySize;
    }

    public void setTodaySize(Long todaySize) {
        this.todaySize = todaySize;
    }

    @Override
    public String toString() {
        return "Developer{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", time='" + time + '\'' +
                ", eamil='" + eamil + '\'' +
                ", publicUploadLink='" + publicUploadLink + '\'' +
                ", privateUpladLink='" + privateUpladLink + '\'' +
                ", privateSecret='" + privateSecret + '\'' +
                ", totalSize=" + totalSize +
                ", useSize=" + useSize +
                ", totalFlow=" + totalFlow +
                ", useFlow=" + useFlow +
                ", todayRequest=" + todayRequest +
                ", toadyFlow=" + toadyFlow +
                '}';
    }
}
