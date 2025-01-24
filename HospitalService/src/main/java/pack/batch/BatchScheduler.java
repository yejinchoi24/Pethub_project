package pack.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@Component
//@EnableScheduling
//->스프링배치 한번만 실행한 후에주석처리하거나 BatchScheduler클래스 자체를 비활성화 해야함.
public class BatchScheduler {

    private static final Logger logger = LoggerFactory.getLogger(BatchScheduler.class);

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job importHospitalJob; // importHospitalJob이 빈으로 등록되어 있어야 합니다.

    @Scheduled(fixedRate = 60000)  // 600초마다 실행, 필요에 따라 조정 가능
    public void runJob() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis()) // 매번 다른 파라미터로 실행
                    .toJobParameters();
            
            logger.info("Starting the importHospitalJob with parameters: {}", jobParameters);
            jobLauncher.run(importHospitalJob, jobParameters);
            logger.info("importHospitalJob completed successfully.");
            
        } catch (Exception e) {
            logger.error("Error occurred while executing importHospitalJob: ", e);
        }
    }
}