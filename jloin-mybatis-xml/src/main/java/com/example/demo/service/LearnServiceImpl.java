package com.example.demo.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.LearnMapper;
import com.example.demo.domain.LearnResouce;
import com.github.pagehelper.PageHelper;

@Service
public class LearnServiceImpl implements LearnService {

	@Autowired
	private LearnMapper learnMapper;
	
	public List queryLearnResouceList(Map<String, Object> params) {
		//LearnMapper.LearnSqlBuilder.queryLearnResouceByParams(params);
		//String sql = new LearnMapper.LearnSqlBuilder().queryLearnResouceByParams(params);
		PageHelper.startPage(1, 1);
		List<LearnResouce> list = learnMapper.queryLearnResouceList(params);
		return list;
	}

}
