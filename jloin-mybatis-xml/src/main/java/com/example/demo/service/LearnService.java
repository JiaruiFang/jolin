package com.example.demo.service;

import java.util.List;
import java.util.Map;

import com.example.demo.tools.Page;


public interface LearnService {

	//Page是分页对象
	List queryLearnResouceList(Map<String,Object> params);
	
}
