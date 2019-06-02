package com.izliang.consumer.model;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;


public class Helper  {


    private int id;

    @ApiModelProperty(value = "帮助文档标题")
    private String title;

    @ApiModelProperty(value = "帮助文档内容")
    private String content;

    @ApiModelProperty(value = "helper_class_id")
    private int helperClassId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getHelperClassId() {
        return helperClassId;
    }

    public void setHelperClassId(int helperClassId) {
        this.helperClassId = helperClassId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
