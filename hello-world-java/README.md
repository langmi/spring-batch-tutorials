# "Hello World!" with Spring Batch 2.1

## Summary

In this tutorial, we will create a simple [Hello World!][hello-world] program with [Spring Batch][spring-batch] Version 2.1 and learn some basics for this amazing Java  [batch processing][wikipedia-batch-processing] framework.

## For the Impatient

If you are in a hurry or just the code-first type, you can jump right to the source on [my github repository][github-repo].

## Introduction

### What is Spring Batch?

From [Spring Batch][spring-batch]:

> Spring Batch is a lightweight, comprehensive batch framework designed to enable the development of robust batch applications vital for the daily operations of enterprise systems.

Doesn't that sound like the usual sales pitch? Ok lets try it with (my) own words:

> Spring Batch is a framework, which does the grunt work for your Java batch needs and provides lots of ready-to-use implementations to keep your own code minimal.


### Basic Spring Batch Concepts

The official reference covers the [Spring Batch concepts][spring-batch-domain-concepts] in every detail, but for this tutorial we can start with a more simplified view:

A Spring Batch program usually consists of a Job, which encapsulates a flow of steps. Here visualized with a sequential example:

![Spring Batch Job][spring-batch-concept-job]

A standard Step reads data, processes it and writes data.

![Spring Batch Step][spring-batch-concept-step]

The data is handled with a so called [Chunk Oriented Processing][chunk-oriented-processing] approach, where the data is read individually, processed individually, but written in chunks.

![Spring Batch Chunk][spring-batch-concept-chunk]

The chunks play a role for the transactional behavior too, Spring Batch commits a transaction after each chunk. Thats enough for the basic concepts, time to get ready for some coding.

## Get Ready For Coding!

### IDE Setup

To keep it simple i provided some build manager configurations for:

* [Buildr][buildr] - see `buildfile`, tested with Buildr 1.4.6
* [Gradle][gradle] - see `build.gradle`, tested with Gradle 1.0-milestone-5
* [Maven][maven] - see `pom.xml`, needs Maven 3+

Each configuration file contains informations on used versions and general setup.

If you work without a build manager you can download all needed libraries from [Spring Batch downloads][spring-batch-downloads], but remember this tutorial is tested with Spring Batch Version 2.1 only.

### Use Case

This tutorial implements a simple use case:

* read lines from a file
* process each line and enrich the data
* write result in a output file

### Spring Batch Program

A Spring Batch program roughly consists of 3 parts:

* Spring Batch infrastructure
* Spring Batch Job definition
* some custom code for the job

## Run a Spring Batch Job

### Run Batch Job, Run!

We have some possibilities to run the job:

* executing the _-executable.jar_ from the command line:

`java -jar hello-world-java-1.0-SNAPSHOT-executable.jar spring/batch/job/hello-world-job.xml helloWorldJob`

* using the Maven exec plugin with 

`mvn clean install exec:java -Dexec.mainClass=org.springframework.batch.core.launch.support.CommandLineJobRunner -Dexec.args="spring/batch/job/hello-world-job.xml helloWorldJob"`

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

[accenture]: http://www.accenture.com/ "Accenture official home page"
[ant]: http://ant.apache.org/ "Ant official home page"
[apache-license]: http://www.apache.org/licenses/LICENSE-2.0.txt "Apache 2.0 License"
[buildr]: http://buildr.apache.org/ "Buildr official home page"
[chunk-oriented-processing-news]: http://static.springsource.org/spring-batch/reference/html/whatsNew.html#whatsNewChunkOrientedProcessing
[chunk-oriented-processing]: http://static.springsource.org/spring-batch/reference/html/configureStep.html#chunkOrientedProcessing
[gradle]: http://www.gradle.org/ "Gradle official home page"
[changes-1-to-2]: http://static.springsource.org/spring-batch/trunk/migration/2.0-highlights.html "Changes from Spring Batch 1.x to 2.0"
[eclipse-help]: http://www.eclipse.org/documentation/ "Eclipse: Starting Point for Documentation"
[github-repo]: https://github.com/langmi/spring-batch-tutorials "My Github Repository for Spring Batch Tutorials Sources"
[hello-world]: http://en.wikipedia.org/wiki/Hello_world_program "Wikipedia: Hello World Programm"
[intellij-idea-project-setup]: http://www.jetbrains.com/idea/webhelp/creating-new-project-from-scratch.html "Intelli J IDEA: Creating New Project From Scratch"
[first-introduction]: http://forum.springsource.org/showthread.php?38417-Spring-Batch-Announcement "first Spring Batch announcement from 2007"
[maven]: http://maven.apache.org/index.html "Maven official home page"
[netbeans]: http://netbeans.org/ "Netbeans official home page"
[netbeans-project-setup]: http://netbeans.org/kb/docs/java/project-setup.html "Netbeans: Creating, Importing, and Configuring Java Projects"
[project-layout]: http://maven.apache.org/guides/introduction/introduction-to-the-standard-directory-layout.html "Standard directory layout for Maven projects"
[springsource]: http://www.springsource.com/ "Springsource official home page"
[spring-batch]: http://static.springsource.org/spring-batch/  "Spring Batch official home page"
[spring-batch-concept-chunk]: https://github.com/langmi/spring-batch-tutorials/raw/master/hello-world-java/spring-batch-concept-chunk.png
[spring-batch-concept-job]: https://github.com/langmi/spring-batch-tutorials/raw/master/hello-world-java/spring-batch-concept-job.png
[spring-batch-concept-step]: https://github.com/langmi/spring-batch-tutorials/raw/master/hello-world-java/spring-batch-concept-step.png
[spring-core]: http://www.springsource.org/spring-core/ "Spring Core Framework official home page"
[spring-batch-domain-concepts]: http://static.springsource.org/spring-batch/reference/html/domain.html
[spring-batch-downloads]: http://static.springsource.org/spring-batch/downloads.html
[wikipedia-batch-processing]: http://en.wikipedia.org/wiki/Batch_processing
