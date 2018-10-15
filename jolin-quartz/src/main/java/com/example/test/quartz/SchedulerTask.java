package com.example.test.quartz;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SchedulerTask {

	private int count=0;
	
	@Scheduled(cron="*/6 * * * * ?")
	public void process() {
		System.out.println("this is scheduler task runing  "+(count++));
	}
	
}