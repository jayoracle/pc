package com.atguigu.springboot.bean;

import java.util.Date;

/**
 * 商品详情
 * author：chenzg
 * date：2019/3/9  18:45
 * desc：
 */
public class GoodsDetail {

    private Integer id;

    private Integer goodsId;

    private String color;

    private String composition;

    private String fabric;

    private String madeIn;

    private Date modifiedTime;

    private Date createdTime;

    private String sizeAndFit;

    private String superColor;

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getSizeAndFit() {
        return sizeAndFit;
    }

    public void setSizeAndFit(String sizeAndFit) {
        this.sizeAndFit = sizeAndFit;
    }

    public String getSuperColor() {
        return superColor;
    }

    public void setSuperColor(String superColor) {
        this.superColor = superColor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getComposition() {
        return composition;
    }

    public void setComposition(String composition) {
        this.composition = composition;
    }

    public String getFabric() {
        return fabric;
    }

    public void setFabric(String fabric) {
        this.fabric = fabric;
    }

    public String getMadeIn() {
        return madeIn;
    }

    public void setMadeIn(String madeIn) {
        this.madeIn = madeIn;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}
