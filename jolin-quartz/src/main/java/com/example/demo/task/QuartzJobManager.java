package com.example.demo.task;

import java.sql.Date;

import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import com.example.demo.domain.QuartzJob;

/**
 * 
 * @ClassName: QuartzJobManager  
 * @Description: QuartzJob 管理器 
 * @author: Mr.fang  
 * @date: 2018年10月15日  
 * @version: v1.0
 */
@Service
public class QuartzJobManager {

	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;
	
	@SuppressWarnings("unchecked")
	public Date addJob(QuartzJob quartzJob) throws Exception {
		//由于spring集成了Quartz,所以Scheduler由schedulerFactoryBean管理
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		//使用JobBuilder定义job--任务名，任务组，执行类
		Class<? extends Job> jobClass = (Class<? extends Job>) Class.forName(quartzJob.getJobClass());
		//深度克隆一个job 携带相信的信息
		JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(quartzJob.getJobName(), quartzJob.getJobGroup()).build();
		//使用TriggerBuilder可以构建触发器
		Trigger trigger = TriggerBuilder.newTrigger().withIdentity(quartzJob.getJobName(), quartzJob.getJobGroup())
				.withSchedule(CronScheduleBuilder.cronSchedule(quartzJob.getCronExpression())).build();
		// 定时任务由定时任务调度器Scheduler进行管理的，执行结果为第一次触发时间
		return (Date) scheduler.scheduleJob(jobDetail, trigger);
	}
}
