# "Hello World!" with Spring Batch 2.1

![Logo][spring-batch-tutorial-hello-world-logo]

## Summary

In this tutorial, we will create a really simple [Hello World!][hello-world] program with [Spring Batch][spring-batch] Version 2.1 and learn some basics for this amazing Java  [batch processing][wikipedia-batch-processing] framework.

You will need at least a basic understanding of the [Spring (Core) framework][spring-core] to get along with the tutorial.

## For the Impatient

If you are in a hurry or just the code-first type, you can jump right to the source here on [my github repository][github-repo], dive into the hello-world-java directory.

## Introduction

### What is Spring Batch?

From the [official Spring Batch website][spring-batch]:

> Spring Batch is a lightweight, comprehensive batch framework designed to enable the development of robust batch applications vital for the daily operations of enterprise systems.

Doesn't that sound like the usual sales pitch? Ok lets try it with (my) own words:

> Spring Batch is a framework, which does the grunt work for your Java batch needs and provides lots of ready-to-use implementations to keep your own code minimal.


### Basic Spring Batch Concepts

The official reference covers the [Spring Batch concepts][spring-batch-domain-concepts] in every detail, but for this tutorial we can start with a more simplified view:

A Spring Batch program usually consists of a Job, which encapsulates a flow of steps, where a step can be seen as abstract isolated unit of work. Here that is visualized with a sequential flow example:

![Spring Batch Job][spring-batch-concept-job]

A standard Step reads data, processes it and writes data, until the data from the reader is exhausted.

![Spring Batch Step][spring-batch-concept-step]

The data is handled with a so called [Chunk Oriented Processing][chunk-oriented-processing] approach, where the data is read individually, processed individually, but written in chunks.

![Spring Batch Chunk][spring-batch-concept-chunk]

The chunks play a role for the transactional behavior too, Spring Batch commits a transaction after each chunk. Thats enough for the basic concepts, time to get ready for some coding.

## Get Ready For Coding!

The Spring Batch framework is really lightweight, but that comes at the prize of a little more complex XML configuration. It is a **Spring** framework after all.

### Spring Batch Configuration

The configuration needed to get a Spring Batch program running roughly consists of two parts:

* Spring Batch infrastructure
* Spring Batch Job definition


#### Spring Batch Infrastructure

Under the hood Spring Batch works with some tables to keep the state of the jobs and to provide restartability. 
The tables are accessed with a DAO called *Job Repository* and to start a Job the framework provides a *Job Launcher*.
Because a job works with transactions, a *transaction manager* is mandatory.

To keep this tutorial simple we use a table-less Job Repository and a dummy transaction manager, translated to the Spring and Spring Batch XML configuration it looks like this:

    <bean id="jobLauncher" 
          class="org.springframework.batch.core.launch.support.SimpleJobLauncher"
          p:jobRepository-ref="jobRepository" />
	
    <bean id="jobRepository" 
          class="org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean"
          p:transactionManager-ref="transactionManager" />
    
    <bean id="transactionManager" 
          class="org.springframework.batch.support.transaction.ResourcelessTransactionManager" />

This is at the same time the most minimal Spring Batch infrastructure configuration possible and does not need any self-made code, all implementations are provided by the framework.

#### Spring Batch Job Definition

The *Job* definition - for this tutorial - is quite short:

    <job id="helloWorldJob">
        <step id="helloWorldStep" >
            <tasklet ref="helloWorldTasklet" />
        </step>
    </job>
    <bean id="helloWorldTasklet" 
          class="de.langmi.spring.batch.tutorials.helloworld.HelloWorldTasklet" />

In contrast to the introduction of the basic concepts, we use a so called [Tasklet Step][tasklet-step], without the standard Reader, Processor and Writer.. A *Tasklet Step* bypasses the standard Step concept and makes it possible to just implement one method, like printing out *Hello World!*. 

#### Complete XML

