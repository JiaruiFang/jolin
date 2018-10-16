package com.example.demo.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.domain.QuartzJob;

@Repository
public interface QuartzJobRepository extends JpaRepository<QuartzJob,Long> {

	//根据jobStatus<任务初始状态查询>
	List<QuartzJob> findByJobStatus(String jobStatus);
	
}
