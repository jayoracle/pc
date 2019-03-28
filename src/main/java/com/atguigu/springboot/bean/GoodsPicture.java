package com.atguigu.springboot.bean;

/**
 * author：chenzg
 * date：2019/3/11  14:20
 * desc：
 */
public class GoodsPicture {

    private Integer id;

    private Integer goodsId;

    private Integer no;

    private String pictureThumbUrl;

    private String pictureUrl;

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public String getPictureThumbUrl() {
        return pictureThumbUrl;
    }

    public void setPictureThumbUrl(String pictureThumbUrl) {
        this.pictureThumbUrl = pictureThumbUrl;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
}
