package com.atguigu.springboot.mapper;

import com.atguigu.springboot.bean.GoodsDetail;
import com.atguigu.springboot.bean.GoodsPrice;

import java.util.List;


//指定这是一个操作数据库的mapper
//@Mapper
public interface GoodsPriceMapper {


    public void insertGoodsPriceBatch(List<GoodsPrice> list);

}
