package com.atguigu.springboot.mapper;

import com.atguigu.springboot.bean.Category;
import com.atguigu.springboot.bean.Employee;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

//@Mapper或者@MapperScan将接口扫描装配到容器中
public interface CategoryMapper  {

    public Category getCategoryById(Integer id);

    public int insertCategory(Category category);

    public void insertByBatch(List<Category> list);

}
