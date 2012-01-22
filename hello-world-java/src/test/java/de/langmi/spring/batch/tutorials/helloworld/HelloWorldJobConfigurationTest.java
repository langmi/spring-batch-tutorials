/**
 * Copyright 2011 Michael R. Lange <michael.r.lange@langmi.de>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.langmi.spring.batch.tutorials.helloworld;

import java.io.PrintStream;
import org.junit.After;
import java.io.ByteArrayOutputStream;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * JobConfigurationTest, it loads the Spring Batch Job definition and runs
 * the configured job.
 *
 * @author Michael R. Lange <michael.r.lange@langmi.de> 
 */
@ContextConfiguration(locations = {"classpath*:spring/batch/job/hello-world-job.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class HelloWorldJobConfigurationTest {

    @Autowired
    private Job job;
    @Autowired
    private JobLauncher jobLauncher;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    /** Launch Test. */
    @Test
    public void launchJob() throws Exception {
        // launch the job
        JobExecution jobExecution = jobLauncher.run(job, new JobParameters());

        // assert job run status
        assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());

        // the \n is important, because .println is used
        assertEquals("Hello World!\n", outContent.toString());
    }

    @Before
    public void setUpStreams() {
        // catch system out
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void cleanUpStreams() {
        // reset JVM standard
        System.setOut(null);
    }
}
