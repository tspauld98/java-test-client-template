/*
 * Copyright (c) 2024 Battle Road Consulting. All rights reserved.
 *
 * For more details on building Java & JVM projects, please refer to https://docs.gradle.org/8.8/userguide/building_java_projects.html in the Gradle documentation.
 */

plugins {
    // Apply the application plugin to add support for building a CLI application in Java.
    application
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "info.rx00405"
version = "1.0-SNAPSHOT"

repositories {
    // Use Maven Central for resolving dependencies.
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
