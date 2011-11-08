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
package de.langmi.spring.batch.tutorials.helloworld

import groovy.util.GroovyTestCase
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;



class HelloWorldJobConfigurationTest extends GroovyTestCase {
    
    void testFoo(){
        // load ApplicationContext
        ApplicationContext context = new FileSystemXmlApplicationContext("src/main/resources/spring/batch/job/hello-world-job.xml");
        // get job
        Job job = context.getBean(Job.class);
        // get job launcher
        JobLauncher jobLauncher = context.getBean(JobLauncher.class);
        // start job
        JobExecution jobExecution = jobLauncher.run(job, new JobParameters());
        
        // assertion
        assert BatchStatus.COMPLETED == jobExecution.getStatus();
    }
}
