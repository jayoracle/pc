package com.atguigu.springboot;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.atguigu.springboot.bean.KlCategory;
import com.atguigu.springboot.bean.KlComment;
import com.atguigu.springboot.bean.KlGoods;
import com.atguigu.springboot.util.HttpUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpringBoot06DataMybatisApplicationTests {


    public  static String requestUrl="https://search.kaola.com/category/%s.html";

	public static void main(String[] args) throws Exception {

        String  ajaxUrl="https://search.kaola.com/api/getFrontCategory.shtml";
        String sss = HttpUtil.sendGet(ajaxUrl, "");
        JSONObject object = JSONObject.parseObject(sss).getJSONObject("body");
        getList(object);
    }


	public static void getList(JSONObject object) throws Exception
    {
        List<String> brandList=new ArrayList<>();
        List<KlCategory> sublist=null;
        JSONArray frontCategoryList = object.getJSONArray("frontCategoryList");
        for(int i=0;i<frontCategoryList.size();i++){
            JSONObject jsonObject = frontCategoryList.getJSONObject(i);
            KlCategory  kc=new KlCategory();
            kc.setId(jsonObject.getInteger("categoryId"));
            kc.setCategoryName(jsonObject.getString("categoryName"));
            JSONArray childrenNodeList = jsonObject.getJSONArray("childrenNodeList");
            sublist=new ArrayList<>();
            for(int j=0;j<childrenNodeList.size();j++){
                JSONObject jsonObject1 = childrenNodeList.getJSONObject(j);
                KlCategory subKc=new KlCategory();
                int id=jsonObject1.getInteger("categoryId");
                subKc.setId(id);
                String rrUrl=String.format(requestUrl,id+"");
                System.out.println(rrUrl);
                subKc.setRequestUrl( rrUrl);
                subKc.setCategoryName(jsonObject1.getString("categoryName"));
                subKc.setParentName(kc.getCategoryName());
                sublist.add(subKc);
            }
            kc.setSubList(sublist);
            Thread thread1 = new Thread(new KaolaRunnable2(kc));
            thread1.start();
            //brandList
        }
    }


}