All XML taken from the configurations above and joined in one file:

    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
           xmlns:p="http://www.springframework.org/schema/p" 
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:schemaLocation="
           http://www.springframework.org/schema/batch 
           http://www.springframework.org/schema/batch/spring-batch-2.1.xsd
           http://www.springframework.org/schema/beans 
           http://www.springframework.org/schema/beans/spring-beans-3.0.xsd">
    
        <description>
            Simple Spring Batch Configuration
            
            - one tasklet step
              - prints out "Hello World!"
            - setup without database, uses in-memory JobRepository
            - not restartable
        </description>
        
        <!-- 
            inline xmlns, otherwise it would look like 
            'batch:job, batch:step, etc.' 
        -->
        <job id="helloWorldJob" xmlns="http://www.springframework.org/schema/batch">
            <step id="helloWorldStep" >
                <tasklet ref="helloWorldTasklet" />
            </step>
        </job>
    
        <bean id="helloWorldTasklet" 
              class="de.langmi.spring.batch.tutorials.helloworld.HelloWorldTasklet" />
        
        <bean id="jobLauncher" 
              class="org.springframework.batch.core.launch.support.SimpleJobLauncher"
              p:jobRepository-ref="jobRepository" />
        
        <bean id="jobRepository" 
              class="org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean"
              p:transactionManager-ref="transactionManager" />
        
        <bean id="transactionManager" 
              class="org.springframework.batch.support.transaction.ResourcelessTransactionManager" />
    </beans>


### Java Code

In the Job configuration we configured a Tasklet Step, here is the missing source-code:

    package de.langmi.spring.batch.tutorials.helloworld;
    
    import org.springframework.batch.core.StepContribution;
    import org.springframework.batch.core.scope.context.ChunkContext;
    import org.springframework.batch.core.step.tasklet.Tasklet;
    import org.springframework.batch.repeat.RepeatStatus;

    public class HelloWorldTasklet implements Tasklet {
    
        /** {@inheritDoc} */
        @Override
        public RepeatStatus execute(StepContribution contribution, 
                                    ChunkContext chunkContext) throws Exception {
    
            System.out.println("Hello World!");
    
            return RepeatStatus.FINISHED;
        }
    }

And that's it, yes really that and the configuration is a complete Spring Batch.

## Testing

### Testing The Tasklet Step

