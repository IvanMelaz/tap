package com.unicredit.tap;

import com.unicredit.tap.configuration.BatchConfig;
import com.unicredit.tap.model.IF03Mapping;
import com.unicredit.tap.model.XmlMapping;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

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

	@Autowired
	private FlatFileItemReader<IF03Mapping> itemReader;

	@Autowired
	private StaxEventItemWriter<XmlMapping> xmlItemWriter;

	@Autowired
	private Jaxb2Marshaller jaxbMarshaller;

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
		// Launch the job using JobLauncherTestUtils
		jobLauncherTestUtils = new JobLauncherTestUtils();
		jobLauncherTestUtils.setJob(job);
		jobLauncherTestUtils.setJobLauncher(jobLauncher);
		jobLauncherTestUtils.setJobRepository(jobRepository);

		JobExecution jobExecution = jobLauncherTestUtils.launchJob();

		// Check job status (e.g., it should be COMPLETED)
		assertEquals("COMPLETED", jobExecution.getStatus().toString());

		// Optionally, check job execution outcome (e.g., verify output file)
		// Example: assert that output file is generated
		// assertTrue(Files.exists(Paths.get(outputPath, "output.xml")));
	}

}
