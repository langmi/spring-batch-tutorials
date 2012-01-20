# "Hello World!" with Spring Batch 2.1

## Summary

In this tutorial, we will create a simple [Hello World!][hello-world] program with [Spring Batch][spring-batch] and learn some basics for this amazing Java framework.

## For the Impatient

If you are in a hurry or just the code-first type, you can jump right to the source on [my github repository][github-repo].

## Introduction

### What is Spring Batch?

From [Spring Batch][spring-batch]:

> Spring Batch is a lightweight, comprehensive batch framework designed to enable the development of robust batch applications vital for the daily operations of enterprise systems.

To get the most of 

## Setup a Spring Batch Job

## Learn to speak: "Hello World!"

## How to setup a simple Spring Batch program?

The setup of a Spring Batch program roughly consists of 3 parts:

* project setup
* Spring Batch infrastructure
* Spring Batch Job

## Run a Spring Batch Job

### Run Batch Job, Run!

We have some possibilities to run the job:

* executing the _-executable.jar_ from the command line:

`java -jar hello-world-java-1.0-SNAPSHOT-executable.jar spring/batch/job/hello-world-job.xml helloWorldJob`

* using the Maven exec plugin with 

`mvn clean install exec:java -Dexec.mainClass=org.springframework.batch.core.launch.support.CommandLineJobRunner -Dexec.args="spring/batch/job/hello-world-job.xml helloWorldJob"`


### Project Setup

For the sake of simplicity i follow the suggested [standard directory layout][project-layout] for [Maven][maven] Projects.

To compile the project, run tests and create a packaged Java Archive, i primarly use Maven, but i provided working configurations for other build systems too. 

#### Buildr

[Buildr][buildr] is a rather new build system, which could be seen as a prettier Maven. Actually i think it's a bit exotic to use Ruby to create a build management software for Java projects. Anyway here is the configuration to get the project running: 

	=begin
        Buildr buildfile

        tested with:
            * Buildr 1.4.6
            * Ruby 1.8.7
            * Gem 1.8.8
            * Java 1.6.0_29

        see http://buildr.apache.org/index.html for more informations to buildr
    =end

    repositories.remote << 'http://repo1.maven.org/maven2/'

    COMPILE_ARTIFACTS = [
        'aopalliance:aopalliance:jar:1.0', 
        'com.thoughtworks.xstream:xstream:jar:1.3', 
        'commons-logging:commons-logging:jar:1.1.1', 
        'org.codehaus.jettison:jettison:jar:1.1', 
        'org.springframework:spring-aop:jar:3.0.6.RELEASE',
        'org.springframework:spring-asm:jar:3.0.6.RELEASE',
        'org.springframework:spring-beans:jar:3.0.6.RELEASE',
        'org.springframework:spring-context:jar:3.0.6.RELEASE',
        'org.springframework:spring-core:jar:3.0.6.RELEASE',
        'org.springframework:spring-expression:jar:3.0.6.RELEASE',
        'org.springframework:spring-jdbc:jar:3.0.6.RELEASE',
        'org.springframework:spring-tx:jar:3.0.6.RELEASE',
        'org.springframework.batch:spring-batch-core:jar:2.1.8.RELEASE',
        'org.springframework.batch:spring-batch-infrastructure:jar:2.1.8.RELEASE'
    ]
    TEST_ARTIFACTS = [
        'commons-collections:commons-collections:jar:3.2', 
        'commons-dbcp:commons-dbcp:jar:1.2.2', 
        'commons-io:commons-io:jar:1.4', 
        'commons-pool:commons-pool:jar:1.3', 
        'org.hamcrest:hamcrest-core:jar:1.1', 
        # junit is optional, buildr 1.4.6 uses junit 4.8.2 as default
        'junit:junit:jar:4.10',
        'org.springframework.batch:spring-batch-test:jar:2.1.8.RELEASE', 
        'org.springframework:spring-test:jar:3.0.6.RELEASE'
    ]

    desc 'hello-world-java'
    define 'hello-world-java' do
      project.group = 'de.langmi.spring.batch.tutorials'
      project.version = '1.0-SNAPSHOT'
      compile.options.target = '1.5'
      compile.with COMPILE_ARTIFACTS
      test.with TEST_ARTIFACTS
      package :jar, :id => 'hello-world-java'
    end


#### Gradle

