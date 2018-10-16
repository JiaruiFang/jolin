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
	 * @param: @param quartzJob
	 * @param: @return
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
	 * @Description: TODO
	 * @param: @param quartzJob
	 * @param: @return
	 * @return: String
	 * @throws  
	 */  
	@PostMapping("/update")
	public String modifyQuartzJob(@RequestBody QuartzJob quartzJob) {
		quartzJobServiceImpl.modifyQuartzJob(quartzJob);
		return "ok";
	}
}
