package com.example.demo.service;

import java.util.List;
import com.example.demo.domain.QuartzJob;

/**
 * @ClassName: QuartzJobService
 * @Description: 定时任务service层
 * @author: Mr.fang
 * @date: 2018年10月15日
 * @version: v1.0
 */
public interface QuartzJobService {

	// 根据jobStatus<任务初始状态查询>
	List<QuartzJob> findByJobStatus(String jobStatus);

	//创建一个定时任务，保存到数据库，并加入到任务管理器
	void createQuartzJob(QuartzJob Job);
	
	//修改job时间
	void modifyQuartzJob(QuartzJob job);
	
	//暂停所有定时任务
	void pauseAllQuartzJob();
	
	//暂停某一个任务
	void pauseQuartzJob(QuartzJob job);
	
	//回复所有定时任务
	void resumeAllQuartzJob();
	
	//恢复某一个定时任务
	void resumeQuartzJob(QuartzJob job);
	
	//删除一个定时任务
	void deleteQuartzJob(QuartzJob job);
}
