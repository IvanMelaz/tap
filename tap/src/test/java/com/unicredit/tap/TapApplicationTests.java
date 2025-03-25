package com.unicredit.tap;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@ContextConfiguration(locations = "classpath:batch-config.xml") // Adjust path if needed.
class TapApplicationTests {

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private Job job; // Assuming your job bean is named 'job' in batch-config.xml

/*	@Test
	void testJobWithInputPathParameterWrongPath() {
		JobParameters jobParameters = new JobParametersBuilder()
				.addString("nas.input.path", "nonexistent/input/file.csv")
				.toJobParameters();

		assertDoesNotThrow(() -> jobLauncher.run(job, jobParameters));
	}*/

}
