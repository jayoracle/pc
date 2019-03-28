package com.atguigu.springboot.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.atguigu.springboot.bean.*;
import com.atguigu.springboot.mapper.*;
import com.atguigu.springboot.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


@RestController
public class CategoryController {


    private String url="http://82.192.84.228/atelier_hub/";
    @Autowired
    private CategoryMapper categoryMapper;

    @GetMapping("/category/{id}")
    public Category getCategory(@PathVariable("id") Integer id){
        return categoryMapper.getCategoryById(id);
    }


    @PostMapping("/save")
    public Category save(@RequestBody  Category category){
        category.setCreateDate(new Date());
        categoryMapper.insertCategory(category);
        return  category;
    }

    /**
     * 同步分类数据
     */
    @GetMapping("/synCategoryList")
    public  List<Category> synCategoryList(){
        String requestName="CategoryList";
        //String param="pageNum=2&pageSize=10";
        String ss = HttpUtil.sendGet(url+requestName, "");
        JSONObject object = JSONObject.parseObject(ss);
        JSONObject categoryList1 = object.getJSONObject("CategoryList");
        JSONArray categoryList =categoryList1.getJSONArray("Category");
        List<Category> list=new ArrayList<Category>();
        Date createDate=new Date();
        for (int i=0;i<categoryList.size();i++)
        {
            JSONObject jsonObject = categoryList.getJSONObject(i);
            Category category=new Category();
            category.setCategoryId(jsonObject.getInteger("ID"));
            category.setName(jsonObject.getString("Name"));
            category.setParentId(jsonObject.getInteger("ParentID"));
            category.setParentName(jsonObject.getString("ParentName"));
            category.setCreateDate(createDate);
            category.setGenderId(jsonObject.getString("GenderID"));
            list.add(category);
        }
        categoryMapper.insertByBatch(list);
        return list;
    }

    @Autowired
    private BrandMapper brandMapper;

    /**
     * 同步分类数据
     */
    @GetMapping("/synBrandList")
    public  String  synBrandList(){
        String requestName="BrandList";
        //String param="pageNum=2&pageSize=10";
        String ss = HttpUtil.sendGet(url+requestName, "");
        JSONObject object = JSONObject.parseObject(ss);
        JSONObject categoryList1 = object.getJSONObject("BrandList");
        JSONArray brandlist =categoryList1.getJSONArray("Brand");
        List<Brand> list=new ArrayList<Brand>();
        Brand brand=null;
        for (int i=0;i<brandlist.size();i++)
        {
            JSONObject jsonObject = brandlist.getJSONObject(i);
            brand=new Brand();
            brand.setId(jsonObject.getInteger("ID"));
            brand.setName(jsonObject.getString("Name"));
            list.add(brand);
        }
        brandMapper.insertBrandBatch(list);
        return ss;
    }


    @Autowired
    private GoodsMapper goodsMapper;

    /**
     * 同步商品
     */
    @GetMapping("/synGoodsList")
    public  String   synGoodsList(){
        String requestName="GoodsList";
        //String param="pageNum=1&pageSize=100";
        String ss = HttpUtil.sendGet(url+requestName, "");
        JSONObject object = JSONObject.parseObject(ss);
        JSONObject categoryList1 = object.getJSONObject("GoodsList");
        JSONArray goodslist =categoryList1.getJSONArray("Good");
        List<Goods> list=null;
        List<List<Goods>> newList=new ArrayList<List<Goods>>();
        Goods goods=null;
        for (int i=0;i<goodslist.size();i++)
        {
            JSONObject jsonObject = goodslist.getJSONObject(i);
            System.out.println("jsonObject"+i+"=="+jsonObject);
            goods=new Goods();
            goods.setId(jsonObject.getInteger("ID"));
            goods.setGoodsName(jsonObject.getString("GoodsName"));
            goods.setBrandId(jsonObject.getInteger("BrandID"));
            goods.setCategoryId(jsonObject.getInteger("CategoryID"));
            goods.setCode(jsonObject.getString("Code"));
            goods.setCollection(jsonObject.getString("Collection"));
            goods.setCreatedTime(jsonObject.getDate("CreatedTime"));
            goods.setGenderId(jsonObject.getInteger("GenderID"));
            goods.setInStock(jsonObject.getInteger("InStock"));
            goods.setMainPicture(jsonObject.getString("MainPicture"));
            goods.setModel(jsonObject.getString("Model"));
            goods.setSeason(jsonObject.getString("Season"));
            goods.setParentCategoryId(jsonObject.getInteger("ParentCategoryID"));
            goods.setSupplierId(jsonObject.getInteger("SupplierID"));
            goods.setModifiedTime(jsonObject.getDate("ModifiedTime"));
            goods.setVariant(jsonObject.getString("Variant"));
            if(i%1000==0){
                list=new  ArrayList<Goods>();
                newList.add(list);
            }
            list.add(goods);
        }
        for(int i=0;i<newList.size();i++){
            System.out.println("jsonObject"+i+"=="+newList.get(i));
            goodsMapper.insertGoodsBatch(newList.get(i));
        }
        System.out.println("allcount========================"+goodslist.size());
        return ss;
    }

