package com.unicredit.tap.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

public class ValidationStepListener implements StepExecutionListener {

    private static final Logger log = LoggerFactory.getLogger(ValidationStepListener.class);

    @Override
    public void beforeStep(StepExecution stepExecution) {
        // Nothing to do before the step
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        if (stepExecution.getExitStatus().equals(ExitStatus.COMPLETED)) {
            log.info("XML Validation Step Completed Successfully");
            return ExitStatus.COMPLETED;
        } else {
            log.error("XML Validation Step Failed");
            return ExitStatus.FAILED;
        }
    }
}
