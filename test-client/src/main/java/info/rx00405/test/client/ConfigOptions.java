/*
 * Copyright (c) 2024 Battle Road Consulting. All rights reserved.
 */

 package info.rx00405.test.client;

public class ConfigOptions {
    boolean runQuiet = false;
    String isoFeature = null;
    boolean printHelp = false;

    public void processArgs(String[] args) {
        for (String arg : args) {
            switch (arg) {
                case "--run-quiet":
                    runQuiet = true;
                    break;
                case "--iso-feature":
                    // TODO: Add support for this
                    break;
                case "--help":
                default:
                    printHelp = true;
                    break;
            }
        }
    }

    public boolean mustPrintHelp() {
        return printHelp;
    }

    public boolean mustRunQuiet() {
        return runQuiet;
    }

    public String getIsoFeature() {
        return isoFeature;
    }
}
