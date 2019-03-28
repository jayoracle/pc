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

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * author：chenzg
 * date：2019/3/19  15:56
 * desc：
 */
public class KaolaRunnable2 implements  Runnable {

    private KlCategory category;
    public KaolaRunnable2(){
    }
    public KaolaRunnable2(KlCategory category){
        this.category=category;
    }
    @Override
    public void run() {
        //String rrUrl=category.getRequestUrl();
        try {
            List<KlCategory> subList= category.getSubList();
            for(int i=0;i<subList.size();i++){
                KlCategory cc=subList.get(i);
                List<KlGoods> goodsList=getGoods(cc.getRequestUrl());
                cc.setGoodsList(goodsList);
            }
            createExcel(category);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public  List<KlGoods> getGoods(String categoryUrl) throws  Exception{

        Document document = Jsoup.connect(categoryUrl).timeout(6000).get();

        List<KlGoods> datalist=new ArrayList<>();

        Elements select = document.select(".simplePage .num");
        String pageStr=select.html().replace("<i>1</i>/","");
        if(pageStr!=null&&!"".equals(pageStr)){
            int pages=Integer.parseInt(pageStr);
            for (int i=1;i<=pages;i++){
                String pageUrl=categoryUrl+"?key=&pageSize=60&pageNo=%s&sortfield=0&isStock=false&isSelfProduct=false&isPromote=false&isTaxFree=false&factoryStoreTag=-1&isCommonSort=false&isDesc=true&b=&proIds=&source=false&country=&needBrandDirect=false&isNavigation=0&lowerPrice=-1&upperPrice=-1&backCategory=&headCategoryId=&#topTab";
                pageUrl=String.format(pageUrl,i+"");
                System.out.println("request_address=="+pageUrl);
                List<KlGoods> goodsLists = getGoodsList(pageUrl);
                datalist.addAll(goodsLists);
            }
        }
        System.out.println("datalist=="+datalist.size()+"=="+datalist);
        return datalist;
    }


    public  List<KlGoods> getGoodsList(String goodsUrl) throws Exception
    {
        Document document = Jsoup.connect(goodsUrl).timeout(6000).get();
        List<KlGoods> goodsList=new ArrayList<>();
        KlGoods kg=null;
        Elements elements = document.select(".goods");

        for(int k=0;k<elements.size();k++){
            Element next=elements.get(k);
            kg=new KlGoods();
            String priceStr=next.select(".price .cur").html();
            priceStr= priceStr.replace("<i>¥</i>","");
            priceStr=priceStr.replace("<span class=\"curtxt\">预定价</span>","");
            kg.setPrice(priceStr);
            Element element=next.select(".titlewrap .title").get(0);
            String goodsName = element.attr("title");
            String goodUrl=element.attr("href");
            kg.setGoodsName(goodsName);
            kg.setGoodsUrl("https:"+goodUrl);
            String country=next.select(".goodsinfo .proPlace").html();
            kg.setCountry(country);

            Pattern p = Pattern.compile("\\d+");
            Matcher m = p.matcher(goodUrl);
            if(m.find()){
                String ss=m.group();
                Integer goodsId=Integer.parseInt(ss);
                kg.setId(goodsId);
                //List<KlComment> commentList = getcommentList(goodsId); //获取评论
                //kg.setCommentList(commentList);
                String brandName=getBrandName(kg.getGoodsUrl());
                kg.setBrandName(brandName);
            }
            goodsList.add(kg);
        }
        return goodsList;
    }

    public  String getBrandName(String goodsUrl) throws Exception{
        Document document = Jsoup.connect(goodsUrl).timeout(6000).get();
        return document.select(".brand").get(0).html();
    }

    public  List<KlComment> getcommentList(Integer goodsId) throws Exception{

        List<KlComment> commentList=new ArrayList<>();
        KlComment comment=null;
        String commentUrl="https://comment.kaola.com/commentAjax/comment_list_new.html";
        Map<String,String> param=new HashMap<String,String>();
        param.put("goodsId",goodsId+"");
        param.put("tagType","0");
        param.put("grade","0");
        param.put("hasContent","1");
        param.put("pageNo","1");
        param.put("pageSize","3");
        String sss = HttpUtil.post(commentUrl,param);
        JSONObject commentPage = JSONObject.parseObject(sss).getJSONObject("commentPage");
        JSONArray result = commentPage.getJSONArray("result");
        for(int i=0;i<result.size();i++){
            JSONObject jsonObject = result.getJSONObject(i);
            String commentContent=jsonObject.getString("commentContent");
            System.out.println("评论：=="+commentContent);
            comment=new KlComment();
            comment.setCommentContent(commentContent);
            comment.setGoodsId(goodsId);
            commentList.add(comment);
        }
        return commentList;
    }




    public static void createExcel(KlCategory klCategory) throws  Exception{
        if(klCategory!=null){
            String name=klCategory.getCategoryName(); //文件名
            name=name.replace("/","");
            List<KlCategory> categoryList=klCategory.getSubList();
            HSSFWorkbook workbook = new HSSFWorkbook();
            for(int i=0;i<categoryList.size();i++){
                KlCategory category=categoryList.get(i);
                String sheetName=category.getCategoryName();
                sheetName=sheetName.replace("/","");
                HSSFSheet sheet = workbook.createSheet(sheetName);
                workbook.setSheetName(i,sheetName);
                System.out.println("创建sheet"+category.getCategoryName());
                HSSFRow row0 = sheet.createRow(0);
                row0.createCell(0).setCellValue("商品名称");
                row0.createCell(1).setCellValue("商品价格");
                row0.createCell(2).setCellValue("商品链接");
                row0.createCell(3).setCellValue("商品国家");
                row0.createCell(4).setCellValue("商品品牌");
                //row0.createCell(4).setCellValue("商品评价");
                List<KlGoods> goodsList= category.getGoodsList();
                for(int j=0;j<goodsList.size();j++){
                    HSSFRow row = sheet.createRow(j+1);
                    KlGoods goods= goodsList.get(j);
                    row.createCell(0).setCellValue(goods.getGoodsName());
                    row.createCell(1).setCellValue(goods.getPrice());
                    row.createCell(2).setCellValue(goods.getGoodsUrl());
                    row.createCell(3).setCellValue(goods.getCountry());
                    row.createCell(4).setCellValue(goods.getBrandName());
                    List<KlComment> commentList= goods.getCommentList();
                    StringBuffer sb =new StringBuffer();
                    if(commentList!=null){
                        for(KlComment comment:commentList){
                            sb.append(comment).append("，\n");
                        }
                    }
                }
            }
            buildExcelFile("kaola3/"+name+".xls",workbook);


        }


    }

    //生成excel文件
    protected static void buildExcelFile(String filename,HSSFWorkbook workbook) throws Exception{
        File file=new File(filename);
        File fileParent = file.getParentFile();
        if(!fileParent.exists()){
            fileParent.mkdirs();
        }
        FileOutputStream fos = new FileOutputStream(file);
        workbook.write(fos);
        fos.flush();
        fos.close();
    }


}
