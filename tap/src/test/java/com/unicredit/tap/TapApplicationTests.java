package com.unicredit.tap;

import com.unicredit.tap.configuration.BatchConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(locations = "classpath:batch-config.xml") // Adjust path if needed.
@SpringBatchTest
@Import(BatchConfig.class) // Import BatchConfig
class TapApplicationTests {

	@Autowired
	private Job job;

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private JobRepository jobRepository;

	@Value("${nas.input.path}")
	private String inputFilePath;

	@Value("${nas.output.path}")
	private String outputFilePath;

	private JobLauncherTestUtils jobLauncherTestUtils;

	@BeforeEach
	public void setUp() {
		// Setup logic if needed (e.g., clear database, prepare files, etc.)
	}

	@Test
	void testJobWithInputPathParameterWrongPath() throws Exception {
		// Create job parameters
		Map<String, JobParameter<?>> jobParametersMap = new HashMap<>();
		jobParametersMap.put("nas.input.path", new JobParameter("classpath:input.csv", String.class)); // Use "nas.input.path"
		JobParameters jobParameters = new JobParameters(jobParametersMap);

		// Launch the job using JobLauncherTestUtils
		jobLauncherTestUtils = new JobLauncherTestUtils();
		jobLauncherTestUtils.setJob(job);
		jobLauncherTestUtils.setJobLauncher(jobLauncher);
		jobLauncherTestUtils.setJobRepository(jobRepository);

		JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

		// Check job status (e.g., it should be COMPLETED)
		assertEquals("COMPLETED", jobExecution.getStatus().toString());

		// Optionally, check job execution outcome (e.g., verify output file)
		// Example: assert that output file is generated
		// assertTrue(Files.exists(Paths.get(outputPath, "output.xml")));
	}

}
