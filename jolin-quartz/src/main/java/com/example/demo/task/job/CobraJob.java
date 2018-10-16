package com.example.demo.task.job;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CobraJob implements Job {

	/**日志*/
	private Logger log = LoggerFactory.getLogger(CobraJob.class);
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		log.info("定时job: "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
	}

}
