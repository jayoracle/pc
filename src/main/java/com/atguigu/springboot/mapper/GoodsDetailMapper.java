package com.atguigu.springboot.mapper;

import com.atguigu.springboot.bean.Goods;
import com.atguigu.springboot.bean.GoodsDetail;

import java.util.List;


//指定这是一个操作数据库的mapper
//@Mapper
public interface GoodsDetailMapper {


    public void insertGoodsDetailBatch(List<GoodsDetail> list);

}
