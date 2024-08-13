package info.rx00405.test.client;

import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.TestPlan;

public class PlatformTestRunner {
    SummaryGeneratingListener listener = new SummaryGeneratingListener();

    public SummaryGeneratingListener getListener() {
        return listener;
    }

    LauncherDiscoveryRequest getDefaultRequest() {
        return LauncherDiscoveryRequestBuilder.request()
            .selectors(DiscoverySelectors.selectPackage("info.rx00405.testClient.tests"))
            .build();
    }

    LauncherDiscoveryRequest getDefaultIsoFeatureRequest(String isoFeature) {
        return LauncherDiscoveryRequestBuilder.request()
            .selectors(DiscoverySelectors.selectPackage("info.rx00405.testClient.tests." + isoFeature))
            .build();
    }

    LauncherDiscoveryRequest getQuietRequest() {
        return LauncherDiscoveryRequestBuilder.request()
            .configurationParameter("cucumber.plugin", "html:test-results/index.html, junit:test-results/cucumber-report.xml, json:test-results/cucumber-report.json")
            .selectors(DiscoverySelectors.selectPackage("info.rx00405.testClient.tests"))
            .build();
    }

    LauncherDiscoveryRequest getQuietIsoFeatureRequest(String isoFeature) {
        return LauncherDiscoveryRequestBuilder.request()
            .configurationParameter("cucumber.plugin", "html:test-results/index.html, junit:test-results/cucumber-report.xml, json:test-results/cucumber-report.json")
            .selectors(DiscoverySelectors.selectPackage("info.rx00405.testClient.tests." + isoFeature))
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
        TestPlan testPlan = launcher.discover(request);
        launcher.registerTestExecutionListeners(listener);
        launcher.execute(testPlan);
    }
}