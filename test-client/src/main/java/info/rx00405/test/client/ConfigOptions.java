/*
 * Copyright (c) 2024 Battle Road Consulting. All rights reserved.
 */

 package info.rx00405.test.client;

public class ConfigOptions {
    boolean runQuiet = false;
    String isoFeature = null;
    String token = null;
    boolean printHelp = false;
    boolean invalidArg = false;

    public void processArgs(String[] args) {
        for (int i=0; i < args.length; i++) {
            switch (args[i]) {
                case "--run-quiet":
                    runQuiet = true;
                    break;
                case "--iso-feature":
                    i++;
                    isoFeature = args[i];
                    break;
                case "--token":
                    i++;
                    token = args[i];
                    break;
                case "--help":
                    printHelp = true;
                    break;
                default:
                    invalidArg = true;
                    break;
            }
        }
    }

    public boolean hasInvalidArg() {
        return invalidArg;
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
