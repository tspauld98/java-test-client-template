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
    implementation("org.junit.platform:junit-platform-launcher")
    implementation("org.junit.platform:junit-platform-suite-api")
    implementation("io.cucumber:cucumber-java:7.18.1")
    implementation("io.cucumber:cucumber-junit-platform-engine:7.18.1")
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

task("cucumber") {
    dependsOn("assemble", "compileTestJava")
    doLast {
        javaexec {
            mainClass.set("io.cucumber.core.cli.Main")
            classpath = cucumberRuntime + sourceSets.main.get().output + sourceSets.test.get().output
            // Change glue for your project package where the step definitions are.
            // And where the feature files are.
            args = listOf("--plugin", "pretty", "--glue", "info.rx00405.test.client.tests.features", "src/main/resources")
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