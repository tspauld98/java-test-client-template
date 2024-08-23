/*
 * Copyright (c) 2024 Battle Road Consulting. All rights reserved.
 *
 * For more details on building Java & JVM projects, please refer to https://docs.gradle.org/8.8/userguide/building_java_projects.html in the Gradle documentation.
 */

plugins {
    // Apply the application plugin to add support for building a CLI application in Java.
    application
    id("com.github.johnrengelman.shadow") version "8.1.1"
    jacoco
    `maven-publish`
    id("io.freefair.aspectj.post-compile-weaving") version "8.10"
}

group = "info.rx00405"
version = "1.0-SNAPSHOT"

configurations {}

val cucumberRuntime by configurations.creating {
    extendsFrom(configurations["testImplementation"])
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenLocal()
    mavenCentral()
}

dependencies {
    // Use JUnit Jupiter for testing.
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.3")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // This dependency is used by the application.
    implementation(libs.guava)
    implementation(platform("org.junit:junit-bom:5.10.3"))
    implementation("org.junit.jupiter:junit-jupiter")
    implementation("org.junit.jupiter:junit-jupiter-api")
    implementation("org.junit.platform:junit-platform-launcher")
    implementation("org.junit.platform:junit-platform-suite-api")
    implementation("io.cucumber:cucumber-java:7.18.1")
    implementation("io.cucumber:cucumber-junit-platform-engine:7.18.1")
    implementation("org.apache.httpcomponents.client5:httpclient5-fluent:5.3.1")
    implementation("org.slf4j:slf4j-api:2.0.16")
    implementation("org.slf4j:slf4j-simple:2.0.16")
    implementation("com.google.code.gson:gson:2.11.0")
    implementation("commons-validator:commons-validator:1.9.0")
    implementation("org.aspectj:aspectjrt:1.9.22")
    implementation("org.aspectj:aspectjweaver:1.9.22")
    implementation("com.graphql-java:graphql-java-extended-validation:22.0")
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

application {
    // Define the main class for the application.
    mainClass = "info.rx00405.test.client.TestClientMain"
}

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
    systemProperty("cucumber.junit-platform.naming-strategy", "long")
}

tasks {
    // Configuring the default jar task
    val jar by getting(Jar::class) {
        archiveBaseName.set("${project.name}-default")
    }

    // Configuring the shadow jar task
    val shadowJar by getting(com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar::class) {
        archiveBaseName.set("${project.name}-shadow")
        archiveClassifier.set("")
        mergeServiceFiles()
    }
}

// Ensuring that both jars are built when running the build task
tasks.build {
    dependsOn(tasks.jar, tasks.shadowJar)
}

task("testClient") {
    dependsOn("assemble", "compileTestJava")
    doLast {
        javaexec {
            mainClass.set("info.rx00405.test.client.TestClientMain")
            classpath = cucumberRuntime + sourceSets.main.get().output + sourceSets.test.get().output
            // Change glue for your project package where the step definitions are.
            // And where the feature files are.
            //args = listOf("--plugin", "pretty", "--glue", "info.rx00405.test.client.tests.features", "src/main/resources")
            //val initialArgs = emptyList();
            args = project.findProperty("testClientArgs")?.toString()?.split(" ") ?: emptyList();
            //args = initialArgs + additionalArgs
            // Configure jacoco agent for the test coverage.
            val jacocoAgent = zipTree(configurations.jacocoAgent.get().singleFile)
                .filter { it.name == "jacocoagent.jar" }
                .singleFile
            jvmArgs = listOf("-javaagent:$jacocoAgent=destfile=$buildDir/results/jacoco/cucumber.exec,append=false")
        }
    }
}

tasks.jacocoTestReport {
    // Give jacoco the file generated with the cucumber tests for the coverage.
    executionData(files("$buildDir/jacoco/test.exec", "$buildDir/results/jacoco/cucumber.exec"))
    reports {
        xml.required.set(true)
    }
}