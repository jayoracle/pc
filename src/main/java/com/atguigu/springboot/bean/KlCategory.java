package com.atguigu.springboot.bean;

import java.util.List;

/**
 * author：chenzg
 * date：2019/3/19  10:14
 * desc：
 */
public class KlCategory
{
    private Integer id;

    private String parentName;

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    private String categoryName;

    private String requestUrl;

    private List<KlGoods> goodsList;

    public List<KlGoods> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<KlGoods> goodsList) {
        this.goodsList = goodsList;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    private List<KlCategory> subList;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<KlCategory> getSubList() {
        return subList;
    }

    public void setSubList(List<KlCategory> subList) {
        this.subList = subList;
    }


}
