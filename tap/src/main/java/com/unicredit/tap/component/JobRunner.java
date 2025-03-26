package com.unicredit.tap.component;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobRunner {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    public void runJob() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("jobParam", "value1")  // example of job parameter
                .toJobParameters();

        jobLauncher.run(job, jobParameters);
    }
}
