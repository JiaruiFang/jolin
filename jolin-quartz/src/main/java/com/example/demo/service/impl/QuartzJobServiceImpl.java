package com.example.demo.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
		// 根据要修改任务的id 到数据库中根据id查询 如果有数据则返回对象，如果没有则返回null
		QuartzJob quartzJob = quartzJobRepository.findById(job.getJobId()).orElse(null);
		System.out.println(quartzJob.getJobId() + "  =  " + job.getJobId());
		if (!StringUtils.isEmpty(quartzJob)) {
			try {
				// 调用任务管理类的方法 修改任务
				quartzJobManager.modifyJob(job);
				quartzJobRepository.save(job);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public void pauseAllQuartzJob() {
		// 任务管理器暂停所有任务
		try {
			quartzJobManager.pauseAllJob();
			// 查询数据库中所有任务
			List<QuartzJob> list = quartzJobRepository.findAll();
			list.forEach((quartzJob) -> {
				// 修改数据库中任务状态
				quartzJob.setJobStatus(QuartzJob.STATUS_NOT_RUNNING);
				quartzJobRepository.save(quartzJob);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void pauseQuartzJob(QuartzJob job) {
		System.out.println("JobId " + job.getJobId());
		// 根据传入quartzJob的主键去数据库中查询
		QuartzJob quartzJob = quartzJobRepository.findById(job.getJobId()).orElse(null);
		if (!StringUtils.isEmpty(quartzJob)) {
			// 修改任务状态
			quartzJob.setJobStatus(QuartzJob.STATUS_NOT_RUNNING);
			quartzJobRepository.save(quartzJob);
			// 任务管理器暂停一个任务
			try {
				quartzJobManager.pauseQuartzJob(quartzJob);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void resumeAllQuartzJob() {
		// 调用任务管理器恢复所有任务
		try {
			quartzJobManager.resumeAllQuartzJob();
			// 修改恢复的任务状态
			List<QuartzJob> list = quartzJobRepository.findAll();
			list.forEach((quartzJob) -> {
				quartzJob.setJobStatus(QuartzJob.STATUS_RUNNING);
				quartzJobRepository.save(quartzJob);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void resumeQuartzJob(QuartzJob job) {
		// 根据任务id到数据库中搜索相关任务，修改任务状态
		QuartzJob quartzJob = quartzJobRepository.findById(job.getJobId()).orElse(null);
		if(!StringUtils.isEmpty(quartzJob)) {
			quartzJob.setJobStatus(QuartzJob.STATUS_RUNNING);
			quartzJobRepository.save(quartzJob);
		}
		try {
			// 调用任务管理器恢复某一个任务
			quartzJobManager.resumeQuartzJob(job);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteQuartzJob(QuartzJob job) {
		// 查询数据库，并删除记录
		QuartzJob quartzJob = quartzJobRepository.findById(job.getJobId()).orElse(null);
		if(!StringUtils.isEmpty(quartzJob)) {
			//quartzJobRepository.delete(quartzJob);
			// 任务管理器停止任务，并删除任务
			try {
				quartzJobManager.deleteJob(quartzJob);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
