package com.example.demo.service.impl;

import java.util.List;

import org.hibernate.jpamodelgen.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.example.demo.domain.QuartzJob;
import com.example.demo.repository.QuartzJobRepository;
import com.example.demo.service.QuartzJobService;
import com.example.demo.task.QuartzJobManager;


@Service
public class QuartzJobServiceImpl implements QuartzJobService {

	@Autowired
	private QuartzJobRepository quartzJobRepository;

	@Autowired
	private QuartzJobManager quartzJobManager;
	
	public List<QuartzJob> findByJobStatus(String jobStatus) {
		List<QuartzJob> list = quartzJobRepository.findByJobStatus(jobStatus);
		return list;
	}

	public void createQuartzJob(QuartzJob Job) {
		// 将任务保存到数据库
		QuartzJob quartzJob = quartzJobRepository.save(Job);
		if(!StringUtils.isEmpty(quartzJob)) {
			//将任务添加到任务管理器中
			
		}
	}

}
