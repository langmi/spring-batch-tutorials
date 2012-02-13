/**
 * Copyright 2012 Michael R. Lange <michael.r.lange@langmi.de>.
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

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * Testing a Spring Batch without any Spring utilities.
 *
 * @author Michael R. Lange <michael.r.lange@langmi.de>
 */
public class HelloWorldJobConfigurationManualTest {

    /** Stream for catching System.out. */
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Test
    public void testLaunch() throws Exception {
        // load ApplicationContext
        ApplicationContext context = new FileSystemXmlApplicationContext("src/main/resources/spring/batch/job/hello-world-job.xml");
        // get job
        Job job = context.getBean(Job.class);
        // get job launcher
        JobLauncher jobLauncher = context.getBean(JobLauncher.class);
        // start job
        JobExecution jobExecution = jobLauncher.run(job, new JobParameters());
        // assertion
        assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
        // assert sysoutput        
        assertEquals("Hello World!", outContent.toString());
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
