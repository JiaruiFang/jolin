package com.example.demo.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Component;

import com.example.demo.domain.LearnResouce;

/**
 * mybatis 注解方式
 * @author Mr.fang
 *
 */
@Component
@Mapper
public interface LearnMapper {

	@SelectProvider(type=LearnSqlBuilder.class,method="queryLearnResouceByParams")
	public List<LearnResouce> queryLearnResouceList(Map<String, Object> params);
	
	static class LearnSqlBuilder {
		public String queryLearnResouceByParams(Map<String, Object> params) {
			StringBuffer sql = new StringBuffer();
			sql.append("select * from learn_resource where 1=1");
			if(StringUtils.isNotEmpty((String)params.get("author"))) {
				 sql.append(" and author like '%").append((String)params.get("author")).append("%'");
			}
			if(StringUtils.isNotEmpty((String)params.get("title"))) {
				sql.append(" and title like '%").append((String)params.get("title")).append("%'");
			}
			System.out.println("查询sql："+sql);
			return sql.toString();
		}
	}
	
}
