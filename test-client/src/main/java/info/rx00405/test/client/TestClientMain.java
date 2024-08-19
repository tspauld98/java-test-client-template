/*
 * Copyright (c) 2024 Battle Road Consulting. All rights reserved.
 */

package info.rx00405.test.client;

/* 
import java.util.logging.Level;

import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.LoggingListener;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
 */

 import org.junit.platform.launcher.listeners.TestExecutionSummary;

public class TestClientMain {
    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        //System.out.println(new TestClientMain().getGreeting());
        PlatformTestRunner runner = new PlatformTestRunner();
        runner.runAll(new ConfigOptions());

        TestExecutionSummary summary = runner.getListener().getSummary();
        summary.printTo(new java.io.PrintWriter(System.out));

        // Additional details
        System.out.println("Tests found: " + summary.getTestsFoundCount());
        System.out.println("Tests succeeded: " + summary.getTestsSucceededCount());
        System.out.println("Tests failed: " + summary.getTestsFailedCount());
        System.out.println("Tests skipped: " + summary.getTestsSkippedCount());

/* 
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
            .configurationParameter("cucumber.glue", "info.rx00405.test.client.tests.features") // Select the package containing the step definitions
            .configurationParameter("cucumber.plugin", "pretty,html:test-results/index.html, junit:test-results/cucumber-report.xml, json:test-results/cucumber-report.json")
            .selectors(DiscoverySelectors.selectPackage("info.rx00405.test.client.tests.features")) // Select the package containing the feature files
            .build();

        // Create the JUnit launcher
        Launcher launcher = LauncherFactory.create();

        // Create a listener to capture the summary of test execution
        SummaryGeneratingListener listener = new SummaryGeneratingListener();

        // Execute the request with the listener
        launcher.execute(request, listener);

        // Retrieve the summary and print it
        TestExecutionSummary summary = listener.getSummary();
        summary.printTo(new java.io.PrintWriter(System.out));

        // Print additional details if needed
        System.out.println("Tests found: " + summary.getTestsFoundCount());
        System.out.println("Tests succeeded: " + summary.getTestsSucceededCount());
        System.out.println("Tests failed: " + summary.getTestsFailedCount());
        System.out.println("Tests skipped: " + summary.getTestsSkippedCount());

 */
    }
}
