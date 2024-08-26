/*
 * Copyright (c) 2024 Battle Road Consulting. All rights reserved.
 */

package info.rx00405.test.client;

import org.junit.platform.launcher.listeners.TestExecutionSummary;

public class TestClientMain {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";

    private static final String heading = "\n###############################################################################\n\n" + //
        "#######    Java Behavior-Driven Test Client\n\n" + //
        "###############################################################################\n";

    private static final String description = "## DESCRIPTION\n\n" + //
        "\tThis is the Java Behavior-Driven Test Client. It uses\n" + //
        "\tthe JUnit 5.x platform to run Cucumber BDD test scenarios. This\n" + //
        "\tclient is designed for black-box testing of separate systems and\n" + //
        "\tcomponents. It is packaged as an executable JAR file and designed\n" + //
        "\tto be run quietly so it can be integrated into CI/CD pipelines for\n" + //
        "\tend-to-end (E2E) integration testing.\n";

    private static final String synopsis = "## USAGE\n\n" + //
        "\tjava -jar [PATH to JAR/]test-client-shadow-<version>.jar [--run-quiet]\n" + //
        "\t\t[--iso-feature <iso-feature>] [--token <access-token>] [--help]\n";

    private static final String arguments = "## OPTIONS\n\n" + //
        "\t--run-quiet\t\t\tRun in quiet mode. The client will not\n" + //
        "\t\t\t\t\tprint any output to standard out.\n" + //
        "\t--iso-feature <iso-feature>\tRun only the feature indicated by the\n" + //
        "\t\t\t\t\t<iso-feature> argument.\n" + //
        "\t--token <access-token>\t\tUse the <access-token> to authenticate\n" + //
        "\t\t\t\t\twith the system under test for certain\n" + //
        "\t\t\t\t\tscenarios.\n" + //
        "\t--help\t\t\t\tPrint this help message.\n";
    
    private static ConfigOptions options = new ConfigOptions();

    public static ConfigOptions getConfigOptions() {
        return options;
    }

    public static void main(String[] args) {
        ConfigOptions options = getConfigOptions();
        options.processArgs(args);

        if (options.hasInvalidArg()) {
            System.out.println(heading);
            System.err.println(ANSI_RED + "ERROR:  Invalid argument. Use --help for more information.\n" + ANSI_RESET);
            System.out.println(synopsis);
        } else {
            if (options.mustPrintHelp()) {
                System.out.println(heading);
                System.out.println(description);
                System.out.println(synopsis);
                System.out.println(arguments);
            } else {
                if (!options.mustRunQuiet()) System.out.println(heading);

                PlatformTestRunner runner = new PlatformTestRunner();
                runner.runAll(options);

                if (!options.mustRunQuiet()) {
                    TestExecutionSummary summary = runner.getListener().getSummary();
                    System.out.println();
                    summary.printTo(new java.io.PrintWriter(System.out));
                    System.out.println();

                    // Additional details
                    System.out.println("Tests found: " + summary.getTestsFoundCount() + "\n");
                    System.out.println("Tests succeeded: " + summary.getTestsSucceededCount() + "\n");
                    System.out.println("Tests failed: " + summary.getTestsFailedCount() + "\n");
                    System.out.println("Tests skipped: " + summary.getTestsSkippedCount() + "\n");
                }
            }
        }
    }
}
