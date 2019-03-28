package com.atguigu.springboot.bean;

import java.math.BigDecimal;

/**
 * author：chenzg
 * date：2019/3/11  16:56
 * desc：
 */
public class GoodsPrice
{
    private Integer id;
    private Integer goodsId;
    private String retailer;
    private String brandReferencePrice;
    private String brandReferencePriceExVAT;
    private String country;
    private String currency;
    private String discount;
    private String netPrice;
    private String percentTax;

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

    public String getRetailer() {
        return retailer;
    }

    public void setRetailer(String retailer) {
        this.retailer = retailer;
    }



    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getPercentTax() {
        return percentTax;
    }

    public void setPercentTax(String percentTax) {
        this.percentTax = percentTax;
    }

    public String getBrandReferencePrice() {
        return brandReferencePrice;
    }

    public void setBrandReferencePrice(String brandReferencePrice) {
        this.brandReferencePrice = brandReferencePrice;
    }

    public String getBrandReferencePriceExVAT() {
        return brandReferencePriceExVAT;
    }

    public void setBrandReferencePriceExVAT(String brandReferencePriceExVAT) {
        this.brandReferencePriceExVAT = brandReferencePriceExVAT;
    }

    public String getNetPrice() {
        return netPrice;
    }

    public void setNetPrice(String netPrice) {
        this.netPrice = netPrice;
    }
}
