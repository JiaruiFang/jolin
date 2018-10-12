package com.example.demo.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import com.example.demo.domain.LearnResouce;

@Component
@Mapper// xml配置文件的方式保持映射文件的传统 系统自动根据方法名在映射文件中找对应的sql
public interface LearnMapper {

	public List<LearnResouce> queryLearnResouceList(Map<String, Object> params);
	
}