    @Autowired
    private GoodsDetailMapper goodsDetailMapper;

    @Autowired
    private GoodsPictureMapper goodsPictureMapper;

    /**
     * 同步商品详情
     */
    @GetMapping("/synGoodsDetailList")
    public  String   synGoodsDetailList(){
        String requestName="GoodsDetailList";
        //String param="pageNum=1&pageSize=100";
        String ss = HttpUtil.sendGet(url+requestName, "");
        JSONObject object = JSONObject.parseObject(ss);
        JSONObject categoryList1 = object.getJSONObject("GoodsDetailList");
        JSONArray goodsDetaillist =categoryList1.getJSONArray("Good");
        List<GoodsDetail> list=null;
        List<List<GoodsDetail>> newList=new ArrayList<List<GoodsDetail>>();
        GoodsDetail goodsDetail=null;


        GoodsPicture goodsPicture=null;
        List<GoodsPicture> piclist=new ArrayList<GoodsPicture>();
        List<List<GoodsPicture>> newPrcList=new ArrayList<List<GoodsPicture>>();

        int index=0;
        for (int i=0;i<goodsDetaillist.size();i++)
        {
            JSONObject jsonObject = goodsDetaillist.getJSONObject(i);
            System.out.println("jsonObject"+i+"=="+jsonObject);
            goodsDetail=new GoodsDetail();
            goodsDetail.setGoodsId(jsonObject.getInteger("ID"));
            goodsDetail.setColor(jsonObject.getString("Color"));
            goodsDetail.setComposition(jsonObject.getString("Composition"));
            goodsDetail.setFabric(jsonObject.getString("Fabric"));
            goodsDetail.setMadeIn(jsonObject.getString("MadeIn"));
            goodsDetail.setSizeAndFit(jsonObject.getString("SizeAndFit"));
            goodsDetail.setCreatedTime(jsonObject.getDate("CreatedTime"));
            goodsDetail.setModifiedTime(jsonObject.getDate("ModifiedTime"));
            goodsDetail.setSuperColor(jsonObject.getString("SuperColor"));

            JSONObject Pictures = jsonObject.getJSONObject("Pictures");
            JSONArray pictures = Pictures.getJSONArray("Picture");
            for(int j=0;j<pictures.size();j++){
                goodsPicture=new GoodsPicture();
                JSONObject jsonObject1 = pictures.getJSONObject(j);
                goodsPicture.setGoodsId(jsonObject.getInteger("ID"));
                goodsPicture.setNo(jsonObject1.getInteger("No"));
                goodsPicture.setPictureThumbUrl(jsonObject1.getString("PictureThumbUrl"));
                goodsPicture.setPictureUrl(jsonObject1.getString("PictureUrl"));
                if(index%1000==0){
                    piclist=new  ArrayList<GoodsPicture>();
                    newPrcList.add(piclist);
                }
                index++;
                piclist.add(goodsPicture);
            }

            if(i%1000==0){
                list=new  ArrayList<GoodsDetail>();
                newList.add(list);
            }
            list.add(goodsDetail);
        }
        for(int i=0;i<newList.size();i++){
            System.out.println("newList"+i+"=="+newList.get(i));
            goodsDetailMapper.insertGoodsDetailBatch(newList.get(i));
        }

        System.out.println("GoodsDetail===============count===="+goodsDetaillist.size());
        for(int i=0;i<newPrcList.size();i++){
            System.out.println("newPrcList"+i+"=="+newPrcList.get(i));
            goodsPictureMapper.insertGoodsPictureBatch(newPrcList.get(i));
        }
        System.out.println("goodsPicture===============count===="+index);
        return ss;
    }


