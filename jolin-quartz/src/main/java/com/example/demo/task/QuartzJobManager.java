package com.example.demo.task;

import java.util.Date;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
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
		// 由于spring集成了Quartz,所以Scheduler由schedulerFactoryBean管理
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		// 使用JobBuilder定义job--任务名，任务组，执行类
		Class<? extends Job> jobClass = (Class<? extends Job>) Class.forName(quartzJob.getJobClass());
		// 深度克隆一个job 携带相信的信息
		JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(quartzJob.getJobName(), quartzJob.getJobGroup())
				.build();
		// 使用TriggerBuilder可以构建触发器
		CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(quartzJob.getJobName(), quartzJob.getJobGroup())
				.withSchedule(CronScheduleBuilder.cronSchedule(quartzJob.getCronExpression())).build();
		// 定时任务由scheduler管理，执行结果为第一次触发时间
		return scheduler.scheduleJob(jobDetail, trigger);
	}

	/**
	 *
	 * @Method: modifyJob
	 * @Description: TODO 修改某个定时任务的时间
	 * @param: @param
	 *             quartzJob @return: void @throws
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public Date modifyJob(QuartzJob quartzJob) throws Exception {
		// 获取调度器
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		// 获取当前正在执行的调度器
		TriggerKey triggerKey = TriggerKey.triggerKey(quartzJob.getJobName(), quartzJob.getJobGroup());
		// 获取带有时间表达式的正在执行的调度器
		CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
		// 如果为null，则证明当前无任务在执行
		if (cronTrigger == null) {
			return null;
		}
		// 通过正在执行的调度器获取任务调度时间和要进行修改的任务时间进行比较
		String oldCronExpression = cronTrigger.getCronExpression();
		// 如果相同，则证明时间相同，不需要修改
		if (oldCronExpression == quartzJob.getCronExpression()) {
			return null;
		} else {
			// 新建一个带有时间表达式的触发器
			cronTrigger = TriggerBuilder.newTrigger().withIdentity(quartzJob.getJobName(), quartzJob.getJobGroup())
					.withSchedule(CronScheduleBuilder.cronSchedule(quartzJob.getCronExpression())).build();
			// 任务管理器重新执行 任务及触发器
			return scheduler.rescheduleJob(triggerKey, cronTrigger);
		}
	}
	
	/**
	 * @throws Exception 
	 * @Method: pauseAllJob  
	 * @Description: 暂停所有任务
	 * @param: 
	 * @return: void
	 * @throws
	 */
	public void pauseAllJob() throws Exception {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		scheduler.pauseAll();
	}
	
	/**
	 *
	 * @Method: pauseQuartzJob  
	 * @Description: 暂停一个任务
	 * @return: void
	 * @throws
	 */
	public void pauseQuartzJob(QuartzJob quartzJob) throws Exception {
		JobKey jobKey = JobKey.jobKey(quartzJob.getJobName(), quartzJob.getJobGroup());
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		scheduler.pauseJob(jobKey);
	}
	
	/**
	 * @throws Exception 
	 * @Method: resumeAllQuartzJob  
	 * @Description: 恢复所有任务
	 * @param: 
	 * @return: void
	 * @throws
	 */
	public void resumeAllQuartzJob() throws Exception {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		scheduler.resumeAll();
	}
	
	/**
	 * @throws Exception 
	 * @Method: resumeQuartzJob  
	 * @Description: 恢复某一个任务
	 * @return: void
	 * @throws
	 */
	public void resumeQuartzJob(QuartzJob quartzJob) throws Exception {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(quartzJob.getJobName(), quartzJob.getJobGroup());
		//恢复任务
		scheduler.resumeJob(jobKey);
	}
	
	/**
	 * @throws Exception 
	 * @Method: deleteJob  
	 * @Description: 删除某一个任务
	 * @param: @param quartzJob
	 * @return: void
	 * @throws
	 */
	public void deleteJob(QuartzJob quartzJob) throws Exception {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		// 停止触发器
		TriggerKey triggerKey = TriggerKey.triggerKey(quartzJob.getJobName(), quartzJob.getJobGroup());
		scheduler.pauseTrigger(triggerKey);
		// 删除触发器
		scheduler.unscheduleJob(triggerKey);
		// 删除任务
		JobKey jobKey = new JobKey(quartzJob.getJobName(), quartzJob.getJobGroup());
		scheduler.deleteJob(jobKey);
	}
}
