package com.example.demo.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.domain.QuartzJob;
import com.example.demo.service.QuartzJobService;

/**
 * 
 * @ClassName: QuartzJobController  
 * @Description: quartz定时任务管理 controller层，  
 * @author: Mr.fang  
 * @date: 2018年10月15日  
 * @version: v1.0
 */
@RestController
@RequestMapping("/quartz")
public class QuartzJobController {

	@Autowired
	private QuartzJobService quartzJobServiceImpl;
	
	@GetMapping(value = "/find")
	public List<QuartzJob> findQuartzJob() {
		List<QuartzJob> list = quartzJobServiceImpl.findByJobStatus("1");
		return list;
	}
	
	/**
	 * 添加一个job
	 * @Method: createQuartzJob  
	 * @Description: TODO
	 * @return: String
	 * @throws  
	 */  
	@PostMapping("/create")
	public String createQuartzJob(@RequestBody QuartzJob quartzJob) {
		System.out.println("quartzJob description "+quartzJob.getDescription());
		System.out.println("quartzJob  "+quartzJob);
		quartzJobServiceImpl.createQuartzJob(quartzJob);
		return "ok";
	}
	
	/**
	 * 修改job时间
	 * @Method: updateQuartzJob  
	 * @return: String
	 * @throws  
	 */  
	@PostMapping("/update")
	public String modifyQuartzJob(@RequestBody QuartzJob quartzJob) {
		System.out.println("quartzJob id: "+quartzJob.getJobId());
		quartzJobServiceImpl.modifyQuartzJob(quartzJob);
		return "ok";
	}
	
	/**  
	 * @Method: pauseAllQuartzJob  
	 * @Description: 暂停所有定时任务
	 * @return: String
	 */
	@GetMapping("/pauseAll")
	public String pauseAllQuartzJob() {
		quartzJobServiceImpl.pauseAllQuartzJob();
		return "ok";
	}
	
	/**  
	 * @Method: pause  
	 * @Description: 暂停某一个任务
	 * @param: 
	 * @return: String
	 * @throws  
	 */
	@PostMapping("/pause")
	public String pauseQuartzJob(@RequestBody QuartzJob quartzJob) {
		System.out.println("quartzJob "+quartzJob);
		quartzJobServiceImpl.pauseQuartzJob(quartzJob);
		return "ok";
	}
	
	/**
	 * @Method: resumeAllQuartzJob  
	 * @Description: 恢复所有定时任务
	 * @throws
	 */
	@PostMapping("/resumeAll")
	public String resumeAllQuartzJob() {
		quartzJobServiceImpl.resumeAllQuartzJob();
		return "ok";
	}
	
	@PostMapping("/resume")
	public String resumeQuartzJob(@RequestBody QuartzJob quartzJob) {
		quartzJobServiceImpl.resumeQuartzJob(quartzJob);
		return "ok";
	}
	
	@PostMapping("/delete")
	public String deleteQuartzJob(@RequestBody QuartzJob quartzJob) {
		quartzJobServiceImpl.deleteQuartzJob(quartzJob);
		return "ok";
	}
	
}
