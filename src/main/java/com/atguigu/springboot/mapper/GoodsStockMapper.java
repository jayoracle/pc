package com.atguigu.springboot.mapper;

import com.atguigu.springboot.bean.Brand;
import com.atguigu.springboot.bean.Stock;

import java.util.List;


//指定这是一个操作数据库的mapper
//@Mapper
public interface GoodsStockMapper {


    public void insertStockBatch(List<Stock> list);

}