The Tasklet implementation is pure Java, so we can test it as that with [JUnit][junit]:

    public class HelloWorldTaskletTest {
    
        private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        private final Tasklet tasklet = new HelloWorldTasklet();
    
        @Test
        public void testExecute() throws Exception {
            tasklet.execute(null, null);
            // the \n is important, because .println is used inside the tasklet implementation
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

### Testing Complete Job

To test the complete Job we can use a lot of Spring JUnit test utilities. The only difference to a standard Spring test is the use of the Job Launcher, which is needed to launch a job.

    @ContextConfiguration(locations = {"classpath*:spring/batch/job/hello-world-job.xml"})
    @RunWith(SpringJUnit4ClassRunner.class)
    public class HelloWorldJobConfigurationTest {
    
        @Autowired
        private Job job;
        @Autowired
        private JobLauncher jobLauncher;
        private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    
        /** Simple Launch Test. */
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

## Run The Spring Batch Job

### Run Batch Job, Run!

#### Mainclass (from within IDE)

using the Maven exec plugin with 

`mvn clean install exec:java -Dexec.mainClass=org.springframework.batch.core.launch.support.CommandLineJobRunner -Dexec.args="spring/batch/job/hello-world-job.xml helloWorldJob"`

#### All-One-Jar

executing the _-executable.jar_ from the command line:

`java -jar hello-world-java-1.0-SNAPSHOT-executable.jar spring/batch/job/hello-world-job.xml helloWorldJob`

#### Complete Runtime

#### Web Archive




## Did you know?

Spring Batch was first [introduced][first-introduction] in 2007. Back then the framework was created by Interface21 - now known as [Springsource][springsource] - and [Accenture][accenture]. It has seen only 2 major versions so far, but the [transition from version 1 to 2][changes-1-to-2] included a massive refactoring of the core concepts. In fact the chunk oriented processing concept was first introduced with Spring Batch version 2.

## Meta

* tested with:
	* Java 1.6
	* Maven 3
	* [Spring Batch][spring-batch] 2.1.8.RELEASE
	* [Spring Framework(core)][spring-core] 3.1.0.RELEASE
* used IDE: primarily programmed with [Netbeans][netbeans] 7.0
* license: [Apache 2.0 License][apache-license]


### Maven, Buildr, Gradle and Friends

To keep it simple i provided some build manager configurations for:

* [Buildr][buildr] - see [buildfile][buildfile], tested with Buildr 1.4.6
* [Gradle][gradle] - see [build.gradle][build-gradle], tested with Gradle 1.0-milestone-5
* [Maven][maven] - see [pom.xml][pom-xml], needs Maven 3+

Each configuration file contains informations on used versions and general setup.

If you work without a build manager you can download all needed libraries from [Spring Batch downloads][spring-batch-downloads], but remember this tutorial is tested with Spring Batch Version 2.1 only.


[accenture]: http://www.accenture.com/ "Accenture official home page"
[apache-license]: http://www.apache.org/licenses/LICENSE-2.0.txt "Apache 2.0 License"
[buildr]: http://buildr.apache.org/ "Buildr official home page"
[buildfile]: https://github.com/langmi/spring-batch-tutorials/blob/master/hello-world-java/buildfile
[build-gradle]: https://github.com/langmi/spring-batch-tutorials/blob/master/hello-world-java/build.gradle
[changes-1-to-2]: http://static.springsource.org/spring-batch/trunk/migration/2.0-highlights.html "Changes from Spring Batch 1.x to 2.0"
[chunk-oriented-processing-news]: http://static.springsource.org/spring-batch/reference/html/whatsNew.html#whatsNewChunkOrientedProcessing
[chunk-oriented-processing]: http://static.springsource.org/spring-batch/reference/html/configureStep.html#chunkOrientedProcessing
[first-introduction]: http://forum.springsource.org/showthread.php?38417-Spring-Batch-Announcement "first Spring Batch announcement from 2007"
[gradle]: http://www.gradle.org/ "Gradle official home page"
[github-repo]: https://github.com/langmi/spring-batch-tutorials "My Github Repository for Spring Batch Tutorials Sources"
[junit]: http://www.junit.org/
[hello-world]: http://en.wikipedia.org/wiki/Hello_world_program "Wikipedia: Hello World Programm"
[maven]: http://maven.apache.org/index.html "Maven official home page"
[pom-xml]: http://foo.com
[springsource]: http://www.springsource.com/ "Springsource official home page"
[spring-batch]: http://static.springsource.org/spring-batch/  "Spring Batch official home page"
[spring-batch-concept-chunk]: https://github.com/langmi/spring-batch-tutorials/raw/master/hello-world-java/spring-batch-concept-chunk.png
[spring-batch-concept-job]: https://github.com/langmi/spring-batch-tutorials/raw/master/hello-world-java/spring-batch-concept-job.png
[spring-batch-concept-step]: https://github.com/langmi/spring-batch-tutorials/raw/master/hello-world-java/spring-batch-concept-step.png
[spring-core]: http://www.springsource.org/spring-core/ "Spring Core Framework official home page"
[spring-batch-domain-concepts]: http://static.springsource.org/spring-batch/reference/html/domain.html
[spring-batch-downloads]: http://static.springsource.org/spring-batch/downloads.html
[spring-batch-tutorial-hello-world-logo]: https://github.com/langmi/spring-batch-tutorials/raw/master/hello-world-java/spring-batch-tutorial-hello-world-logo.png
[tasklet-step]: http://static.springsource.org/spring-batch/reference/html/configureStep.html#taskletStep
[wikipedia-batch-processing]: http://en.wikipedia.org/wiki/Batch_processing
