package com.atguigu.springboot.bean;

/**
 * author：chenzg
 * date：2019/3/19  13:42
 * desc：
 */
public class KlComment {
    private Integer id;
    private Integer goodsId;
    private String commentContent;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }
}
