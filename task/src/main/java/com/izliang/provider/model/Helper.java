package com.izliang.provider.model;

import io.swagger.annotations.ApiModelProperty;
import org.dom4j.tree.AbstractEntity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "zl_helper")
public class Helper  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ApiModelProperty(value = "帮助文档标题")
    @Column(name = "title")
    private String title;

    @ApiModelProperty(value = "帮助文档内容")
    @Column(name = "content",columnDefinition="text")
    private String content;

    @ApiModelProperty(value = "helper_class_id")
    @Column(name = "helper_class_id")
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
