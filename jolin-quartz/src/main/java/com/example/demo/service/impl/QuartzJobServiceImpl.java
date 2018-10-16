package com.example.demo.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
		if (!StringUtils.isEmpty(quartzJob)) {
			try {
				// 将任务添加到任务管理器中
				Date date = quartzJobManager.addJob(quartzJob);
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String dateFirst = format.format(date);
				quartzJob.setStartTime(dateFirst);
				// 更新这个任务的时间，这里是应用了JPA操作，如果存在这个记录的话，save方法就变为修改操作
				quartzJobRepository.save(quartzJob);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void modifyQuartzJob(QuartzJob job) {
		//根据要修改任务的id 到数据库中根据id查询  如果有数据则返回对象，如果没有则返回null
		QuartzJob quartzJob = quartzJobRepository.findById(job.getJobId()).orElse(null);
		System.out.println(quartzJob.getJobId()+"  =  "+job.getJobId());
		if(!StringUtils.isEmpty(quartzJob)) {
			quartzJobRepository.save(job);
			//将此任务添加到 任务管理类中
			try {
				quartzJobManager.addJob(job);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
