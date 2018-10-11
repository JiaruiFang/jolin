package com.example.demo.dao;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.tools.Page;

@Repository
public class LearnDaoimpl implements LearnDao {

	//引入 JdbcTemplate模板 springboot之JdbcTemplate的使用
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public Page queryLearnResouceList(Map<String, Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from learn_resource where 1=1");
		if(StringUtils.isNotEmpty((String)params.get("author"))) {
			sql.append(" and author like '%").append((String)params.get("author")).append("%'");
		}
		if(StringUtils.isNotEmpty((String)params.get("title"))) {
			sql.append(" and title like '%").append((String)params.get("title")).append("%'");
		}
		Page page = new Page(sql.toString(), 2, 2, jdbcTemplate);
		return page;
	}

}
