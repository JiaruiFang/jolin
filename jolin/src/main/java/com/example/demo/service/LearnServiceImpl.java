package com.example.demo.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.LearnDao;
import com.example.demo.tools.Page;

@Service
public class LearnServiceImpl implements LearnService {

	@Autowired
	private LearnDao learnDao;
	
	public Page queryLearnResouceList(Map<String, Object> params) {
		Page page = learnDao.queryLearnResouceList(params);
		return page;
	}

}
