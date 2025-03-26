package com.unicredit.tap;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:batch-config.xml") // Import your XML configuration
public class TapApplication implements CommandLineRunner {

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private Job if03CvsJob; // Assuming your job is named 'if03CvsJob' in batch-config.xml

	public static void main(String[] args) {
		SpringApplication.run(TapApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		JobParameters jobParameters = new JobParametersBuilder()
				.addLong("time", System.currentTimeMillis()) // Add unique parameter to ensure new job instance
				.toJobParameters();

		jobLauncher.run(if03CvsJob, jobParameters);
	}
}