    @Autowired
    private GoodsPriceMapper goodsPriceMapper;


    /**
     * 同步分类数据
     */
    @GetMapping("/synGoodsPriceList")
    public  String  synGoodsPriceList(){
        String requestName="GoodsPriceList";
        //String param="pageNum=2&pageSize=10";
        String ss = HttpUtil.sendGet(url+requestName, "");
        JSONObject object = JSONObject.parseObject(ss);
        JSONObject categoryList1 = object.getJSONObject("GoodsPriceList");
        JSONArray pricelist =categoryList1.getJSONArray("Price");
        List<GoodsPrice> list=null;
        List<List<GoodsPrice>> priceList=new ArrayList<List<GoodsPrice>>();
        int index=0;
        GoodsPrice goodsPrice=null;
        for (int i=0;i<pricelist.size();i++)
        {
            JSONObject jsonObject = pricelist.getJSONObject(i);
            JSONArray retailers = jsonObject.getJSONArray("Retailers");
            Integer id=jsonObject.getInteger("ID");
            for(int j=0;j<retailers.size();j++){
                goodsPrice=new GoodsPrice();
                JSONObject jsonObject1 = retailers.getJSONObject(j);
                goodsPrice.setGoodsId(id);
                String BrandReferencePrice=jsonObject1.getString("BrandReferencePrice");
                BrandReferencePrice=BrandReferencePrice.split(",")[0];
                goodsPrice.setBrandReferencePrice(BrandReferencePrice);
                goodsPrice.setBrandReferencePriceExVAT(jsonObject1.getString("BrandReferencePriceExVAT").split(",")[0]);
                goodsPrice.setCountry(jsonObject1.getString("Country"));
                goodsPrice.setDiscount(jsonObject1.getString("Discount"));
                goodsPrice.setNetPrice(jsonObject1.getString("NetPrice").split(",")[0]);
                goodsPrice.setPercentTax(jsonObject1.getString("PercentTax"));
                goodsPrice.setRetailer(jsonObject1.getString("Retailer"));
                goodsPrice.setCurrency(jsonObject1.getString("Currency"));
                if(index%1000==0){
                    list=new  ArrayList<GoodsPrice>();
                    priceList.add(list);
                }
                index++;
                list.add(goodsPrice);
            }

        }
        for(int i=0;i<priceList.size();i++){
            System.out.println("priceList"+i+"=="+priceList.get(i));
            goodsPriceMapper.insertGoodsPriceBatch(priceList.get(i));
        }
        return ss;
    }


    @Autowired
    private GoodsStockMapper goodsStockMapper;
    /**
     * 同步库存数据
     */
    @GetMapping("/synStockList")
    public  String  synStockList(){
        String requestName="GoodsStockList";
        //String param="pageNum=1&pageSize=10";
        String ss = HttpUtil.sendGet(url+requestName, "");
        JSONObject object = JSONObject.parseObject(ss);
        JSONObject categoryList1 = object.getJSONObject("GoodsStockList");
        JSONArray goodsStockList =categoryList1.getJSONArray("Goods");
        List<List<Stock>> newList=new ArrayList<List<Stock>>();
        List<Stock> datalist=null;
        Stock stock=null;
        int indx=0;
        for (int i=0;i<goodsStockList.size();i++)
        {
            JSONObject jsonObject = goodsStockList.getJSONObject(i);
            Integer id=jsonObject.getInteger("ID");
            JSONObject stocks = jsonObject.getJSONObject("Stocks");
            JSONArray stocklist = stocks.getJSONArray("Stock");
            for(int j=0;j<stocklist.size();j++){
                JSONObject jsonObject11 =stocklist.getJSONObject(j);
                stock=new Stock();
                stock.setGoodsId(id);
                stock.setBarcode(jsonObject11.getString("Barcode"));
                stock.setSize(jsonObject11.getString("Size"));
                stock.setQty(jsonObject11.getString("Qty"));
                if(indx%1000==0){
                    datalist=new  ArrayList<Stock>();
                    newList.add(datalist);
                }
                datalist.add(stock);
                indx++;
            }

        }
        for(int i=0;i<newList.size();i++){
            System.out.println("priceList"+i+"=="+newList.get(i));
            goodsStockMapper.insertStockBatch(newList.get(i));
        }
        return ss;
    }

}
