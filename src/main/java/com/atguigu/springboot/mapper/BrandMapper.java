package com.atguigu.springboot.mapper;

import com.atguigu.springboot.bean.Brand;
import com.atguigu.springboot.bean.Department;
import org.apache.ibatis.annotations.*;

import java.util.List;


//指定这是一个操作数据库的mapper
//@Mapper
public interface BrandMapper {


    public void insertBrandBatch(List<Brand> list);

}