[Gradle][gradle] is another build system. It is more like the old pal [Ant][ant] and as such a bit more verbose than Buildr. Again here a configuration to get some simple build tasks running:

    /*  Gradle buildfile
        tested with:
            * Gradle 1.0-milestone-5
            * Groovy 1.7.10
            * Java 1.6.0_29
    
        see http://www.gradle.org/ for more informations to Gradle */
        
    apply plugin: 'java'
    apply plugin: 'maven'
    
    repositories {
        mavenLocal()
        mavenCentral()
    }
    
    project.group = "de.langmi.spring.batch.tutorials"
    project.version = "1.0-SNAPSHOT"
    
    sourceCompatibility = '1.6'
    targetCompatibility = '1.6'
    
    dependencies {
        compile "aopalliance:aopalliance:1.0"
        compile "com.thoughtworks.xstream:xstream:1.3"
        compile "commons-logging:commons-logging:1.1.1" 
        compile "org.codehaus.jettison:jettison:1.1"
        compile "org.springframework:spring-aop:3.0.6.RELEASE"
        compile "org.springframework:spring-asm:3.0.6.RELEASE"
        compile "org.springframework:spring-beans:3.0.6.RELEASE"
        compile "org.springframework:spring-context:3.0.6.RELEASE"
        compile "org.springframework:spring-core:3.0.6.RELEASE"
        compile "org.springframework:spring-expression:3.0.6.RELEASE"
        compile "org.springframework:spring-jdbc:3.0.6.RELEASE"
        compile "org.springframework:spring-tx:3.0.6.RELEASE"
        compile "org.springframework.batch:spring-batch-core:2.1.8.RELEASE"
        compile "org.springframework.batch:spring-batch-infrastructure:2.1.8.RELEASE"
    
        testCompile "commons-collections:commons-collections:3.2"
        testCompile "commons-dbcp:commons-dbcp:1.2.2"
        testCompile "commons-io:commons-io:1.4"
        testCompile "commons-pool:commons-pool:1.3"
        testCompile "junit:junit:4.10"
        testCompile "org.hamcrest:hamcrest-core:1.1"
        testCompile "org.springframework.batch:spring-batch-test:2.1.8.RELEASE"
        testCompile "org.springframework:spring-test:3.0.6.RELEASE"
    }

#### IDE specific Project Setups

To create a project with your favourite IDE, please follow the links to the appropiate documentation.

* [Eclipse][eclipse-help]
* [Intelli J IDEA project setup][intellij-idea-project-setup]
* [Netbeans project setup][netbeans-project-setup]

#### Maven

#### Which Java Libraries are needed?

Each entry in this library list follows the pattern `<library>:<version>`. List is produced with Maven Plugin and command `dependency:list`.

* Spring Version 3.0.6.RELEASE
	* spring-aop
	* spring-asm
	* spring-beans
	* spring-context
	* spring-core
	* spring-expression
	* spring-jdbc
	* spring-tx
* Spring Batch Version 2.1.8.RELEASE
	* spring-batch-core
	* spring-batch-infrastructure
* other
	* aopalliance:1.0
	* xstream:1.3
	* commons-logging:1.1.1
	* jettison:1.1
	* xpp3_min:1.1.4c
* for test
	* commons-collections:3.2
	* commons-dbcp:1.2.2
	* commons-io:jar:1.4
	* commons-pool:1.3
	* junit:4.10
	* hamcrest-core:1.1
	* spring-test:3.0.6.RELEASE
	* spring-batch-test:2.1.8.RELEASE

For Maven users i provided the complete dependency configuration:

	<dependencies>
	    <!-- Spring (core) Framework dependencies -->
	    <dependency>
	        <groupId>org.springframework</groupId>
	        <artifactId>spring-beans</artifactId>
	        <version>${spring.framework.version}</version>
	    </dependency>
	    <dependency>
	        <groupId>org.springframework</groupId>
	        <artifactId>spring-context</artifactId>
	        <version>${spring.framework.version}</version>
	    </dependency>
	    <!-- spring-jdbc specified, because spring-batch has
	         older 2.5.6 version configured, we want 3.0.6 here -->
	    <dependency>
	        <groupId>org.springframework</groupId>
	        <artifactId>spring-jdbc</artifactId>
	        <version>${spring.framework.version}</version>
	    </dependency>
	    <!-- Spring Batch dependencies -->
	    <dependency>
	        <groupId>org.springframework.batch</groupId>
	        <artifactId>spring-batch-core</artifactId>
	        <version>${spring.batch.version}</version>
	    </dependency>
	    <dependency>
	        <groupId>org.springframework.batch</groupId>
	        <artifactId>spring-batch-infrastructure</artifactId>
	        <version>${spring.batch.version}</version>
	    </dependency>
	    <!-- test scoped dependencies -->
	    <dependency>
	        <groupId>org.springframework.batch</groupId>
	        <artifactId>spring-batch-test</artifactId>
	        <version>${spring.batch.version}</version>
	        <scope>test</scope>
	    </dependency>
	    <dependency>
	        <groupId>org.springframework</groupId>
	        <artifactId>spring-test</artifactId>
	        <version>${spring.framework.version}</version>
	        <scope>test</scope>            
	    </dependency>
	    <dependency>
	        <groupId>junit</groupId>
	        <artifactId>junit</artifactId>
	        <version>4.10</version>
	        <scope>test</scope>
	    </dependency>
	</dependencies>


## Did you know?

Spring Batch was first [introduced][first-introduction] in 2007. Back then the framework was created by Interface21 - now known as [Springsource][springsource] - and [Accenture][accenture]. It has seen only 2 major versions so far, but the [transition from version 1 to 2][changes-1-to-2] included a massive refactoring of the core concepts. In fact the Tasklet used in this tutorial was first released with version 2.0.

## Meta

* tested with:
    * Java 1.6
    * Maven 3
	* [Spring Batch][spring-batch] 2.1.8.RELEASE
	* [Spring Framework(core)][spring-core] 3.0.6.RELEASE
* used IDE: primarily programmed with [Netbeans][netbeans] 7.0
* license: [Apache 2.0 License][apache-license]

[accenture]: http://www.accenture.com/ "Accenture official home page"
[ant]: http://ant.apache.org/ "Ant official home page"
[apache-license]: http://www.apache.org/licenses/LICENSE-2.0.txt "Apache 2.0 License"
[buildr]: http://buildr.apache.org/ "Buildr official home page"
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
[spring-core]: http://www.springsource.org/spring-core/ "Spring Core Framework official home page"
