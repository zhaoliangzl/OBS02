package com.izliang.provider.model.tmp;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Entity
@Table(name = "zl_obs_tmp_register_session")
public class TmpForSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ApiModelProperty(value = "邮箱验证码，由于分布式session暂时不可用，所以使用数据库代替")
    @Column(name = "code")
    private String code;

    @ApiModelProperty(value = "邮箱")
    @Column(name = "eamil")
    private String email;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
