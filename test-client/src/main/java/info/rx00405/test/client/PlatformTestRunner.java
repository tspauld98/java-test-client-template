/*
 * Copyright (c) 2024 Battle Road Consulting. All rights reserved.
 */

 package info.rx00405.test.client;

import org.junit.platform.launcher.listeners.LoggingListener;
import java.util.logging.Level;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.core.LauncherFactory;

public class PlatformTestRunner {
    SummaryGeneratingListener listener = new SummaryGeneratingListener();
    LoggingListener loggingListener = LoggingListener.forJavaUtilLogging(Level.INFO);

    public SummaryGeneratingListener getListener() {
        return listener;
    }

    public LoggingListener getLoggingListener() {
        return loggingListener;
    }

    LauncherDiscoveryRequest getDefaultRequest() {
        return LauncherDiscoveryRequestBuilder.request()
            .configurationParameter("cucumber.glue", "info.rx00405.test.client.tests.features")
            .configurationParameter("cucumber.plugin", "pretty")
            .selectors(DiscoverySelectors.selectPackage("info.rx00405.test.client.tests.features"))
            .build();
    }

    LauncherDiscoveryRequest getDefaultIsoFeatureRequest(String isoFeature) {
        return LauncherDiscoveryRequestBuilder.request()
            .configurationParameter("cucumber.glue", "info.rx00405.test.client.tests.features")
            .configurationParameter("cucumber.plugin", "pretty")
            .selectors(DiscoverySelectors.selectPackage("info.rx00405.test.client.tests.features." + isoFeature))
            .build();
    }

    LauncherDiscoveryRequest getQuietRequest() {
        return LauncherDiscoveryRequestBuilder.request()
            .configurationParameter("cucumber.glue", "info.rx00405.test.client.tests.features")
            .configurationParameter("cucumber.plugin", "html:test-results/index.html, junit:test-results/cucumber-report.xml, json:test-results/cucumber-report.json")
            .selectors(DiscoverySelectors.selectPackage("info.rx00405.test.client.tests.features"))
            .build();
    }

    LauncherDiscoveryRequest getQuietIsoFeatureRequest(String isoFeature) {
        return LauncherDiscoveryRequestBuilder.request()
            .configurationParameter("cucumber.glue", "info.rx00405.test.client.tests.features")
            .configurationParameter("cucumber.plugin", "html:test-results/index.html, junit:test-results/cucumber-report.xml, json:test-results/cucumber-report.json")
            .selectors(DiscoverySelectors.selectPackage("info.rx00405.test.client.tests.features." + isoFeature))
            .build();
    }

    public void runAll(ConfigOptions config) {
        LauncherDiscoveryRequest request = null;
        if (!config.getRunQuiet()) {
            if (config.getIsoFeature() != null) {
                request = getDefaultIsoFeatureRequest(config.getIsoFeature());
            } else {
                request = getDefaultRequest();
            }
        } else {
            if (config.getIsoFeature() != null) {
                request = getQuietIsoFeatureRequest(config.getIsoFeature());
            } else {
                request = getQuietRequest();
            }
        }

        Launcher launcher = LauncherFactory.create();
        launcher.execute(request, getListener());
    }
}