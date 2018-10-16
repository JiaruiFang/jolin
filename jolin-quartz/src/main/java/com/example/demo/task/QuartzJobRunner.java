package com.example.demo.task;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import com.example.demo.domain.QuartzJob;
import com.example.demo.service.QuartzJobService;

@Component
public class QuartzJobRunner implements ApplicationRunner {

	/**日志*/
	private Logger log = LoggerFactory.getLogger(QuartzJobRunner.class);
	
	@Autowired
	private QuartzJobService quartzJobService;
	@Autowired
	private QuartzJobManager quartzJobManager;
	
	/* 
	 * 项目启动时，通过任务管理器执行的run方法  
	 * @param args
	 * @throws Exception  
	 * @see org.springframework.boot.ApplicationRunner#run(org.springframework.boot.ApplicationArguments)  
	 */
	public void run(ApplicationArguments args) throws Exception {
		log.info("**********初始化加载定时任务开始**********");
		//从数据库中查询任务状态是正在执行的状态
		List<QuartzJob> quartzJobList = quartzJobService.findByJobStatus(QuartzJob.STATUS_RUNNING);
		quartzJobList.forEach((quartzJob)->{
			try {
				quartzJobManager.addJob(quartzJob);
			} catch (Exception e) {
				e.printStackTrace();
				log.info("**********初始化加载定时任务异常**********");
			}
		});
		log.info("**********初始化加载定时任务结束**********");
	}

}
